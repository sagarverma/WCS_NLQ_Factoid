package com.ibm.tatzia.query.functioncatalog;

import java.util.List;

public class FunctionParameterOption {
  
  private List<String> input;
  private String output;
  
  public List<String> getInput() {
    return input;
  }
  public void setInput(List<String> input) {
    this.input = input;
  }
  public String getOutput() {
    return output;
  }
  public void setOutput(String output) {
    this.output = output;
  }
  @Override
  public String toString() {
    return "FunctionParameter [input=" + input + ", output=" + output + "]";
  }
  
}
