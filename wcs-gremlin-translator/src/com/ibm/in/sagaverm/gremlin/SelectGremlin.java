package com.ibm.in.sagaverm.gremlin;

public class SelectGremlin {
	String label;
	String alias;
	String property;
	Boolean distinct;
	String funcCall;
	String asAlias;
	
	public SelectGremlin(String label, String alias, String property,
			Boolean distinct, String funcCall, String asAlias) {
		super();
		this.label = label;
		this.alias = alias;
		this.property = property;
		this.distinct = distinct;
		this.funcCall = funcCall;
		this.asAlias = asAlias;
	}
	
	public Boolean getDistinct() {
		return distinct;
	}

	public void setDistinct(Boolean distinct) {
		this.distinct = distinct;
	}

	public String getFuncCall() {
		return funcCall;
	}

	public void setFuncCall(String funcCall) {
		this.funcCall = funcCall;
	}

	public String getAsAlias() {
		return asAlias;
	}

	public void setAsAlias(String asAlias) {
		this.asAlias = asAlias;
	}

	public SelectGremlin() {
		// TODO Auto-generated constructor stub
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	

}
