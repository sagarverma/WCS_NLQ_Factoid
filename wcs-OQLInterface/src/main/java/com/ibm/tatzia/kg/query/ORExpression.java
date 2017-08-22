package com.ibm.tatzia.kg.query;

import java.util.List;

public class ORExpression extends Expr{
	List<Expr> exprs;

	public List<Expr> getExprs() {
		return exprs;
	}

	public void setExprs(List<Expr> exprs) {
		this.exprs = exprs;
	}

	@Override
	public String toString() {
		return "ORExpression [exprs=" + exprs + "]";
	}


	
}
