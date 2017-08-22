package com.ibm.tatzia.query.functioncatalog;

import java.util.List;

public class FunctionCatalog {
  private String catalogVersion;
  private List<Function> functions;
  
  public String getCatalogVersion() {
    return catalogVersion;
  }
  public void setCatalogVersion(String catalogVersion) {
    this.catalogVersion = catalogVersion;
  }
  public List<Function> getFunctions() {
    return functions;
  }
  public void setFunctions(List<Function> functions) {
    this.functions = functions;
  }
  @Override
  public String toString() {
    return "FunctionCatalog [catalogVersion=" + catalogVersion + ", functions=" + functions + "]";
  }

}
