package com.ibm.tatzia.kg.query;

public class ParenExpr extends Expr {
	
	Expr expr;

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "ParenExpr [expr=" + expr + "]";
	}
	
	
}
