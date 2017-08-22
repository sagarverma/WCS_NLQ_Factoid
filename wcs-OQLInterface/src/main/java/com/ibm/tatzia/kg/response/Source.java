package com.ibm.tatzia.kg.response;

public class Source {
	public static final String TYPE_APPLICATION = "application";
	public static final String TYPE_TEXT = "text";
	public static final String TYPE_VIDEO = "video";
	
	public String url;
	public String type;
	
	public Source(String url, String type) {
		this.url = url;
		this.type = type;
	}
	
	public String getUrl() {
		return this.url;
	}

	public String getType() {
		return this.type;
	}
}
