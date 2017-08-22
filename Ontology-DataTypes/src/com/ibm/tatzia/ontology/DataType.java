package com.ibm.tatzia.ontology;



public enum DataType {
	BIN ("xsd:base64Binary"),
	LANG ("xsd:language"),
	LONG ("xsd:long"),
	STRING ("xsd:string"),
	INT ("xsd:int"),	
	INTEGER ("xsd:integer"),
	DOUBLE ("xsd:double"),
	BOOLEAN ("xsd:boolean"),
	DATE ("xsd:date"),
	YEAR ("xsd:year"),
	DATETIME ("xsd:dateTime"),
	SHORT ("xsd:short"),	
	CHAR ("xsd:char"),		
	FLOAT ("xsd:float"),
	BOOL ("xsd:bool"),
	BYTE ("xsd:byte"),
	NESTEDQUERY(""),
	CLOB("xsd:clob");
	
	String value;
	DataType(String str){
		value = str;
	}
	
	public static DataType fromValue(String v) {
        for (DataType c: DataType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        System.err.println(v);
   //     throw new IllegalArgumentException(v);
        return null;
    }
	public static DataType fromName(String v) {
        for (DataType c: DataType.values()) {
            if (c.name().equals(v)) {
                return c;
            }
        }
        System.err.println(v);
   //     throw new IllegalArgumentException(v);
        return null;
    }
	/* checks with the name */
	public static DataType fromString(String text) {
	    if (text != null) {
	      for (DataType b : DataType.values()) {
	        if (text.equalsIgnoreCase(b.toString().replaceAll("xsd:", ""))) {
	          return b;
	        }
	      }
	    }
	    return null;
	  }
}
