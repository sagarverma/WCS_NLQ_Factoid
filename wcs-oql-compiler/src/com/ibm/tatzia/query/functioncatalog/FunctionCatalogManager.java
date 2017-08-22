package com.ibm.tatzia.query.functioncatalog;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FunctionCatalogManager {
  
  private ObjectMapper mapper;
  private String catalogFile = "config/catalog.json";
  private FunctionCatalog functionCatalog;
  
  
  public FunctionCatalogManager() throws JsonParseException, JsonMappingException, IOException {
    mapper = new ObjectMapper();
    loadCatalog();
  }
  
  public boolean loadCatalog() throws JsonParseException, JsonMappingException, IOException {
    File file = new File(catalogFile);
    functionCatalog = mapper.readValue(file, FunctionCatalog.class);
    return true;
  }
  
  public boolean checkFunctionExist(String functionName){
    List<Function> functions = functionCatalog.getFunctions();
    for(Function f : functions){
      if(f.getName().equals(functionName)){
        return true;
      }
    }
    return false;
  }
  
  public Function getFunction(String functionName){
    List<Function> functions = functionCatalog.getFunctions();
    for(Function f : functions){
      if(f.getName().equals(functionName)){
        return f;
      }
    }
    return null;
  }
  
  public void printCatalog() {
    if(null == functionCatalog) {
      System.out.println("Catalog not laoded.");
    }
    else{
      System.out.println(functionCatalog.toString());
    }
  }
  
}
