package com.ibm.in.sagaverm.gremlin;

public class GroupByGremlin {
	String label;
	String alias;
	public GroupByGremlin(String label, String alias) {
		super();
		this.label = label;
		this.alias = alias;
	}
	public GroupByGremlin() {
		// TODO Auto-generated constructor stub
	}
	String property;
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
