package com.ibm.tatzia.kg.response;

import java.util.ArrayList;
import java.util.List;

public class AnswerDataRow {
	List<Object> values;

	public AnswerDataRow() {
		super();
		this.values = new ArrayList<Object>();
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public void addValue(Object value) {
		this.values.add(value);
	}

	
	
}
