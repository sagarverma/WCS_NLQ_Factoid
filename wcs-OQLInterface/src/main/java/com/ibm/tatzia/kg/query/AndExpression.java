package com.ibm.tatzia.kg.query;

import java.util.List;

public class AndExpression extends Expr{
	List <Expr> Exprs;

	public List<Expr> getExprs() {
		return Exprs;
	}

	public void setExprs(List<Expr> exprs) {
		Exprs = exprs;
	}

	@Override
	public String toString() {
		return "AndExpression [Exprs=" + Exprs + "]";
	}

	
}
