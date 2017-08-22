package com.ibm.in.sagaverm.kg;

import java.util.ArrayList;

public class FuzzyTriple {
	String objectInstance;
	String objectConcepts;
	String sentence;
	String subjectInstance;
	String subjectConcepts;
	
	public FuzzyTriple() {
		// TODO Auto-generated constructor stub
	}

	public FuzzyTriple(String objectInstance, String objectConcepts,
			String sentence, String subjectInstance, String subjectConcepts) {
		super();
		this.objectInstance = objectInstance;
		this.objectConcepts = objectConcepts;
		this.sentence = sentence;
		this.subjectInstance = subjectInstance;
		this.subjectConcepts = subjectConcepts;
	}

	public String getObjectInstance() {
		return objectInstance;
	}

	public void setObjectInstance(String objectInstance) {
		this.objectInstance = objectInstance;
	}

	public String getObjectConcepts() {
		return objectConcepts;
	}

	public void setObjectConcepts(String objectConcepts) {
		this.objectConcepts = objectConcepts;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getSubjectInstance() {
		return subjectInstance;
	}

	public void setSubjectInstance(String subjectInstance) {
		this.subjectInstance = subjectInstance;
	}

	public String getSubjectConcepts() {
		return subjectConcepts;
	}

	public void setSubjectConcepts(String subjectConcepts) {
		this.subjectConcepts = subjectConcepts;
	}
		
}
