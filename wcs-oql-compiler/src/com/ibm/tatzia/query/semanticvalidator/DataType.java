package com.ibm.tatzia.query.semanticvalidator;

public enum DataType {
  
  LONG("LONG"),
  INTEGER("INTEGER");
  
  private String dataType;
  
  private DataType(String dataType) {
    this.dataType = dataType;
  }
  
  public String dataType() {
    return dataType;
  }
  
  boolean isCompatibleWith(DataType dataType){
    if(this.dataType.equals(dataType)){
      return true;
    }
    return false;
  }
  

}
