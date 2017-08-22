package com.ibm.tatzia.kg.query;

public class Relation {
	
	OntologyConcept concept;
	String relationName;
	public OntologyConcept getConcept() {
		return concept;
	}
	public void setConcept(OntologyConcept concept) {
		this.concept = concept;
	}
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	@Override
	public String toString() {
		return "Relation [concept=" + concept + ", relationName=" + relationName + "]";
	}
	
	
}
