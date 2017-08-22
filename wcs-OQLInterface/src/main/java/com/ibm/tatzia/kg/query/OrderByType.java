package com.ibm.tatzia.kg.query;

public enum OrderByType {
	
	Descending("DESC"),
	Ascending("ASC");

	private String symbol;

	private OrderByType(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return symbol;
	}
}
