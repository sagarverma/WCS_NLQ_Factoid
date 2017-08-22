package com.ibm.in.sagaverm.gremlin;

public class RelationGremlin {
	String labelLeft;
	String aliasLeft;
	String relation;
	String labelRight;
	String aliasRight;
	public RelationGremlin(String labelLeft, String aliasLeft, String relation,
			String labelRight, String aliasRight) {
		super();
		this.labelLeft = labelLeft;
		this.aliasLeft = aliasLeft;
		this.relation = relation;
		this.labelRight = labelRight;
		this.aliasRight = aliasRight;
	}
	public RelationGremlin() {
		// TODO Auto-generated constructor stub
	}
	public String getLabelLeft() {
		return labelLeft;
	}
	public void setLabelLeft(String labelLeft) {
		this.labelLeft = labelLeft;
	}
	public String getAliasLeft() {
		return aliasLeft;
	}
	public void setAliasLeft(String aliasLeft) {
		this.aliasLeft = aliasLeft;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getLabelRight() {
		return labelRight;
	}
	public void setLabelRight(String labelRight) {
		this.labelRight = labelRight;
	}
	public String getAliasRight() {
		return aliasRight;
	}
	public void setAliasRight(String aliasRight) {
		this.aliasRight = aliasRight;
	}
	
	
	
}
