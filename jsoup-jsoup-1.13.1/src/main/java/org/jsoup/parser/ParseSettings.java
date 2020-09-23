package org.jsoup.parser;

import org.jsoup.nodes.Attributes;

import static org.jsoup.internal.Normalizer.lowerCase;

import org.jsoup.internal.StringUtil;

/**
 * Controls parser settings, to optionally preserve tag and/or attribute name case.
 */
public class ParseSettings {
    /**
     * HTML default settings: both tag and attribute names are lower-cased during parsing.
     */
    public static final ParseSettings htmlDefault;
    /**
     * Preserve both tag and attribute case.
     */
    public static final ParseSettings preserveCase;

    static {
        htmlDefault = new ParseSettings(false, false);
        preserveCase = new ParseSettings(true, true);
    }

    private final boolean preserveTagCase;
    private final boolean preserveAttributeCase;
    private String searchText;
    private int contextLength;
    private boolean isSearch = false;

    /**
     * Returns true if preserving tag name case.
     */
    public boolean preserveTagCase() {
        return preserveTagCase;
    }

    /**
     * Returns true if preserving attribute case.
     */
    public boolean preserveAttributeCase() {
        return preserveAttributeCase;
    }

    /**
     * Define parse settings.
     * @param tag preserve tag case?
     * @param attribute preserve attribute name case?
     */
    public ParseSettings(boolean tag, boolean attribute) {
        preserveTagCase = tag;
        preserveAttributeCase = attribute;
    }

    /**
     * Normalizes a tag name according to the case preservation setting.
     */
    public String normalizeTag(String name) {
        name = name.trim();
        if (!preserveTagCase)
            name = lowerCase(name);
        return name;
    }

    /**
     * Normalizes an attribute according to the case preservation setting.
     */
    public String normalizeAttribute(String name) {
        name = name.trim();
        if (!preserveAttributeCase)
            name = lowerCase(name);
        return name;
    }

    Attributes normalizeAttributes(Attributes attributes) {
        if (attributes != null && !preserveAttributeCase) {
            attributes.normalize();
        }
        return attributes;
    }

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
		isSearch = !StringUtil.isBlank(this.searchText);
	}

	public boolean isSearchText() {
		return isSearch;
	}

	public void setContextLength(int contextLength) {
		this.contextLength = contextLength;
	}

	public int getContextLength() {
		return contextLength;
	}
}
