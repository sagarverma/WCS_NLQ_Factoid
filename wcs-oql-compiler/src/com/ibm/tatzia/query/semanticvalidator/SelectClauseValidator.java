package com.ibm.tatzia.query.semanticvalidator;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.tatzia.kg.query.FunctionCall;
import com.ibm.tatzia.kg.query.OQLIdentifier;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.kg.query.Select;
import com.ibm.tatzia.kg.query.SelectColumn;
import com.ibm.tatzia.ontology.Ontology;
import com.ibm.tatzia.query.functioncatalog.Function;
import com.ibm.tatzia.query.functioncatalog.FunctionCatalogManager;
import com.ibm.tatzia.query.semanticvalidator.util.SemanticValidationUtil;

public class SelectClauseValidator implements SemanticValidator{
  
  @Override
  public void validate(OQLQuery oqlQuery, SemanticValidatorNamespace validatorNamespace,
      Ontology ontology) throws SemanticValidationException, JsonParseException, JsonMappingException, IOException {

    //ToDo: Add support for expression validation in select
    
    Select select = oqlQuery.getSelect();
    List<SelectColumn> sColumns = select.getSelectColumns();
    for (SelectColumn sColumn : sColumns) {
      Object selectValueObject = sColumn.getValue();
      if (selectValueObject instanceof OQLIdentifier) {
        OQLIdentifier oqlIdentifier = (OQLIdentifier) selectValueObject;
        boolean res = SemanticValidationUtil.validateConceptPropertyForSelect(oqlIdentifier, validatorNamespace, ontology);
        if(res) {
         // System.out.println("Validated concepts and properties in select clause.");
        }
        else{
          System.err.println("This should not have happend, an exception was not thrown.");
        }
      }
      else if(selectValueObject instanceof FunctionCall){
        FunctionCall functionCall = (FunctionCall) selectValueObject;
        String returnType = SemanticValidationUtil.validateFunctionAndGetDataType(functionCall, validatorNamespace, ontology);
        System.out.println("Validated function " + functionCall.getFunctionName() + " in select");
      }
      else{
      //throw new SemanticValidationException("Select column is neither an instance if OQLIdentifier or FunctionCall");
        System.err.println("Select column " + selectValueObject.getClass() + " is neither an instance if OQLIdentifier or FunctionCall");
      }
    }
    
  }

}
