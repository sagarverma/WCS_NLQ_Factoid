package com.ibm.tatzia.kg.query;

public enum BinOp {
	Equal("="),
	Contains("~"),
	LessThan("<"),
	LessThanOrEqual("<="),
	GreaterThan(">"),
	GreaterThanOrEqual(">="),
	NotEqual("<>"),
	Matches("match"),
	In("IN"),
	NotIn("NOT IN"),
	LIKE("like");


	private String symbol;

	private BinOp(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
	
	public static BinOp fromString(String text) {
	    if (text != null) {
	      for (BinOp b : BinOp.values()) {
	        if (text.equalsIgnoreCase(b.symbol.replaceAll(" ", ""))) {
	          return b;
	        }
	      }
	    }
	    return null;
	  }

}
