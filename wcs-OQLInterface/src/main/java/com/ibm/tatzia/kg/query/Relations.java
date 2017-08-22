package com.ibm.tatzia.kg.query;

import java.util.List;

public class Relations {
	
	//relations: RELATION relation (COMMA relation)*;
	//relation: concept relationName;
	List<Relation> relations;

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	@Override
	public String toString() {
		return "Relations [relations=" + relations + "]";
	}
	
}
