package com.ibm.tatzia.kg.query;

/*
 * 
 * Having clause can have selectcolumn references using column alias.  This is for that.
 */
public class ColumnRef extends Expr {
	
	String name;
	SelectColumn col; // this resolves the name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SelectColumn getCol() {
		return col;
	}
	public void setCol(SelectColumn col) {
		this.col = col;
	}
	@Override
	public String toString() {
		return "ColumnRef [name=" + name + ", col=" + col + "]";
	}
	

	
}
