package com.ibm.in.sagaverm.kg;

import java.util.HashMap;

public class Triple {
	String subjectLabel;
	HashMap<String, String> subjectDataProp;
	String relation;
	String objectLabel;
	HashMap<String, String> objectDataProp;
	
	public Triple(String subjectLabel, HashMap<String, String> subjectDataProp,
			String relation, String objectLabel,
			HashMap<String, String> objectDataProp) {
		super();
		this.subjectLabel = subjectLabel;
		this.subjectDataProp = subjectDataProp;
		this.relation = relation;
		this.objectLabel = objectLabel;
		this.objectDataProp = objectDataProp;
	}
	
	public Triple() {
		// TODO Auto-generated constructor stub
	}

	public String getSubjectLabel() {
		return subjectLabel;
	}
	public void setSubjectLabel(String subjectLabel) {
		this.subjectLabel = subjectLabel;
	}
	public HashMap<String, String> getSubjectDataProp() {
		return subjectDataProp;
	}
	public void setSubjectDataProp(HashMap<String, String> subjectDataProp) {
		this.subjectDataProp = subjectDataProp;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getObjectLabel() {
		return objectLabel;
	}
	public void setObjectLabel(String objectLabel) {
		this.objectLabel = objectLabel;
	}
	public HashMap<String, String> getObjectDataProp() {
		return objectDataProp;
	}
	public void setObjectDataProp(HashMap<String, String> objectDataProp) {
		this.objectDataProp = objectDataProp;
	}
	
}
