// � Copyright IBM Corp. 2011, 2012
package com.ibm.tatzia.kg.query;

import com.ibm.tatzia.ontology.DataType;

public class ConstantVal extends Expr {
	
	String _value;  // this can be prefixed with + and -, so -1, +1, 1 are all valid strings
	DataType type;
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}
	public ConstantVal(String passed){
		_value = passed;
	}
	public void add(String passed){
		_value+=passed;
	}
	
	public String getValue(){
		return _value;
	}
	
	public String toString(){
		return " Const: " + _value + " ";
	}
	
}