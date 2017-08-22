package com.ibm.tatzia.kg.response;

import java.util.ArrayList;
import java.util.List;

public class AnswerData {
	Integer numOfRows;
	List<AnswerDataRow> rows;
	
	public AnswerData() {
		super();
		this.rows = new ArrayList<AnswerDataRow>();
	}
	
	public Integer getNumOfRows() {
		return numOfRows;
	}
	public void setNumOfRows(Integer numOfRows) {
		this.numOfRows = numOfRows;
	}
	public List<AnswerDataRow> getRows() {
		return rows;
	}
	public void setRows(List<AnswerDataRow> rows) {
		this.rows = rows;
	}
	public void addRow(AnswerDataRow row) {
		this.rows.add(row);
	}
	
	
}
