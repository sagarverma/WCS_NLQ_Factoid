package com.ibm.tatzia.kg.query;

public class CompExpr extends Expr{
	
	Expr leftExpr;
	BinOp op;
	Expr rightExpr;
	public Expr getLeftExpr() {
		return leftExpr;
	}
	public void setLeftExpr(Expr leftExpr) {
		this.leftExpr = leftExpr;
	}
	public BinOp getOp() {
		return op;
	}
	public void setOp(BinOp op) {
		this.op = op;
	}
	public Expr getRightExpr() {
		return rightExpr;
	}
	public void setRightExpr(Expr rightExpr) {
		this.rightExpr = rightExpr;
	}
	@Override
	public String toString() {
		return "CompExpr [leftExpr=" + leftExpr + ", op=" + op + ", rightExpr=" + rightExpr + "]";
	}

	
}
