package com.ibm.tatzia.query.functioncatalog;

import java.util.List;

public class Function {
  
  private String name;
  
  private List<FunctionParameterOption> parameterOptions;
  
  private String details;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<FunctionParameterOption> getParameterOptions() {
    return parameterOptions;
  }

  public void setParameterOptions(List<FunctionParameterOption> parameterOptions) {
    this.parameterOptions = parameterOptions;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public String toString() {
    return "Function [name=" + name + ", parameterOptions=" + parameterOptions + ", details=" + details + "]";
  }
}
