package com.ibm.tatzia.kg.response;

public enum AnswerType {
	Data("Data"), // Data from SQL index
	Text("Text"); // Text from SOLR index
	
	
	String value;
	AnswerType(String str){
		value = str;
	}

	public static AnswerType fromValue(String v) {
        for (AnswerType c: AnswerType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        System.err.println(v);
        throw new IllegalArgumentException(v);
    }
}
