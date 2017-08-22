package com.ibm.tatzia.kg.query;

import java.util.List;


public class Where {
	Expr expr;

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "Where [expr=" + expr + "]";
	}

	
	

}
