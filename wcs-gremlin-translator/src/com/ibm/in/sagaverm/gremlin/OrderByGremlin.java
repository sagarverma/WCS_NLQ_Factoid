package com.ibm.in.sagaverm.gremlin;

public class OrderByGremlin {
	String label;
	String alias;
	String property;
	String orderType;
	public OrderByGremlin(String label, String alias, String property,
			String orderType) {
		super();
		this.label = label;
		this.alias = alias;
		this.property = property;
		this.orderType = orderType;
	}
	public OrderByGremlin() {
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
