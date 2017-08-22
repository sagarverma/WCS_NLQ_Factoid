package com.ibm.tatzia.kg.query;

public class Obj {
	AliasRef aliasref;
	boolean intermediateQuery;
	OQLNestedQuery oqlQuery;
	
	
	public boolean isIntermediateQuery() {
		return intermediateQuery;
	}

	public void setIntermediateQuery(boolean intermediateQuery) {
		this.intermediateQuery = intermediateQuery;
	}

	public OQLNestedQuery getOqlQuery() {
		return oqlQuery;
	}

	public void setOqlQuery(OQLNestedQuery oqlQuery) {
		this.oqlQuery = oqlQuery;
	}

	public void setAliasref(AliasRef aliasref) {
		this.aliasref = aliasref;
	}
	public AliasRef getAliasref() {
		return aliasref;
	}

	@Override
	public String toString() {
		return "Obj [aliasref=" + aliasref + ", intermediateQuery=" + intermediateQuery + ", oqlQuery=" + oqlQuery
				+ "]";
	}
	
	
	
}
