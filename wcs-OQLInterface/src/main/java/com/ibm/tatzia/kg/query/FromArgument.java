package com.ibm.tatzia.kg.query;

public class FromArgument{
	private OQLNestedQuery intermediateConcept;
	private OntologyConcept concept;
	private Alias alias;
	private boolean isIntermediate;
	public OQLNestedQuery getIntermediateConcept() {
		return intermediateConcept;
	}
	public void setIntermediateConcept(OQLNestedQuery intermediateConcept) {
		this.intermediateConcept = intermediateConcept;
	}
	public OntologyConcept getConcept() {
		return concept;
	}
	public void setConcept(OntologyConcept concept) {
		this.concept = concept;
	}
	public Alias getAlias() {
		return alias;
	}
	public void setAlias(Alias alias) {
		this.alias = alias;
	}
	public boolean isIntermediate() {
		return isIntermediate;
	}
	public void setIntermediate(boolean isIntermediate) {
		this.isIntermediate = isIntermediate;
	}
	@Override
	public String toString() {
		return "FromArgument [intermediateConcept=" + intermediateConcept + ", concept=" + concept + ", alias=" + alias
				+ ", isIntermediate=" + isIntermediate + "]";
	}
	
	
}
