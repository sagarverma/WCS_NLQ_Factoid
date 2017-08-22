package com.ibm.tatzia.kg.response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Answer implements Comparable<Answer> {

	// Answer meta-data
	private String id;
	private AnswerType type;
	private String title;
	private double confidence;
	private List<Field> fields;
	
	// Data answer
	private AnswerData answerData;
		
	// Text answer
	private AnswerText answerText;
	
	
	public Answer(String id) {
		this.id = id;
		this.fields = new ArrayList<Field>();
	}

	public int compareTo(Answer answer) {
		return getId().compareTo(answer.getId());
	}

	@Override
	public boolean equals(Object o) {
		  if (!(o instanceof Answer)) {
			  return false;
		  }
		  
		  Answer answer = (Answer) o;
		  return compareTo(answer) == 0;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		return this.id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String value) {
		this.title = value;
	}
	
	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double value) {
		this.confidence = value;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public void addField(Field field) {
		this.fields.add(field);
	}

	public AnswerData getAnswerData() {
		return answerData;
	}

	public void setAnswerData(AnswerData answerData) {
		this.answerData = answerData;
	}

	public AnswerText getAnswerText() {
		return answerText;
	}

	public void setAnswerText(AnswerText answerText) {
		this.answerText = answerText;
	}

	public AnswerType getType() {
		return type;
	}

	public void setType(AnswerType type) {
		this.type = type;
	}
		
	public static class ConfidenceComparator implements Comparator<Answer> {
		public int compare(Answer answer1, Answer answer2) {
			double confidence1 = answer1.getConfidence();
			double confidence2 = answer2.getConfidence();

			if (confidence1 < confidence2) {
				return +1;
			} else if (confidence1 > confidence2) {
				return -1;
			} else {
				return  0;
			}				
		}		
	};
	
	
}
