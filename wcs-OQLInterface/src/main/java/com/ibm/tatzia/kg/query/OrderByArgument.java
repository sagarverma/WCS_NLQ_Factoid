package com.ibm.tatzia.kg.query;

public class OrderByArgument {
	OQLIdentifier oqlOrg;
	String strOrg; // select column alias
	boolean isOQLIdentier;
	SelectColumn col; //if isOQLIdentier = false then col will point to the selectColumn: we are resolving the strOrg here
	OrderByType otype; 
	
	public void setCol(SelectColumn col) {
		this.col = col;
	}
	public SelectColumn getCol() {
		return col;
	}
	public OQLIdentifier getOqlOrg() {
		return oqlOrg;
	}
	public void setOqlOrg(OQLIdentifier oqlOrg) {
		this.oqlOrg = oqlOrg;
	}
	public String getStrOrg() {
		return strOrg;
	}
	public void setStrOrg(String strOrg) {
		this.strOrg = strOrg;
	}
	
	public boolean isOQLIdentier() {
		return isOQLIdentier;
	}
	public void setOQLIdentier(boolean isOQLIdentier) {
		this.isOQLIdentier = isOQLIdentier;
	}
	public OrderByType getOtype() {
		return otype;
	}
	public void setOtype(OrderByType otype) {
		this.otype = otype;
	}
	@Override
	public String toString() {
		return "OrderByArgument [oqlOrg=" + oqlOrg + ", strOrg=" + strOrg + ", isOQLIdentier=" + isOQLIdentier
				+ ", col=" + col + ", otype=" + otype + "]";
	}
	
	
		
}
