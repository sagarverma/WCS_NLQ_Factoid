package com.ibm.tatzia.kg.response;

//import com.ibm.tatzia.kg.query.AggType;


public enum FieldType {
	Boolean("Boolean"), 
	Integer("Integer"), 
	Long("Long"), 
	Float("Float"), 
	Double("Double"), 
	String("String"), 
	Text("Text"), 
	Date("Date"),
	Clob("Clob");
	
	String value;
	FieldType(String str){
		value = str;
	}

	public static FieldType fromValue(String v) {
        for (FieldType c: FieldType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        System.err.println(v);
        throw new IllegalArgumentException(v);
    }
	
	public static FieldType fromString(String text) {
	    if (text != null) {
	      for (FieldType b : FieldType.values()) {
	        if (text.equalsIgnoreCase(b.toString())) {
	          return b;
	        }
	      }
	    }
	    return null;
	  }
}

