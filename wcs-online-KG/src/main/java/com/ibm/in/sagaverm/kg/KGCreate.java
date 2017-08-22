package com.ibm.in.sagaverm.kg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.ibm.in.sagaverm.connect.GremlinConnect;
import com.ibm.in.sagaverm.discover.DiscoveryQuery;
import com.ibm.in.sagaverm.discover.KGTextTypes;
import com.ibm.in.sagaverm.translator.OQLToGremlin;

public class KGCreate {
	public static void main(String args[]) throws IOException, JSONException{
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
				"\"subgraph\" : {\"paths\":[[\"Person\",\"isBornIn\",\"City\"],[\"Country\",\"hasState\",\"State\"],[\"State\",\"hasCity\",\"City\"]], "+
								"\"concepts\":[\"Person\",\"Country\",\"City\"]," +  
								"\"dataProperties\":{\"Person\":[\"name\"],\"Country\":[\"name\"],\"City\":[\"name\"]}}}";
		
		String input14 = "{\"dq\" : \"Abraham Lincoln\"," + 
				"\"oql\" : \"SELECT oCountry.name FROM Person oPerson , Country oCountry, City oCity WHERE oPerson.name = 'Abraham Lincoln' AND oPerson.jobTitle = 'President' AND oPerson->isBornIn = oCity AND oCountry->hasState->hasCity = oCity AND oPerson->presidentOf = oCountry\"," + 
				"\"subgraph\" : {\"paths\":[[\"Person\",\"isBornIn\",\"City\"],[\"Country\",\"hasState\",\"State\"],[\"State\",\"hasCity\",\"City\"],[\"Person\",\"presidentOf\",\"Country\"]], "+
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

		String input = input15;
		Object obj=JSONValue.parse(input);  
	    JSONObject jsonObject = (JSONObject) obj;
	    
	    String oql = (String) jsonObject.get("oql");
		String dq = (String) jsonObject.get("dq");
		HashMap<String,Object> subgraph = (HashMap<String,Object>) jsonObject.get("subgraph");
		
		
		System.out.println(oql);
		System.out.println(dq);
		
		removeTriples();
		
		/*DiscoveryQuery discoveryQuery = new DiscoveryQuery();
		KGTextTypes kgTextTypes = discoveryQuery.getKG(dq);
		
		ArrayList<FuzzyTriple> fuzzyTripleList = getFuzzyTriples(kgTextTypes);
		ArrayList<Triple> tripleList = getTriples(fuzzyTripleList, subgraph);
		insertTriples(tripleList);
		*/
		
		OQLToGremlin oqlToGemlin = new OQLToGremlin();
		String gremlinQuery = OQLToGremlin.convertOQLToGremlin(input);
		
		System.out.println(gremlinQuery);
		
		GremlinConnect gremlinConnect = new GremlinConnect();
		System.out.println(gremlinConnect.getResult(gremlinQuery).toString());
		
	}
	
		public static JSONObject getResult(String query){
			//System.out.println(query);
			Object obj=JSONValue.parse(query);  
		    JSONObject jsonObject = (JSONObject) obj;
		    
		    //String oql = (String) jsonObject.get("oql");
			String dq = (String) jsonObject.get("dq");
			HashMap<String,Object> subgraph = (HashMap<String,Object>) jsonObject.get("subgraph");
			
			
			//System.out.println(oql);
			//System.out.println(dq);
			
			//removeTriples();
			/*
			DiscoveryQuery discoveryQuery = new DiscoveryQuery();
			KGTextTypes kgTextTypes = discoveryQuery.getKG(dq);
			
			ArrayList<FuzzyTriple> fuzzyTripleList = getFuzzyTriples(kgTextTypes);
			ArrayList<Triple> tripleList = getTriples(fuzzyTripleList, subgraph);
			insertTriples(tripleList);
			*/
			
			OQLToGremlin oqlToGremlin = new OQLToGremlin();
			String gremlinQuery = oqlToGremlin.convertOQLToGremlin(query);
			
			//System.out.println(gremlinQuery);
			
			GremlinConnect gremlinConnect = new GremlinConnect();
			
			JSONArray result = new JSONArray();
			try {
				result = gremlinConnect.getResult(gremlinQuery);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONObject output = new JSONObject();
			output.put("dq", dq);
			output.put("subgraph", subgraph);
			output.put("gremlinQuery", gremlinQuery);
			output.put("result", result);
			
			return output;
		}
		
	   public static String convertCamelCase(String camelCase){
		   String out = "";
		   for (String w : camelCase.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
		        out += " " + w;
		   }
		   return out;				 
	   }
	   
	   public static ArrayList<FuzzyTriple> getFuzzyTriples(KGTextTypes kgTextTypes){
		   ArrayList<FuzzyTriple> fuzzyTripleList = new ArrayList<FuzzyTriple>();
		   for(int i=0; i < kgTextTypes.getKg_our().size(); i++){
			   String objectString = kgTextTypes.getKg_our().get(i).get(0);
			   String subjectString = kgTextTypes.getKg_our().get(i).get(2);
			   String sentence = kgTextTypes.getKg_our().get(i).get(3);
			   String objectConcepts = null;
			   String subjectConcepts = null;
			   
			   for(int j=0; j < kgTextTypes.getText_types().size(); j++){
				   if(FuzzySearch.partialRatio(objectString, kgTextTypes.getText_types().get(j)) > 30){
					   objectConcepts = kgTextTypes.getText_types().get(j);
					   break;
				   }
			   }
			   
			   for(int j=0; j < kgTextTypes.getText_types().size(); j++){
				   if(FuzzySearch.partialRatio(subjectString, kgTextTypes.getText_types().get(j)) > 30){
					   subjectConcepts = kgTextTypes.getText_types().get(j);
					   break;
				   }
			   }
			   
			   if(objectConcepts != null && subjectConcepts != null){
				   FuzzyTriple fuzzyTriple = new FuzzyTriple();
				   
				   fuzzyTriple.setObjectInstance(objectString);
				   fuzzyTriple.setObjectConcepts(objectConcepts);
				   fuzzyTriple.setSentence(sentence);
				   fuzzyTriple.setSubjectInstance(subjectString);
				   fuzzyTriple.setSubjectConcepts(subjectConcepts);
				   
				   //System.out.println(objectString + " " + objectConcepts + " " + subjectString + " " + subjectConcepts);
				   fuzzyTripleList.add(fuzzyTriple);
			   }
		   }
		   
		   return fuzzyTripleList;
	   }
	   
	   public static ArrayList<Triple> getTriples(ArrayList<FuzzyTriple> fuzzyTripleList, HashMap<String, Object> subgraph){
		   ArrayList<Triple> tripleList = new ArrayList<Triple>();
		   
		   ArrayList<ArrayList<String>> paths = (ArrayList<ArrayList<String>>) subgraph.get("paths");
		   ArrayList<String> concepts = (ArrayList<String>) subgraph.get("concepts");
		   HashMap<String,ArrayList<String>> dataProperties = (HashMap<String,ArrayList<String>>) subgraph.get("dataProperties");
		
		   for(int i=0; i < paths.size(); i++){
			   String objectConcept = paths.get(i).get(2);
			   String relation = paths.get(i).get(1);
			   String subjectConcept = paths.get(i).get(0);
			   ArrayList<String> objectDataProperties = dataProperties.get(objectConcept);
			   ArrayList<String> subjectDataProperties = dataProperties.get(subjectConcept);
			   
			   for(int j=0; j < fuzzyTripleList.size(); j++){
				   Triple triple = new Triple();
				   String objectInstance = fuzzyTripleList.get(j).getObjectInstance();
				   String subjectInstance = fuzzyTripleList.get(j).getSubjectInstance();
				   String probableObjectConcepts = fuzzyTripleList.get(j).getObjectConcepts();
				   String probableSubjectConcepts = fuzzyTripleList.get(j).getSubjectConcepts();
				   String sentence = fuzzyTripleList.get(j).getSentence();
				   HashMap<String, String> objectDataProps = new HashMap<String, String>();
				   HashMap<String, String> subjectDataProps = new HashMap<String, String>();
				   
				   if(FuzzySearch.partialRatio(convertCamelCase(objectConcept), convertCamelCase(probableObjectConcepts)) >= 30 &&
						   FuzzySearch.partialRatio(convertCamelCase(relation), convertCamelCase(sentence)) >= 30 && 
						   FuzzySearch.partialRatio(convertCamelCase(subjectConcept), convertCamelCase(probableSubjectConcepts)) >= 30){
					   triple.setObjectLabel(objectConcept);
					   triple.setSubjectLabel(subjectConcept);
					   triple.setRelation(relation);
					   objectDataProps.put("name", objectInstance);
					   subjectDataProps.put("name", subjectInstance);
					   triple.setObjectDataProp(objectDataProps);
					   triple.setSubjectDataProp(subjectDataProps);
					   //System.out.println(objectConcept + " "+ subjectConcept);
					   
					   tripleList.add(triple);
				   }
			   }
			   
		   }
		   return tripleList;
	   }

	   public static void insertTriples(ArrayList<Triple> tripleList) throws IOException, JSONException{
		 
		   GremlinConnect gremlinConnect = new GremlinConnect();
		   
		   for(int i=0; i<tripleList.size(); i++){
			   gremlinConnect.addEdgeToGremlin(tripleList.get(i));
		   }
	   }
	   
	   public static void removeTriples() throws IOException, JSONException{
		   GremlinConnect gremlinConnect = new GremlinConnect();
		   File f = new File("/home/sagar/NLQ/codes/old/res.bak");

           BufferedReader b = new BufferedReader(new FileReader(f));

           gremlinConnect.getResult("g.V().drop().iterate()");
           
           String readLine = "";

           while ((readLine = b.readLine()) != null) {
        	   //System.out.println(readLine);
        	   gremlinConnect.getResult(readLine).toString();
           }
	   }
}
