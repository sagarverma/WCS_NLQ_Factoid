package com.ibm.in.sagaverm.gremlin;

public class FilterGremlin {
	String label;
	String alias;
	String property;
	String operator;
	Object value;
	
	public FilterGremlin(String label, String alias, String property,
			String operator, Object value) {
		super();
		this.label = label;
		this.alias = alias;
		this.property = property;
		this.operator = operator;
		this.value = value;
	}

	public FilterGremlin() {
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
