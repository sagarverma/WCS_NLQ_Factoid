package com.ibm.tatzia.kg.query;

import java.util.List;

public class ObjExpr {

	Obj object;
	List<String> relations;
	public Obj getObject() {
		return object;
	}
	public void setObject(Obj object) {
		this.object = object;
	}
	
	public List<String> getRelations() {
		return relations;
	}
	public void setRelations(List<String> relations) {
		this.relations = relations;
	}
	@Override
	public String toString() {
		return "ObjExpr [object=" + object + ", relations=" + relations + "]";
	}
	
	
}
