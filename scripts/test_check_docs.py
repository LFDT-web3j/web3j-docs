import unittest
from check_docs import ArticleLinks


class LinkExtractionTest(unittest.TestCase):
    def test_checks_article_links_and_images_but_not_canonical_metadata(self):
        parser = ArticleLinks()
        parser.feed('''<head><link rel="canonical" href="https://unpublished.invalid/page/"></head>
        <article><a href="https://example.org/guide">Guide</a>
        <img src="https://example.org/image.png">
        <a href="https://docs.web3j.io/broken-content-link">Check this too</a>
        <a href="https://github.com/LFDT-web3j/web3j-docs/edit/main/docs/new.md">Edit</a>
        <pre>https://example.invalid/placeholder</pre></article>''')
        self.assertEqual(parser.links, {
            "https://example.org/guide", "https://example.org/image.png",
            "https://docs.web3j.io/broken-content-link"})

    def test_deduplicates_links_and_ignores_local_navigation(self):
        parser = ArticleLinks()
        parser.feed('<article><a href="../quickstart/">Local</a>'
                    '<a href="https://example.org/">A</a><a href="https://example.org/">B</a></article>')
        self.assertEqual(parser.links, {"https://example.org/"})


if __name__ == "__main__":
    unittest.main()
