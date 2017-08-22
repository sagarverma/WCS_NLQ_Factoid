package com.ibm.in.sagaverm.gremlin;

import java.util.ArrayList;
import java.util.HashMap;

public class Gremlin {
	ArrayList<SelectGremlin> selectGremlinList = new ArrayList<SelectGremlin>();
	ArrayList<FromGremlin> fromGremlinList = new ArrayList<FromGremlin>();
	ArrayList<FilterGremlin> filterGremlinList = new ArrayList<FilterGremlin>();
	ArrayList<RelationGremlin> relationGremlinList = new ArrayList<RelationGremlin>();
	ArrayList<GroupByGremlin> groupByGremlinList = new ArrayList<GroupByGremlin>();
	ArrayList<OrderByGremlin> orderByGremlinList =  new ArrayList<OrderByGremlin>();
	ArrayList<HavingGremlin> havingGremlinList = new ArrayList<HavingGremlin>();
	HashMap<String, String> labelAliasMap = new HashMap<String, String>();
	public Gremlin(ArrayList<SelectGremlin> selectGremlinList,
			ArrayList<FromGremlin> fromGremlinList,
			ArrayList<FilterGremlin> filterGremlinList,
			ArrayList<RelationGremlin> relationGremlinList,
			ArrayList<GroupByGremlin> groupByGremlinList,
			ArrayList<OrderByGremlin> orderByGremlinList,
			ArrayList<HavingGremlin> havingGremlinList,
			HashMap<String, String> labelAliasMap) {
		super();
		this.selectGremlinList = selectGremlinList;
		this.fromGremlinList = fromGremlinList;
		this.filterGremlinList = filterGremlinList;
		this.relationGremlinList = relationGremlinList;
		this.groupByGremlinList = groupByGremlinList;
		this.orderByGremlinList = orderByGremlinList;
		this.havingGremlinList = havingGremlinList;
		this.labelAliasMap = labelAliasMap;
	}
	public Gremlin() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<SelectGremlin> getSelectGremlinList() {
		return selectGremlinList;
	}
	public void setSelectGremlinList(ArrayList<SelectGremlin> selectGremlinList) {
		this.selectGremlinList = selectGremlinList;
	}
	public ArrayList<FromGremlin> getFromGremlinList() {
		return fromGremlinList;
	}
	public void setFromGremlinList(ArrayList<FromGremlin> fromGremlinList) {
		this.fromGremlinList = fromGremlinList;
	}
	public ArrayList<FilterGremlin> getFilterGremlinList() {
		return filterGremlinList;
	}
	public void setFilterGremlinList(ArrayList<FilterGremlin> filterGremlinList) {
		this.filterGremlinList = filterGremlinList;
	}
	public ArrayList<RelationGremlin> getRelationGremlinList() {
		return relationGremlinList;
	}
	public void setRelationGremlinList(
			ArrayList<RelationGremlin> relationGremlinList) {
		this.relationGremlinList = relationGremlinList;
	}
	public ArrayList<GroupByGremlin> getGroupByGremlinList() {
		return groupByGremlinList;
	}
	public void setGroupByGremlinList(ArrayList<GroupByGremlin> groupByGremlinList) {
		this.groupByGremlinList = groupByGremlinList;
	}
	public ArrayList<OrderByGremlin> getOrderByGremlinList() {
		return orderByGremlinList;
	}
	public void setOrderByGremlinList(ArrayList<OrderByGremlin> orderByGremlinList) {
		this.orderByGremlinList = orderByGremlinList;
	}
	public ArrayList<HavingGremlin> getHavingGremlinList() {
		return havingGremlinList;
	}
	public void setHavingGremlinList(ArrayList<HavingGremlin> havingGremlinList) {
		this.havingGremlinList = havingGremlinList;
	}
	public HashMap<String, String> getLabelAliasMap() {
		return labelAliasMap;
	}
	public void setLabelAliasMap(HashMap<String, String> labelAliasMap) {
		this.labelAliasMap = labelAliasMap;
	}
	
	
}
