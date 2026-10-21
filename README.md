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

Install [Lychee 0.24.2](https://github.com/lycheeverse/lychee/releases/tag/lychee-v0.24.2) for the last command. CI pins the same version. The Java tests use a local mock RPC server and offline signing; they do not require a node, provider credentials or funds. The balance-read, authorization-signing and raw-transaction examples are included directly from `examples/src/main/java`, so CI tests those published sources. The CLI, plugin and generated-wrapper walkthroughs are documented separately; these tests do not run a live contract deployment.

Lychee complements MkDocs: MkDocs validates internal navigation and anchors, while Lychee checks external HTTP(S) destinations in parallel, with retries, timeouts and explicit exceptions. Its standalone CLI runs the same checks locally and in GitHub Actions and produces a reviewable link report, without requiring a paid service.

Strict MkDocs builds check navigation, internal Markdown links and anchors. `check_docs.py` compares guide/example versions, checks core source tags and the API reference and exports HTTP(S) links from rendered articles, including images, to `build/external-links.txt`. `build/link-sources.json` maps links back to pages. The external check follows redirects and retries transient failures; it does not validate external page anchors, benchmark claims, or all legacy code snippets. Theme canonical URLs and edit buttons are not article-content destinations. Loopback and private-network addresses are excluded. Three exact URLs (Etherscan, Sepolia Etherscan and a Medium diagram attribution) are also excluded because they return HTTP 403 from GitHub-hosted runners despite passing a local check; their review dates and evidence are recorded in `lychee.toml`. Any other exception in `lychee.toml` must name the exact scope, reason and review date; do not broadly accept HTTP 403 or 404.

Pull requests produce a downloadable `documentation-preview` artifact and a link report. Fix failures before merging. The main-branch deployment calls these same checks before publishing.

## Analytics

The previous configuration used the legacy Universal Analytics ID `UA-85710619-1`. Confirm the site’s GA4 measurement ID and any existing tag forwarding before changing analytics in production. Once confirmed, configure [Material’s `extra.analytics`](https://squidfunk.github.io/mkdocs-material/setup/setting-up-site-analytics/) with `provider: google` and `property: G-…`; do not invent a replacement ID. Analytics migration remains pending until that ID is available.

## Version updates and publication

`mkdocs.yml` configures the core documentation version at `extra.web3j.version`. EVM and Unit use that same version; Android, build plugins, OpenAPI and Sokt have separate release numbers. Update `examples/pom.xml` with the core version. Verify each artifact exists and its compatibility requirements; plugin versions need not match core. The guide’s API landing page and core source/release links use that version automatically. In Markdown, write core source URLs as `https://github.com/LFDT-web3j/web3j/blob/v{{ web3j.version }}/...` (or `tree/v{{ web3j.version }}`). The existing Markdown template plugin expands the tag at build time, keeping released guides tied to the corresponding source rather than a moving branch.

`mike` owns the deployed root `versions.json` and `latest` alias. Do not add a competing manifest under `docs/`. The historical generated Javadoc is excluded from new builds; `docs/javadoc-api/index.md` retains the existing API entry URL and links to release-specific Javadoc.io pages.

Merging to main publishes the version from `mkdocs.yml` and moves `latest` after validation. Existing versioned guide directories remain intact; publishing a new guide does not rewrite 4.11.0 or other historical releases. Publication is serialized and skips a run if main has advanced. To rehearse Mike locally without publishing:

```bash
mike deploy --update-aliases 6.0.0 latest
mike serve
```

The command creates/updates the local `gh-pages` branch; omit `--push` during rehearsal. Only maintainers should publish. Update Python dependencies deliberately in a clean Python 3.13 virtual environment and commit the complete `pip freeze` output to `requirements.txt` after passing these checks.
