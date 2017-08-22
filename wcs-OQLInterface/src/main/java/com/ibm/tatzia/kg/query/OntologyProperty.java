package com.ibm.tatzia.kg.query;

public class OntologyProperty {
	AliasRef aliasref;
	
	
	String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAliasref(AliasRef aliasref) {
		this.aliasref = aliasref;
	}
	public AliasRef getAliasref() {
		return aliasref;
	}

	@Override
	public String toString() {
		return "OntologyProperty [aliasref=" + aliasref + ", name=" + name + "]";
	}

	
	
}
