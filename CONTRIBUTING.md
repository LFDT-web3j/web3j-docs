# Contributing to Web3j documentation

Report documentation problems in the [docs issue tracker](https://github.com/LFDT-web3j/web3j-docs/issues). Include the page URL, affected Web3j version and steps or links that failed. Library implementation problems belong in the [core issue tracker](https://github.com/LFDT-web3j/web3j/issues).

For a documentation change:

1. Create a branch and edit the Markdown under `docs/`.
2. Use relative Markdown links between guide pages so they remain in the selected version.
3. Link technical claims to maintained primary sources. Mark historical client-specific recipes clearly.
4. Follow the [README checks](README.md#checks-before-review), inspect the rendered pages, and add or update executable examples when changing the tested snippets.
5. Open a PR describing the user-visible correction, related issues and validation. Sign off commits for the project's DCO check (`git commit -s`).

Keep core, Android and plugin version assumptions separate. Do not include API keys, wallet credentials or real-value transfers in examples. PR checks require no publication credentials; merging a reviewed change to main triggers the existing docs publication workflow.
