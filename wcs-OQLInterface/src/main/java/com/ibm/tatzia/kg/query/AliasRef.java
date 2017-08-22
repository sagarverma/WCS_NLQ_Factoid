package com.ibm.tatzia.kg.query;

public class AliasRef {

	Alias alias;
	String name;
	
	
	public void setAlias(Alias alias) {
		this.alias = alias;
	}
	public Alias getAlias() {
		return alias;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "AliasRef [alias=" + alias + "]";
	}
		
}
