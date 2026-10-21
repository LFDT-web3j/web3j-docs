"""Check release consistency and export rendered article links for Lychee."""
from html.parser import HTMLParser
from pathlib import Path
import json
import re
import xml.etree.ElementTree as ET

import yaml


class ArticleLinks(HTMLParser):
    """Exclude theme metadata (e.g. unpublished canonical URLs), not content links."""

    def __init__(self):
        super().__init__()
        self.in_article = False
        self.links = set()

    def handle_starttag(self, tag, attrs):
        if tag == "article":
            self.in_article = True
        if not self.in_article:
            return
        attrs = dict(attrs)
        url = attrs.get("href" if tag == "a" else "src" if tag == "img" else "")
        if url and url.startswith(("https://", "http://")):
            # The theme's edit buttons require authentication and may name new PR files.
            if not url.startswith("https://github.com/LFDT-web3j/web3j-docs/edit/"):
                self.links.add(url)

    def handle_endtag(self, tag):
        if tag == "article":
            self.in_article = False


def check(root=Path(".")):
    config = yaml.safe_load((root / "mkdocs.yml").read_text())
    version = str(config["extra"]["web3j"]["version"])
    if not re.fullmatch(r"[0-9]+\.[0-9]+\.[0-9]+", version):
        raise ValueError("Guide version must name a stable published release")
    pom = ET.parse(root / "examples/pom.xml")
    actual = pom.findtext("{*}properties/{*}web3j.version")
    if actual != version:
        raise ValueError(f"Example dependency {actual} differs from guide {version}")
    api = root / "site/javadoc-api/index.html"
    if f"https://javadoc.io/doc/org.web3j/core/{version}/index.html" not in api.read_text():
        raise ValueError("API landing page does not link to the configured release")
    pages = sorted((root / "site").rglob("*.html"))
    if len(pages) < 2:
        raise ValueError("Build the documentation before checking it")
    sources = {}
    for page in pages:
        html = page.read_text()
        if re.search(r"<title>[^<]*4\.8\.2-SNAPSHOT", html):
            raise ValueError(f"Stale generated Javadoc was published: {page}")
        if re.search(r"\{\{\s*(?:web3j|android|gradle_plugin|maven_plugin|openapi|sokt)\.", html):
            raise ValueError(f"Unexpanded version template: {page}")
        parser = ArticleLinks()
        parser.feed(html)
        for url in parser.links:
            source = re.match(
                r"https://github\.com/(?:LFDT-web3j|web3j)/web3j/(?:blob|tree)/([^/]+)(?:/|$)",
                url,
            )
            if source and source.group(1) != f"v{version}":
                raise ValueError(f"Core source link differs from guide {version}: {url} in {page}")
            sources.setdefault(url, []).append(str(page.relative_to(root)))
    if not sources:
        raise ValueError("No rendered content links found")
    output = root / "build"
    output.mkdir(exist_ok=True)
    (output / "external-links.txt").write_text("\n".join(sorted(sources)) + "\n")
    (output / "link-sources.json").write_text(json.dumps(sources, indent=2) + "\n")
    print(f"Web3j {version}: checked {len(pages)} HTML pages; exported {len(sources)} article links")


if __name__ == "__main__":
    check()
