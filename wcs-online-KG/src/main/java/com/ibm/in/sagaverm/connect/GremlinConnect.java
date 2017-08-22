package com.ibm.in.sagaverm.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.ibm.in.sagaverm.kg.Triple;

public class GremlinConnect {
	String input_start = "{\"gremlin\": \"";
	String input_end = "\", \"bindings\": {},   \"language\": \"gremlin-groovy\"}";
	
	public HttpURLConnection getConnection() throws IOException{
		URL url;
		HttpURLConnection conn;
		
		url = new URL("http://9.109.125.63:8182");
		conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept","*/*");
		
		return conn;
	}
	
	public String getVertexQuery(String label, HashMap<String, String> dataProperties){
		String query = "g.V().hasLabel('";
		query += label + "')";
		Iterator it = dataProperties.entrySet().iterator();
		while(it.hasNext()){
			HashMap.Entry pair = (HashMap.Entry)it.next();
	        query += ".has('" + pair.getKey() + "','" + pair.getValue() + "')";
		}
		
		return query;
	}
	
	public boolean vertexExist(String label, HashMap<String, String> dataProperties) throws IOException, JSONException{
		HttpURLConnection conn = getConnection();
		OutputStream os = conn.getOutputStream();
		os.write((this.input_start + getVertexQuery(label, dataProperties) + this.input_end).getBytes());
		os.flush();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		while ((output = br.readLine()) != null) {
			JSONObject jsonObject = new JSONObject(output);
			JSONObject result = (JSONObject) jsonObject.get("result");
			JSONArray data = (JSONArray)result.get("data");
			if(data.length() == 0){
				return false;
			}
			else{
				return true;
			}
		}

		conn.disconnect();
		return false;
	}
	
	public String getAddVertexQuery(String label, HashMap<String, String> dataProperties){
		String query = "graph.addVertex(label,'";
		query += label + "'";
		Iterator it = dataProperties.entrySet().iterator();
		while(it.hasNext()){
			HashMap.Entry pair = (HashMap.Entry)it.next();
	        query += ",'" + pair.getKey() + "','" + pair.getValue() + "'";
		}
		
		query += ")";
		
		return query;
	}
	
	public void addVertexToGremlin(String label, HashMap<String, String> dataProperties) throws IOException, JSONException{
		if(!vertexExist(label, dataProperties)){
			HttpURLConnection conn = getConnection();
			String addVertexQuery = getAddVertexQuery(label, dataProperties);
			OutputStream os = conn.getOutputStream();
			os.write((this.input_start + addVertexQuery + this.input_end).getBytes());
			os.flush();
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			conn.disconnect();
		}
	}
	
	public String getEdgeQuery(Triple triple){
		String query = getVertexQuery(triple.getSubjectLabel(), triple.getSubjectDataProp());
		query += ".out('";
		query += triple.getRelation();
		query += "').hasLabel('";
		query += triple.getObjectLabel();
		query += "')";
		Iterator it = triple.getObjectDataProp().entrySet().iterator();
		while(it.hasNext()){
			HashMap.Entry pair = (HashMap.Entry)it.next();
	        query += ".has('" + pair.getKey() + "','" + pair.getValue() + "')";
		}
		return query;
	}
	
	public boolean edgeExist(Triple triple) throws IOException, JSONException{
		HttpURLConnection conn = getConnection();
		OutputStream os = conn.getOutputStream();
		os.write((this.input_start + getEdgeQuery(triple) + this.input_end).getBytes());
		os.flush();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		while ((output = br.readLine()) != null) {
			JSONObject jsonObject = new JSONObject(output);
			JSONObject result = (JSONObject) jsonObject.get("result");
			JSONArray data = (JSONArray)result.get("data");
			if(data.length() == 0){
				return false;
			}
			else{
				return true;
			}
		}

		conn.disconnect();
		return false;
	}
	
	public void addEdgeToGremlin(Triple triple) throws IOException, JSONException{
		if(edgeExist(triple)){
			return;
		}
		else{
			addVertexToGremlin(triple.getSubjectLabel(), triple.getSubjectDataProp());
			addVertexToGremlin(triple.getObjectLabel(), triple.getObjectDataProp());
			
			String query = getVertexQuery(triple.getSubjectLabel(), triple.getSubjectDataProp());
			query += ".next().addEdge('";
			query += triple.getRelation();
			query += "',g.V().hasLabel('";
			query += triple.getObjectLabel();
			query += "')";
			
			Iterator it = triple.getObjectDataProp().entrySet().iterator();
			while(it.hasNext()){
				HashMap.Entry pair = (HashMap.Entry)it.next();
		        query += ".has('" + pair.getKey() + "','" + pair.getValue() + "')";
			}
			query += ".next())";
			
			//System.out.println(query);
			HttpURLConnection conn = getConnection();
			OutputStream os = conn.getOutputStream();
			os.write((this.input_start + query + this.input_end).getBytes());
			os.flush();
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			conn.disconnect();
		}
	}
	
	public JSONArray getResult(String gremlinQuery) throws IOException, JSONException{
		HttpURLConnection conn = getConnection();
		OutputStream os = conn.getOutputStream();
		os.write((this.input_start + gremlinQuery + this.input_end).getBytes());
		//System.out.print(this.input_start + gremlinQuery + this.input_end);
		os.flush();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		JSONArray data = new JSONArray();
		while ((output = br.readLine()) != null) {
			JSONObject jsonObject = new JSONObject(output);
			JSONObject result = (JSONObject) jsonObject.get("result");
			data = (JSONArray)result.get("data");
		}

		conn.disconnect();
		
		return data;
	}
}
