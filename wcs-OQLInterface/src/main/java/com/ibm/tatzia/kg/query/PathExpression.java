package com.ibm.tatzia.kg.query;

public class PathExpression extends Expr{
	ObjExpr lObjExpr;
	ObjExpr rObjExpr;
	JoinType type;
	public ObjExpr getlObjExpr() {
		return lObjExpr;
	}
	public void setlObjExpr(ObjExpr lObjExpr) {
		this.lObjExpr = lObjExpr;
	}
	public ObjExpr getrObject(){
		return rObjExpr;
	}
	public void setrObject(ObjExpr rObject) {
		this.rObjExpr = rObject;
	}
	
	
	public ObjExpr getrObjExpr() {
		return rObjExpr;
	}
	public void setrObjExpr(ObjExpr rObjExpr) {
		this.rObjExpr = rObjExpr;
	}
	public JoinType getType() {
		return type;
	}
	public void setType(JoinType type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "PathExpression [lObjExpr=" + lObjExpr + ", rObjExpr="
				+ rObjExpr + ", type=" + type + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lObjExpr == null) ? 0 : lObjExpr.hashCode());
		result = prime * result
				+ ((rObjExpr == null) ? 0 : rObjExpr.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PathExpression other = (PathExpression) obj;
		if (lObjExpr == null) {
			if (other.lObjExpr != null)
				return false;
		} else if (!lObjExpr.equals(other.lObjExpr))
			return false;
		if (rObjExpr == null) {
			if (other.rObjExpr != null)
				return false;
		} else if (!rObjExpr.equals(other.rObjExpr))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
}
