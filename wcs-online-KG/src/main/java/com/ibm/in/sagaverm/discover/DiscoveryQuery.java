package com.ibm.in.sagaverm.discover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.internal.LinkedTreeMap;
import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryRequest;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryResponse;

/**
 * End-to-end example for querying Discovery.
 */
public class DiscoveryQuery {

    public KGTextTypes getKG(String discoveryQuery) {
        Discovery discovery = new Discovery("2017-06-25");
        discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api");
        discovery.setUsernameAndPassword("a4cde69a-4fd7-4389-89df-6d84b265be6b", "0Zz4d58z3Ad0");
        String environmentId = "d918452f-5dc2-4165-8cc0-b9eafc7d6492";
        String configurationId = "d0506ecd-075c-4e55-8ee9-42d82b7ebb79";
        String collectionId = "e420a501-6ac2-4fd2-a70c-685627710b93";
        
        QueryRequest.Builder queryBuilder = new QueryRequest.Builder(environmentId, collectionId);
        queryBuilder.query("lincoln");
        QueryResponse queryResponse = discovery.query(queryBuilder.build()).execute();

        KGTextTypes kgTextTypes = new KGTextTypes();
        
        ArrayList<ArrayList<String>> kg_our = new ArrayList<ArrayList<String>>();
        ArrayList<String> text_types = new ArrayList<String>();
        
        for(int i=0; i < queryResponse.getResults().size(); i++){
        	Map<String, Object> result = queryResponse.getResults().get(i);
        	kg_our.addAll((ArrayList<ArrayList<String>>) result.get("kg_our"));
        	LinkedTreeMap enriched_text = (LinkedTreeMap) result.get("enriched_text");
        	ArrayList<LinkedTreeMap> entities = (ArrayList<LinkedTreeMap>)enriched_text.get("entities");
        	for(int j=0; j < entities.size(); j++){
        		String text = entities.get(j).get("text").toString();
        		String types = entities.get(j).get("type").toString();

        		if(entities.get(j).get("knowldegeGraph") != null){
        			LinkedTreeMap knowldegeGraph = (LinkedTreeMap)entities.get(j).get("knowldegeGraph");
        			String typeHierarchy = knowldegeGraph.get("typeHierarchy").toString();
        			String[] typeHierarchySplit = typeHierarchy.split("/");
        			for(int k = 0; k < typeHierarchySplit.length; k++){
        				types += " " + typeHierarchySplit[k];
        			}
        		}
        		text_types.add(text + " " + types);
        	}
        	
        }
        
        kgTextTypes.setKg_our(kg_our);
        kgTextTypes.setText_types(text_types);
        
        return kgTextTypes;
    }
    
 
}
