package com.ibm.in.sagaverm.translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.ibm.in.sagaverm.OQLParse;
import com.ibm.in.sagaverm.gremlin.FilterGremlin;
import com.ibm.in.sagaverm.gremlin.Gremlin;
import com.ibm.in.sagaverm.gremlin.RelationGremlin;
import com.ibm.in.sagaverm.gremlin.SelectGremlin;
import com.ibm.tatzia.kg.query.OQLQuery;

public class OQLToGremlin {
	public static void main( String[] args) throws Exception
	{
		String input1 = "{\"dq\" : \"Abraham Lincoln\"," + 
				"\"oql\" : \"SELECT oPerson.name, oPerson.jobTitle FROM Person oPerson\"," + 
				"\"subgraph\" : {\"paths\":[], "+
								"\"concepts\":[\"Person\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\",\"jobTitle\"]}}}";
				
		String input2 = "{\"dq\" : \"Abraham Lincoln\"," + 
				"\"oql\" : \"SELECT oCity.name FROM Person oPerson , City oCity WHERE oPerson.name = 'Abraham Lincoln' AND oPerson->isBornIn = oCity\"," + 
				"\"subgraph\" : {\"paths\":[[\"Person\",\"isBornIn\",\"City\"]], "+
								"\"concepts\":[\"Person\",\"City\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\"],\"City\":[\"name\"]}}}";
	
		String input3 = "{\"dq\" : \"Egypt\"," + 
				"\"oql\" : \"SELECT oCountry2.name FROM Country oCountry1 , Country oCountry2 WHERE oCountry1.name = 'Egypt' AND oCountry1->hasBorder = oCountry2\"," + 
				"\"subgraph\" : {\"paths\":[[\"Country\",\"hasBorder\",\"Country\"]], "+
								"\"concepts\":[\"Country\"]," +  
								"\"dataProperties\":{\"Country\":[\"name\"]}}}";
		
		String input4 = "{\"dq\" : \"Finland\"," + 
				"\"oql\" : \"SELECT oCity.name FROM Country oCountry , City oCity WHERE oCountry.name = 'Finland' AND oCountry->hasCapital = oCity\"," + 
				"\"subgraph\" : {\"paths\":[[\"Country\",\"hasCapital\",\"City\"]], "+
								"\"concepts\":[\"Country\",\"City\"]," +  
								"\"dataProperties\":{\"Country\":[\"name\"],\"City\":[\"name\"]}}}";
		
		String input5 = "{\"dq\" : \"Celsius\"," + 
				"\"oql\" : \"SELECT oOrganization.name FROM Person oPerson , Organization oOrganization WHERE oPerson.name = 'Anders Celsius' AND oPerson->hasAttended = oOrganization\"," + 
				"\"subgraph\" : {\"paths\":[[\"Person\",\"hasAttended\",\"Organization\"]], "+
								"\"concepts\":[\"Person\",\"Organization\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\"],\"Organization\":[\"name\"]}}}";
		
		String input6 = "{\"dq\" : \"Denmark\"," + 
				"\"oql\" : \"SELECT oCity.name FROM Country oCountry , City oCity WHERE oCountry.name = 'Denmark' AND oCountry->hasCapital = oCity\"," + 
				"\"subgraph\" : {\"paths\":[[\"Country\",\"hasCapital\",\"City\"]], "+
								"\"concepts\":[\"Country\",\"City\"]," +  
								"\"dataProperties\":{\"Country\":[\"name\"],\"City\":[\"name\"]}}}";
		
		String input7 = "{\"dq\" : \"Michael Faraday\"," + 
				"\"oql\" : \"SELECT oPerson.name , oPerson.jobTitle FROM Person oPerson WHERE oPerson.name = 'Michael Faraday'\"," + 
				"\"subgraph\" : {\"paths\":[], "+
								"\"concepts\":[\"Person\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\",\"jobTitle\"]}}}";
		
		String input8 = "{\"dq\" : \"Paraguay\"," + 
				"\"oql\" : \"SELECT oCountry.name , oCountry.inContinent FROM Country oCountry WHERE oCountry.name = 'Paraguay'\"," + 
				"\"subgraph\" : {\"paths\":[], "+
								"\"concepts\":[\"Country\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\",\"inContinent\"]}}}";
		
		String input9 = "{\"dq\" : \"Ghana\"," + 
				"\"oql\" : \"SELECT oRiver.name FROM Country oCountry , River oRiver WHERE oCountry.name = 'Ghana' AND oCountry->hasRiver = oRiver\"," + 
				"\"subgraph\" : {\"paths\":[[\"Country\",\"hasRiver\",\"River\"]], "+
								"\"concepts\":[\"Country\",\"River\"]," +  
								"\"dataProperties\":{\"Country\":[\"name\"],\"River\":[\"name\"]}}}";
		
		String input10 = "{\"dq\" : \"President\"," + 
				"\"oql\" : \"SELECT oPerson.name , oPerson.jobTitle FROM Person oPerson WHERE oPerson.jobTitle = 'President'\"," + 
				"\"subgraph\" : {\"paths\":[], "+
								"\"concepts\":[\"Person\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\",\"jobTitle\"]}}}";
		
		String input11 = "{\"dq\" : \"Plymouth\"," + 
				"\"oql\" : \"SELECT oPerson.name , oPerson.jobTitle FROM Person oPerson , City oCity WHERE oCity.name = 'Plymouth' AND oPerson->isBornIn = oCity\"," + 
				"\"subgraph\" : {\"paths\":[[\"Person\",\"isBornIn\",\"City\"]], "+
								"\"concepts\":[\"Person\",\"City\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\",\"jobTitle\"],\"City\":[\"name\"]}}}";
		
		String input12 = "{\"dq\" : \"President Assassinated\"," + 
				"\"oql\" : \"SELECT oPerson1.name , oPerson2.name FROM Person oPerson1 , Person oPerson2, Country oCountry WHERE oPerson2.jobTitle = 'President' AND oCountry.name = 'United States' AND oPerson2->presidentOf= oCountry AND oPerson1->assassinated = oPerson2\"," + 
				"\"subgraph\" : {\"paths\":[[\"Person\",\"assassinated\",\"Person\"], [\"Person\",\"presidentOf\",\"Country\"]]," +
								"\"concepts\":[\"Person\",\"Country\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\",\"jobTitle\"],\"Country\":[\"name\"]}}}";
		
		String input13 = "{\"dq\" : \"Abraham Lincoln\"," + 
				"\"oql\" : \"SELECT oPerson.name , oCountry.name FROM Person oPerson , Country oCountry , City oCity WHERE oPerson.name = 'Abraham Lincoln' AND oPerson->isBornIn = oCity AND oCountry->hasState->hasCity = oCity\"," + 
				"\"subgraph\" : {\"paths\":[[\"Person\",\"wasBornIn\",\"City\"],[\"Country\",\"hasState\",\"hasCity\",\"City\"]], "+
								"\"concepts\":[\"Person\",\"Country\",\"City\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\"],\"Country\":[\"name\"],\"City\":[\"name\"]}}}";
		
		String input14 = "{\"dq\" : \"Abraham Lincoln\"," + 
				"\"oql\" : \"SELECT oCountry.name FROM Person oPerson , Country oCountry, City oCity WHERE oPerson.name = 'Abraham Lincoln' AND oPerson.jobTitle = 'President' AND oPerson->isBornIn = oCity AND oCountry->hasState->hasCity = oCity AND oPerson->presidentOf = oCountry\"," + 
				"\"subgraph\" : {\"paths\":[[\"Person\",\"wasBornIn\",\"City\"],[\"Country\",\"hasState\",\"hasCity\",\"City\"],[\"Person\",\"presidentOf\",\"Country\"]], "+
								"\"concepts\":[\"Person\",\"Country\",\"City\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\"],\"Country\":[\"name\"],\"City\":[\"name\"]}}}";
		
		String input15 =  "{\"subgraph\":{\"dataProperties\":{\"CapitalCity\":[\"name\"],\"GeographicFeature\":[\"name\"]},"
				+ "\"concepts\":[\"GeographicFeature\",\"CapitalCity\",\"Country\"],"
				+ "\"paths\":[[\"Country\",\"hasCapitalCity\",\"CapitalCity\"],[\"Country\",\"hasRivers\",\"GeographicFeature\"]]},"
				+ "\"oql\":\"SELECT oCapitalCity.name FROM Country oCountry , CapitalCity oCapitalCity , GeographicFeature oGeographicFeature WHERE oGeographicFeature.name = 'Nile' AND oCountry->hasCapitalCity = oCapitalCity AND oCountry->hasRivers = oGeographicFeature\""
				+ ",\"dq\":\"Nile\"}";
		
		String input16 = "{\"subgraph\":{\"dataProperties\":{\"Country\":[\"inContinent\"],\"GeographicFeature\":[\"name\"]},\"concepts\":[\"GeographicFeature\",\"Country\"],\"paths\":[[\"Country\",\"hasRivers\",\"GeographicFeature\"]]},\"oql\":\"SELECT oGeographicFeature.name , oCountry.inContinent FROM Country oCountry , GeographicFeature oGeographicFeature WHERE oGeographicFeature.name = 'Nile' AND oCountry->hasRivers = oGeographicFeature\",\"dq\":\"Nile\"}";		
		
		String input17 = "{\"subgraph\":{\"dataProperties\":{\"Country\":[\"inContinent\"],\"Person\":[\"name\"]},\"concepts\":[\"Country\",\"Person\",\"City\",\"State\"],\"paths\":[[\"Person\",\"isBornIn\",\"City\"],[\"State\",\"hasCities\",\"City\"],[\"Country\",\"hasStates\",\"State\"]]},\"oql\":\"SELECT oCountry.inContinent FROM Country oCountry , Person oPerson WHERE oPerson.name = 'Michael Faraday' AND oCountry->hasStates->hasCities->isBornIn = oPerson\",\"dq\":\"Michael,Faraday\"}";
		
		String input18 = "{\"subgraph\":{\"dataProperties\":{\"Country\":[\"name\"],\"Person\":[\"name\"]},\"concepts\":[\"Country\",\"Person\",\"City\",\"State\"],\"paths\":[[\"Person\",\"isBornIn\",\"City\"],[\"State\",\"hasCities\",\"City\"],[\"Country\",\"hasStates\",\"State\"]]},\"oql\":\"SELECT oCountry.name FROM Country oCountry , Person oPerson WHERE oPerson.name = 'Michael Faraday' AND oCountry->hasStates->hasCities->isBornIn = oPerson\",\"dq\":\"Michael,Faraday\"}";
		
		String input19 = "{\"subgraph\":{\"dataProperties\":{\"Organization\":[\"name\"],\"City\":[\"name\"]},\"concepts\":[\"City\",\"Organization\",\"Person\"],\"paths\":[[\"Person\",\"isBornIn\",\"City\"],[\"Person\",\"hasAttended\",\"Organization\"]]},\"oql\":\"SELECT oOrganization.name FROM Person oPerson , Organization oOrganization , City oCity WHERE oCity.name = 'Plymouth' AND oPerson->hasAttended = oOrganization AND oPerson->isBornIn = oCity\",\"dq\":\"Plymouth\"}";

		String input20 = "{\"subgraph\":{\"dataProperties\":{\"Organization\":[\"name\"]},\"concepts\":[\"Organization\",\"President\",\"Person\"],\"paths\":[[\"Person\",\"hasAttended\",\"Organization\"],[\"Person\",\"Person_isa_President\",\"President\"]]},\"oql\":\"SELECT oOrganization.name FROM President oPresident , Organization oOrganization WHERE oPresident->Person_isa_President->hasAttended = oOrganization\",\"dq\":\"\"}";
		
		String input21 = "{\"subgraph\":{\"dataProperties\":{\"President\":[\"jobTitle\",\"name\"],\"City\":[\"name\"]},\"concepts\":[\"President\",\"City\",\"Person\"],\"paths\":[[\"Person\",\"isBornIn\",\"City\"],[\"Person\",\"Person_isa_President\",\"President\"]]},\"oql\":\"SELECT oPresident.name , oPresident.jobTitle , oCity.name FROM President oPresident , City oCity WHERE oPresident->Person_isa_President->isBornIn = oCity\",\"dq\":\"\"}";
		
		String input22 = "{\"subgraph\":{\"dataProperties\":{\"President\":[\"name\"],\"State\":[\"name\"]},\"concepts\":[\"President\",\"State\",\"Country\"],\"paths\":[[\"President\",\"presidentOf\",\"Country\"],[\"Country\",\"hasStates\",\"State\"]]},\"oql\":\"SELECT oState.name FROM President oPresident , State oState WHERE oPresident.name = 'Abraham Lincoln' AND oPresident->presidentOf->hasStates = oState\",\"dq\":\"Abraham,Lincoln\"}";
		
		String input23 = "{\"subgraph\":{\"dataProperties\":{\"Country\":[\"name\"],\"Person\":[\"jobTitle\",\"name\"]},\"concepts\":[\"Country\",\"City\",\"Person\",\"CapitalCity\"],\"paths\":[[\"City\",\"City_isa_CapitalCity\",\"CapitalCity\"],[\"Country\",\"hasCapitalCity\",\"CapitalCity\"],[\"Person\",\"isBornIn\",\"City\"]]},\"oql\":\"SELECT oPerson.name , oPerson.jobTitle FROM Country oCountry , Person oPerson WHERE oCountry.name = 'United States' AND oCountry->hasCapitalCity->City_isa_CapitalCity->isBornIn = oPerson\",\"dq\":\"United,States\"}";

		Object obj=JSONValue.parse(input1);  
	    JSONObject jsonObject = (JSONObject) obj;
	    
	    String oql = (String) jsonObject.get("oql");
		String dq = (String) jsonObject.get("dq");
		HashMap<String,Object> subgraph = (HashMap<String,Object>) jsonObject.get("subgraph");
		
		ArrayList<ArrayList<String>> paths = (ArrayList<ArrayList<String>>) subgraph.get("paths");
		ArrayList<String> concepts = (ArrayList<String>) subgraph.get("concepts");
		HashMap<String,ArrayList<String>> dataProperties = (HashMap<String,ArrayList<String>>) subgraph.get("dataProperties");
		
		OQLParse oqlParse = new OQLParse();
		
		OQLQuery oqlQuery = oqlParse.getOQLQuery(oql);
		Gremlin gremlin = oqlParse.getGremlin(oqlQuery, paths, concepts);
		
		String gremlinQuery = getGremlinQuery(gremlin);
		
		System.out.println(gremlinQuery);
		
	}
	
	public static String convertOQLToGremlin(String input){
		
		//System.out.println(input);
		Object obj=JSONValue.parse(input);  
	    JSONObject jsonObject = (JSONObject) obj;
	    
	    String oql = (String) jsonObject.get("oql");
		String dq = (String) jsonObject.get("dq");
		HashMap<String,Object> subgraph = (HashMap<String,Object>) jsonObject.get("subgraph");
		
		//System.out.println(oql.toString());
		//System.out.println(dq);
		//System.out.println(subgraph);
		
		ArrayList<ArrayList<String>> paths = (ArrayList<ArrayList<String>>) subgraph.get("paths");
		ArrayList<String> concepts = (ArrayList<String>) subgraph.get("concepts");
		HashMap<String,ArrayList<String>> dataProperties = (HashMap<String,ArrayList<String>>) subgraph.get("dataProperties");
		
		OQLParse oqlParse = new OQLParse();
		
		OQLQuery oqlQuery = oqlParse.getOQLQuery(oql);
		Gremlin gremlin = oqlParse.getGremlin(oqlQuery, paths, concepts);
		
		String gremlinQuery = getGremlinQuery(gremlin);
		
		return gremlinQuery;
	}
	
	public static String getGremlinQuery(Gremlin gremlin){
		String gremlinQuery = "g.V().match(";
		
		
		int i = 0;
		if(gremlin.getRelationGremlinList() != null){
			ArrayList<String> allPaths = getAllPaths(gremlin.getRelationGremlinList());
			
			while(i < allPaths.size() - 1){
				gremlinQuery += allPaths.get(i);
				gremlinQuery += ',';
				i += 1;
			}
			
			gremlinQuery += allPaths.get(i);	
			
			if(gremlin.getFilterGremlinList() == null){
				gremlinQuery += ",";
			}
			else if(gremlin.getFilterGremlinList().size() == 0){
				gremlinQuery += ").";
			}
			else{
				gremlinQuery += ",";
			}
		}
		
		if(gremlin.getFilterGremlinList() != null){
			i = 0;
			while(i < gremlin.getFilterGremlinList().size() - 1){
				gremlinQuery += getFilterInGremlin(gremlin.getFilterGremlinList().get(i));
				gremlinQuery += ',';
				i += 1;
			}
			
			gremlinQuery += getFilterInGremlin(gremlin.getFilterGremlinList().get(i));
			
			gremlinQuery += ",";
		}
		
		if(gremlin.getHavingGremlinList() != null){
			gremlinQuery += "__.as('";
			gremlinQuery += gremlin.getHavingGremlinList().get(0).getAlias() + "_" + gremlin.getHavingGremlinList().get(0).getProperty();
			gremlinQuery += "').";
			gremlinQuery += gremlin.getHavingGremlinList().get(0).getFunction();
			gremlinQuery += "().is(";
			gremlinQuery += gremlin.getHavingGremlinList().get(0).getOperator();
			gremlinQuery += "(";
			gremlinQuery += gremlin.getHavingGremlinList().get(0).getValue();
			gremlinQuery += ")),";
		}
		
		i = 0;
		while(i < gremlin.getSelectGremlinList().size() - 1){
			gremlinQuery += getSelectInGremlin(gremlin.getSelectGremlinList().get(i));
			gremlinQuery += ",";
			i += 1;
		}
		
		gremlinQuery += getSelectInGremlin(gremlin.getSelectGremlinList().get(i));
		gremlinQuery += ").select(";
		
		i = 0;
		while(i < gremlin.getSelectGremlinList().size() - 1){
			gremlinQuery += "'";
			gremlinQuery += gremlin.getSelectGremlinList().get(i).getAsAlias();
			gremlinQuery += "',";
			i += 1;
		}
		
		gremlinQuery += "'";
		gremlinQuery += gremlin.getSelectGremlinList().get(i).getAsAlias();
		gremlinQuery += "')";
		
		if(gremlin.getGroupByGremlinList() != null){
			gremlinQuery += ".group()";
			
			i = 0;
			while(i < gremlin.getGroupByGremlinList().size() - 1){
				gremlinQuery += ".by('";
				gremlinQuery += gremlin.getGroupByGremlinList().get(i).getAlias() + "_" + gremlin.getGroupByGremlinList().get(i).getProperty();
				gremlinQuery += "')";
				
				i += 1;
			}
			gremlinQuery += ".by('";
			gremlinQuery += gremlin.getGroupByGremlinList().get(i).getAlias() + "_" + gremlin.getGroupByGremlinList().get(i).getProperty();
			gremlinQuery += "')";	
		}
		
		if(gremlin.getOrderByGremlinList() != null){
			gremlinQuery += ".order().by('";
			gremlinQuery += gremlin.getOrderByGremlinList().get(0).getAlias() + "_" + gremlin.getOrderByGremlinList().get(0).getProperty();
			gremlinQuery += "',";
			gremlinQuery += gremlin.getOrderByGremlinList().get(0).getOrderType();
			gremlinQuery += ")";
		}
	
		return gremlinQuery;
	}
	
	
	public static ArrayList<String> getAllPaths(ArrayList<RelationGremlin> relationGremlinList){
		HashMap<String, String> paths = new HashMap<String, String>();
		
		Digraph<String> graph = new Digraph<String>();
		
		for(int i=0; i<relationGremlinList.size(); i++){
			graph.add(relationGremlinList.get(i).getAliasLeft(),relationGremlinList.get(i).getAliasRight(),relationGremlinList.get(i).getRelation());
		}
		
		System.out.println(graph);
		
		String currentNode = graph.neighbors.keySet().iterator().next();
		ArrayList<String> visited = new ArrayList<String>();
		
		LinkedList<String> queue = new LinkedList<String>();
		
		String rootPath = "__.as('" + currentNode + "')";
		paths.put(currentNode, rootPath);
		
		visited.add(currentNode);
		queue.add(currentNode);
		
		while(queue.size() != 0){
			currentNode = queue.poll();
			
			List<String> outBound = graph.outboundNeighbors(currentNode);
			for(int i=0; i < outBound.size(); i++){
				if(!visited.contains(outBound.get(i))){
					paths.put(outBound.get(i), paths.get(currentNode) + 
							".out('" + graph.getEdgeLabel(currentNode, outBound.get(i)) + "').as('" + outBound.get(i) + "')");
					visited.add(outBound.get(i));
					queue.add(outBound.get(i));
				}
			}
			
			List<String> inBound = graph.inboundNeighbors(currentNode);
			for(int i=0; i < inBound.size(); i++){
				if(!visited.contains(inBound.get(i))){
					paths.put(inBound.get(i), paths.get(currentNode) + 
							".in('" + graph.getEdgeLabel(inBound.get(i), currentNode) + "').as('" + inBound.get(i) + "')");
					visited.add(inBound.get(i));
					queue.add(inBound.get(i));
				}
			}
			
		}
		
		ArrayList<String> allPaths = new ArrayList<String>();
		Iterator i = paths.values().iterator();
		
		while(i.hasNext()){
			String temp = i.next().toString();
			System.out.println(temp);
			allPaths.add(temp);
		}
		
		return allPaths;
	}
	
	public static String getFilterInGremlin(FilterGremlin filterGremlin){
		String filterMatch = "__.as('";
		filterMatch += filterGremlin.getAlias();
		filterMatch += "').hasLabel('";
		filterMatch += filterGremlin.getLabel();
		filterMatch += "').has('";
		filterMatch += filterGremlin.getProperty();
		filterMatch += "',";
		
		if(filterGremlin.getOperator().equals("lt") || filterGremlin.getOperator().equals("gt") || 
				filterGremlin.getOperator().equals("lte") || filterGremlin.getOperator().equals("gte")){
			filterMatch += filterGremlin.getOperator();
			filterMatch += "(";
			filterMatch += filterGremlin.getValue();
			filterMatch += "))";
		}
		else if(filterGremlin.getOperator().equals("within")){
			ArrayList<Object> withinList = (ArrayList<Object>) filterGremlin.getValue();
			
			filterMatch += "within(";
			int i = 0;
			while(i < withinList.size() - 1){
				if(withinList.get(i) instanceof String){
					filterMatch += withinList.get(i);
					filterMatch += ",";
				}
				else{
					filterMatch += filterGremlin.getValue();
					filterMatch += ",";
				}
				i += 1;
			}
			if(withinList.get(i) instanceof String){
				filterMatch += "";
				filterMatch += withinList.get(i);
				filterMatch += "))";
			}
			else{
				filterMatch += filterGremlin.getValue();
				filterMatch += "))";
			}	
		}
		else{
			if(filterGremlin.getValue() instanceof String){
				filterMatch += "";
				filterMatch += filterGremlin.getValue();
				filterMatch += ")";
			}
			else{
				filterMatch += filterGremlin.getValue();
				filterMatch += ")";
			}
		}
		return filterMatch;
	}
	
	public static String getSelectInGremlin(SelectGremlin selectGremlin){
		String selectMatch = "__.as('";
		selectMatch += selectGremlin.getAlias();
		selectMatch += "').hasLabel('";
		selectMatch += selectGremlin.getLabel();
		selectMatch += "').values('";
		selectMatch += selectGremlin.getProperty();
		selectMatch += "')";
		
		if(selectGremlin.getDistinct()){
			selectMatch += ".dedup()";
		}
		
		if(selectGremlin.getFuncCall() != null){
			selectMatch += ".";
			selectMatch += selectGremlin.getFuncCall();
			selectMatch += "()";
		}
		
		selectMatch += ".as('";
		selectMatch += selectGremlin.getAsAlias();
		selectMatch += "')";
		
		return selectMatch;
	}
}
