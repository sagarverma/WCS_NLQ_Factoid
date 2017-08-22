package com.ibm.in.sagaverm.gremlin;

public class HavingGremlin {
	String alias;
	String label;
	String property;
	String function;
	String operator;
	Object value;
	public HavingGremlin(String alias, String label, String property,
			String function, String operator, Object value) {
		super();
		this.alias = alias;
		this.label = label;
		this.property = property;
		this.function = function;
		this.operator = operator;
		this.value = value;
	}
	public HavingGremlin() {
		// TODO Auto-generated constructor stub
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
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
