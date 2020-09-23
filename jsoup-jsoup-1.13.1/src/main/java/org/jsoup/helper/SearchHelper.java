package org.jsoup.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Position;
import org.jsoup.nodes.SearchResult;
import org.jsoup.nodes.TextNode;

public class SearchHelper {
	private static final int SEARCH_LEN = 50;
	private String searchText;
	private List<Position> searchCache = new ArrayList<>();
	private List<SearchResult> docSearchResult = new ArrayList<>();
	private List<SearchResult> paraSeachResult = new ArrayList<>();
	private String sourceText = "";
	private String paraText = "";
	private int paraOffset = 0;
	private Pattern pattern;
	private int contextLength = SEARCH_LEN;

	public SearchHelper(String searchText) {
		this.searchText = searchText;
	}

	public List<SearchResult> getDocSearchResult() {
		return docSearchResult;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public int getContextLength() {
		return contextLength;
	}

	public void setContextLength(int contextLength) {
		if (contextLength > 0) {
			this.contextLength = contextLength;
		}
	}

	public void getDocContainingText(TextNode textNode) {
		getParaSearchText(textNode);
		matchText();
	}

	private void getParaSearchText(TextNode textNode) {
		if (textNode.getWholeText().equals("\n") || textNode.getWholeText().equals("\r\n")) {
			saveParaSearchResult();
			clean();
			return;
		}
		cacheParaSearchText(textNode);
	}

	private void cacheParaSearchText(TextNode textNode) {
		int start = sourceText.length();
		sourceText += textNode.getWholeText();
		paraText += textNode.getWholeText();
		int end = sourceText.length();
		Position position = new Position(start, end);
		position.textNode = textNode;
		searchCache.add(position);
	}

	private void matchText() {
		initPattern();
		Matcher matcher = pattern.matcher(sourceText);
		int end = -1;
		while (matcher.find()) {
			end = matcher.end();
			getSearchResult(matcher.start(), matcher.end());
		}
		if (end >= 0) {
			sourceText = sourceText.substring(end);
			paraOffset = end;
		}
	}

	private void initPattern() {
		if (pattern == null) {
			pattern = Pattern.compile(searchText);
		}
	}

	private void getSearchResult(int start, int end) {
		SearchResult searchResult = new SearchResult(searchText);
		for (Position position : searchCache) {
			if (position.contain(start)) {
				getStartPosition(position, start, searchResult);
			}
			if (position.contain(end)) {
				getEndPosition(position, end, searchResult);
			}
		}
		if (!searchResult.isInvalid()) {
			paraSeachResult.add(searchResult);
		}
	}

	private void getStartPosition(Position position, int endIndex, SearchResult searchResult) {
		int index = endIndex - position.start;
		int offset = position.textNode.getWholeText().substring(0, index).getBytes().length;

		searchResult.startXPath = position.textNode.getXPath();
		searchResult.startPosition = position.textNode.byteStartPos() + offset;
		searchResult.startOffset = paraOffset + endIndex;
	}

	private void getEndPosition(Position position, int endIndex, SearchResult searchResult) {
		int index = endIndex - position.start - 1;
		int offset = position.textNode.getWholeText().substring(0, index).getBytes().length;

		searchResult.endXPath = position.textNode.getXPath();
		searchResult.endPosition = position.textNode.byteStartPos() + offset;
		searchResult.endOffset = paraOffset + endIndex;
	}

	private void saveParaSearchResult() {
		for (SearchResult searchResult : paraSeachResult) {
			getLeftText(searchResult);
			getRightText(searchResult);
			docSearchResult.add(searchResult);
		}
	}

	private void getLeftText(SearchResult searchResult) {
		int end = searchResult.startOffset;
		if (end < 0 || end >= paraText.length()) {
			return;
		}
		int start = end - contextLength > 0 ? end - contextLength : 0;
		searchResult.leftText = paraText.substring(start, end);
	}

	private void getRightText(SearchResult searchResult) {
		int start = searchResult.endOffset;
		if (start < 0 || start >= paraText.length()) {
			return;
		}
		int end = start + contextLength >= paraText.length() ? paraText.length() : start + contextLength;
		searchResult.rightText = paraText.substring(start, end);
	}

	private void clean() {
		searchCache.clear();
		paraSeachResult.clear();
		sourceText = "";
		paraText = "";
		paraOffset = 0;
	}
}
