package com.ibm.tatzia.kg.response;

import java.util.ArrayList;
import java.util.List;

public class Response {
	String message;
	List<String> backendQueries;
	List<Answer> answers;
	String KGQL;
	
	public Response(String message) {
		super();
		this.message = message;
		this.backendQueries = new ArrayList<String>();
		this.answers = new ArrayList<Answer>();
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getBackendQueries() {
		return backendQueries;
	}
	public void setBackendQueries(List<String> backendQueries) {
		this.backendQueries = backendQueries;
	}
	public void addBackendQuery(String backendQuery) {
		this.backendQueries.add(backendQuery);
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	public void addAnswer(Answer answer) {
		this.answers.add(answer);
	}

	public String getKGQL() {
		return KGQL;
	}

	public void setKGQL(String kGQL) {
		KGQL = kGQL;
	}

		
	
}
