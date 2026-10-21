# Web3j documentation

Source for [docs.web3j.io](https://docs.web3j.io/). The current guide targets Web3j 6 and Java 21. API documentation is linked to the corresponding published Maven Central artifact.

## Local setup

Use Python 3.13, Java 21 and Maven 3.9 or newer:

```bash
git clone https://github.com/LFDT-web3j/web3j-docs.git
cd web3j-docs
python3.13 -m venv venv
source venv/bin/activate
python -m pip install -r requirements.txt
mkdocs serve
```

For a built preview, run `mkdocs build --strict` and serve `site/` with a local HTTP server. The version selector uses Mike's deployed versions manifest; it is available on the published site.

## Checks before review

```bash
mkdocs build --strict
python -m unittest discover -s scripts -p 'test_*.py'
python scripts/check_docs.py
mvn -B -f examples/pom.xml verify
lychee --no-progress build/external-links.txt
```

Install [Lychee 0.24.2](https://github.com/lycheeverse/lychee/releases/tag/lychee-v0.24.2) for the last command. CI pins the same version. The Java tests use a local mock RPC server and offline signing; they do not require a node, provider credentials or funds. The code displayed in the quickstart and signing guide is included directly from `examples/src/main/java`, so CI tests the published source.

Strict MkDocs builds check navigation, internal Markdown links and anchors. `check_docs.py` compares guide/example versions, checks the API reference and exports HTTP(S) links from rendered articles, including images, to `build/external-links.txt`. `build/link-sources.json` maps links back to pages. The external check follows redirects and retries transient failures; it does not validate external page anchors, benchmark claims, or all legacy code snippets. Theme canonical URLs and edit buttons are not article-content destinations. Loopback and private-network addresses are excluded. Any other exception in `lychee.toml` must name the exact scope, reason and review date; do not broadly accept HTTP 403 or 404.

Pull requests produce a downloadable `documentation-preview` artifact and a link report. Fix failures before merging. The main-branch deployment calls these same checks before publishing.

## Version updates and publication

`mkdocs.yml` contains independent versions for core, Android, build plugins and extensions. Update `examples/pom.xml` with the core version. Verify each artifact exists and its compatibility requirements; plugin versions need not match core. The guide's API landing page uses the core version automatically.

`mike` owns the deployed root `versions.json` and `latest` alias. Do not add a competing manifest under `docs/`. The historical generated Javadoc is excluded from new builds; `docs/javadoc-api/index.md` retains the existing API entry URL and links to release-specific Javadoc.io pages.

Merging to main publishes the version from `mkdocs.yml` and moves `latest` after validation. Existing versioned guide directories remain intact; publishing a new guide does not rewrite 4.11.0 or other historical releases. Publication is serialized and skips a run if main has advanced. To rehearse Mike locally without publishing:

```bash
mike deploy --update-aliases 6.0.0 latest
mike serve
```

The command creates/updates the local `gh-pages` branch; omit `--push` during rehearsal. Only maintainers should publish. Update Python dependencies deliberately in a clean Python 3.13 virtual environment and commit the complete `pip freeze` output to `requirements.txt` after passing these checks.
