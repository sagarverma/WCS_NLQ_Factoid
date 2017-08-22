// � Copyright IBM Corp. 2011, 2012
package com.ibm.tatzia.kg.query;

import java.util.ArrayList;

public class ListExpression extends Expr {
	
	private ArrayList<Expr> _elements;	
	public ListExpression(){
		_elements = new ArrayList<Expr>();
	}
	
	public ArrayList<Expr> getElements(){
		return _elements;
	}
	
	public void addElement(Expr passed){
		_elements.add(passed);
	}
	
	public String toString(){
		String retVal = new String();
		retVal += " (";
		
		if (_elements.size() > 0)
			retVal += _elements.get(0).toString();
		
		for (int i=1 ; i<_elements.size(); i++){
			retVal += ", " + _elements.get(i).toString();
		}
		retVal += ") ";
		
		return retVal;
		
	}
}