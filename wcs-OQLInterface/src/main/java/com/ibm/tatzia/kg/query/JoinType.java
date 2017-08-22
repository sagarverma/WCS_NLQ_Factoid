package com.ibm.tatzia.kg.query;

public enum JoinType {
 INNERJOIN("INNER JOIN"),
 OUTERJOIN("OUTER JOIN"),
 LEFTOUTERJOIN("LEFT OUTER JOIN"),
 RIGTHOUTERJOIN("RIGHT OUTER JOIN");
 
 private String symbol;

	private JoinType(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		
		return symbol;
	}
	public static JoinType fromString(String text) {
	    if (text != null) {
	      for (JoinType b : JoinType.values()) {
	        if (text.equalsIgnoreCase(b.symbol.replaceAll(" ",""))) {
	          return b;
	        }
	      }
	    }
	    return null;
	  }

}
