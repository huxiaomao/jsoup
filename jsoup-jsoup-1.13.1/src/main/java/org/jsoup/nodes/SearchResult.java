package org.jsoup.nodes;

public class SearchResult {
	public String text;
	public String leftText;
	public String rightText;
	
	public String startXPath;
	public int startPosition = Integer.MAX_VALUE;
	public int startOffset = Integer.MAX_VALUE;

	public String endXPath;
	public int endPosition = Integer.MAX_VALUE;
	public int endOffset = Integer.MAX_VALUE;

	public SearchResult(String text) {
		this.text = text;
	}

	public String getStartXPath() {
		return startXPath;
	}

	public void setStartXPath(String startXPath) {
		this.startXPath = startXPath;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}

	public String getEndXPath() {
		return endXPath;
	}

	public void setEndXPath(String endXPath) {
		this.endXPath = endXPath;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(int endOffset) {
		this.endOffset = endOffset;
	}

	public boolean isInvalid() {
		return startPosition == Integer.MAX_VALUE || endPosition == Integer.MAX_VALUE;
	}
	
	@Override
	public String toString() {
		return leftText + text + rightText;
	}
}
