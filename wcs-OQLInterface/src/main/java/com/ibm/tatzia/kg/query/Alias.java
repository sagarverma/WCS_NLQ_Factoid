package com.ibm.tatzia.kg.query;

import com.ibm.tatzia.kg.query.OQLNestedQuery;
import com.ibm.tatzia.kg.query.OntologyConcept;

public class Alias {
	OQLNestedQuery intermediate = null;
	OntologyConcept o = null;
	int id;
	static int count =0;
	String name;

	public Alias() {
		id=count++;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OQLNestedQuery getIntermediate() {
		return intermediate;
	}
	public void setIntermediate(OQLNestedQuery intermediate) {
		this.intermediate = intermediate;
	}

	public OntologyConcept getO() {
		return o;
	}

	public void setO(OntologyConcept o) {
		this.o = o;
	}
	@Override
	public String toString() {
		return "Alias [id=" + id + ", name=" + name + "]";
	}

	
}
