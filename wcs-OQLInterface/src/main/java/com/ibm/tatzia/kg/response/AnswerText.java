package com.ibm.tatzia.kg.response;

import java.util.List;
public class AnswerText {
	List<Object> values;
	public List<Object> getValues() {
		return values;
	}
	public void setValues(List<Object> values) {
		this.values = values;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
	Source source;
}
