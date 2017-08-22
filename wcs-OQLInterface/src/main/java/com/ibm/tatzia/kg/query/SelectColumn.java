// � Copyright IBM Corp. 2011, 2012
package com.ibm.tatzia.kg.query;

public class SelectColumn {
	
	private Expr 	_value; // yah not SelectExpr
	private String 		_alias;
	
	public SelectColumn(){
		_value 		 = null;
		_alias 		 = null;
	}
	
	public String getAlias(){
		return _alias;
	}
		
	public void setAlias(String passed){
		_alias = passed;
	}
	
	public Object getValue(){
		return _value;
	}
	
	public void setValue(Expr passed){
		_value = passed;
	}
	
 	
	@Override
	public String toString() {
		return "SelectColumn [_value=" + _value + ", _alias=" + _alias + "]";
	}

	public String getSyntax() {
		// TODO Auto-generated method stub
		return null;
	}
	
} // end class 
