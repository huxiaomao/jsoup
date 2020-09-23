package org.jsoup.nodes;

public class Position {
	public TextNode textNode;
	public int start;
	public int end;

	public Position() {
	}

	public Position(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	public boolean contain(int position) {
		return position >= start && position < end;
	}
	
	@Override
	public String toString() {
		return "[" + start + "," + end + "]";
	}
}
