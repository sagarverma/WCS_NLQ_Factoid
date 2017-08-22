// � Copyright IBM Corp. 2011, 2012
package com.ibm.tatzia.kg.query;

import java.util.ArrayList;

public class FunctionCall extends Expr {
	
	// stores name of the function
	private String 		_functionName;
	private boolean		_hasDistinct;		
	
	// params can be expressions of type:
	//	 1) SQLIdentifer, 2) constant, 3) FunctionCall 
	private ArrayList<Expr> _params;
	
	
	public FunctionCall(){
		_functionName = null;
		_params = new ArrayList<Expr>();
		_hasDistinct = false;
	}
	
	public String getFunctionName(){
		return _functionName;
	}
	
	public void setHasDistinct(boolean passed){
		_hasDistinct = passed;
	}
	
	public boolean getHasDistinct(){
		return _hasDistinct;
	}
	
	public void setFunctionName(String passed){
		_functionName = passed;
	}
	
	public ArrayList<Expr> getParams(){
		return _params;
	}
	
 	public void setParams(ArrayList<Expr> passed) {
 		_params = passed;
 	}
 	
 	public void addParam(Expr passed){
 		_params.add(passed);
 	}
 		
 	public String toString(){
 		String retVal = new String();
 		
 		if (_hasDistinct)
 			retVal += " DISTINCT ";
 		retVal = _functionName;
 		if(_params.size() == 0){
 			retVal += "() ";
 		}
 		else {
 			retVal += " (";
 			retVal += _params.get(0);
 			for (int i=1 ; i<_params.size(); i++){
 				retVal += ", " + _params.get(i);
 			}
 			retVal += ") ";
 		}
 		
 		return retVal;
 	}
	
 	
} // end class 