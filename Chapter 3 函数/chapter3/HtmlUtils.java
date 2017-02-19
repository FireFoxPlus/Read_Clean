package chapter3;
//重构之前
public class HtmlUtils {

	public static String testableHtml(PageData pageData,
			boolean includeSuiteSetup) throws Exception {
		WikiPage wikiPage = pageData.getWikiPage();
		StringBuffer buffer = new StringBuffer();
		if (pageData.hasAttribute("test")) {
			if (includeSuiteSetup) {
				WikiPage suiteSetup = PageCrawlerImpl.getInheritedPage(
						SuiteResponder.SUITE_SETUP_NAME, wikiPage);
				if (suiteSetup != null) {
					WikiPagePath pagePath = suiteSetup.getPageCrawler()
							.getFullPath(suiteSetup);
					String pagePathName = PathParser.render(pagePath);
					buffer.append("!include -setup . ").append(pagePathName)
							.append("\n");
				}
			}
			WikiPage setup = PageCrawlerImpl
					.getInheritedPage("setUp", wikiPage);
			if (setup != null) {
				WikiPagePath setupPath = wikiPage.getPageCrawler().getFullPath(
						setup);
				String setupPathName = PathParser.render(setupPath);
				buffer.append("!include -setup . ").append(setupPathName)
						.append("\n");
			}
		}
		buffer.append(pageData.getContent());
		if (pageData.hasAttribute("test")) {
			WikiPage tearDown = PageCrawlerImpl.getInheritedPage("TearDown",
					wikiPage);
			if (tearDown != null) {
				WikiPagePath tearDownPath = wikiPage.getPageCrawler()
						.getFullPath(tearDown);
				String tearDownPathName = PathParser.render(tearDownPath);
				buffer.append("!include -teardown . ").append(tearDownPathName)
						.append("\n");
			}
			if (includeSuiteSetup) {
				WikiPage suiteTearDown = PageCrawlerImpl.getInheritedPage(
						SuiteResponder.SUITE_TEARDOWN_NAME, wikiPage);
				if (suiteTearDown != null) {
					WikiPagePath pagePath = suiteTearDown.getPageCrawler()
							.getFullPath(suiteTearDown);
					String pagePathName = PathParser.render(pagePath);
					buffer.append("!include -teardown . ").append(pagePathName)
							.append("\n");
				}
			}
		}
		pageData.setContent(buffer.toString());
		return pageData.getHtml();
	}

}
