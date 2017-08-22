package com.ibm.tatzia.kg.query;

public enum QueryType {
	Aggregation("A"),
	RegularSQL("R"),
	Entity("E"),
	Content("C");

	private String symbol;
	
	private QueryType(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return this.symbol;
	}
}
