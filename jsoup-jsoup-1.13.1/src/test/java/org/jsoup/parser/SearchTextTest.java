package org.jsoup.parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.integration.ParseTest;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.SearchResult;
import org.junit.Test;

import static org.junit.Assert.*;

public class SearchTextTest {
	@Test
	public void searchTest() {
		try {
			File in = ParseTest.getFile("/htmltests/text00004.html");

			Parser parser = Parser.htmlParser();
			parser.settings().setSearchText("紅樓夢");
			Document doc = Jsoup.parse(new FileInputStream(in), "utf-8", "", parser);

			List<SearchResult> searchResults = doc.getSearchHelper().getDocSearchResult();
			assertEquals(searchResults.size(), 4);

			assertEquals(searchResults.get(0).leftText, "　　《");
			assertEquals(searchResults.get(0).text, "紅樓夢");
			assertEquals(searchResults.get(0).rightText,
					"》旨義\r\n" + " 　是書題名極[多，一曰《紅樓]〔二〕\r\n" + " 夢》，是總其全部之名也；又曰《風月寶鑑》，是戒");

			assertEquals(searchResults.get(1).leftText, "風月之情；又曰《石頭記》，是自譬石頭所記之事也。此三名皆書中曾已點睛矣。如寶玉作夢，夢中有曲，名曰《");
			assertEquals(searchResults.get(1).text, "紅樓夢");
			assertEquals(searchResults.get(1).rightText, "十二支》，此則《紅樓夢》之點睛。又如賈瑞病，跛道人持一鏡來，上面即鏨「風月寶鑑」四字，此則《風月寶鑑");

			assertEquals(searchResults.get(2).leftText, "》，是自譬石頭所記之事也。此三名皆書中曾已點睛矣。如寶玉作夢，夢中有曲，名曰《紅樓夢十二支》，此則《");
			assertEquals(searchResults.get(2).text, "紅樓夢");
			assertEquals(searchResults.get(2).rightText, "》之點睛。又如賈瑞病，跛道人持一鏡來，上面即鏨「風月寶鑑」四字，此則《風月寶鑑》之點睛。又如道人親眼");

			assertEquals(searchResults.get(3).leftText, "十二女子也；然通部細搜檢去，上中下女子豈止十二人哉！若云其中自有十二個，則又未嘗指明白係某某，及至「");
			assertEquals(searchResults.get(3).text, "紅樓夢");
			assertEquals(searchResults.get(3).rightText, "」一回中，亦曾翻出金陵十二釵之簿籍，又有十二支曲可考。");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void brByteEndTest() {
		try {
			File in = ParseTest.getFile("/htmltests/bano_9781411433182_oeb_fm1_r1.html");
			Parser parser = Parser.htmlParser();
			Document document = Jsoup.parse(new FileInputStream(in), "utf-8", "", parser);

			System.out.println("debug");
		} catch (Exception e) {

		}
	}
}
