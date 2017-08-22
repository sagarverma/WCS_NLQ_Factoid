package com.ibm.in.sagaverm.discover;

import java.util.ArrayList;

public class KGTextTypes {
	ArrayList<ArrayList<String>> kg_our = new ArrayList<ArrayList<String>>();
    ArrayList<String> text_types = new ArrayList<String>();
	public KGTextTypes(ArrayList<ArrayList<String>> kg_our,
			ArrayList<String> text_types) {
		super();
		this.kg_our = kg_our;
		this.text_types = text_types;
	}
	public KGTextTypes() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<ArrayList<String>> getKg_our() {
		return kg_our;
	}
	public void setKg_our(ArrayList<ArrayList<String>> kg_our) {
		this.kg_our = kg_our;
	}
	public ArrayList<String> getText_types() {
		return text_types;
	}
	public void setText_types(ArrayList<String> text_types) {
		this.text_types = text_types;
	}
	
}
