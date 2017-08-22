package com.ibm.tatzia.query.semanticvalidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.tatzia.kg.query.Expr;
import com.ibm.tatzia.kg.query.FunctionCall;
import com.ibm.tatzia.kg.query.OQLIdentifier;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.kg.query.OntologyConcept;
import com.ibm.tatzia.kg.query.Select;
import com.ibm.tatzia.kg.query.SelectColumn;
import com.ibm.tatzia.ontology.Concept;
import com.ibm.tatzia.ontology.Ontology;
import com.ibm.tatzia.ontology.Property;
import com.ibm.tatzia.query.functioncatalog.Function;
import com.ibm.tatzia.query.functioncatalog.FunctionCatalogManager;
import com.ibm.tatzia.query.semanticvalidator.util.OntologyUtil;


public class FunctionValidator implements SemanticValidator {

  private OQLQuery oqlQuery;
//  private Ontology ontology;
  private FunctionCatalogManager functionCatalogManager;

  public FunctionValidator() throws JsonParseException, JsonMappingException, IOException {
  //  this.ontology = OntologyUtil.getOntology("FI-");
    functionCatalogManager = new FunctionCatalogManager();
  }

  @Override
  public void validate(OQLQuery oqlQuery, SemanticValidatorNamespace semanticValidatorResponse, Ontology ontology)
      throws SemanticValidationException {

    HashMap<String, Concept> aliasConceptMap = semanticValidatorResponse.getAliasConceptMap();


    Select select = oqlQuery.getSelect();
    List<SelectColumn> sColumns = select.getSelectColumns();
    System.out.println("Will be printing columns");
    for (SelectColumn sColumn : sColumns) {
      Object selectValueObject = sColumn.getValue();
      System.out.println(sColumn);

      if (selectValueObject instanceof FunctionCall) {

        FunctionCall functionCall = (FunctionCall) selectValueObject;
        String name = functionCall.getFunctionName();
        Function function = functionCatalogManager.getFunction(name);
        if (null == function || !function.getName().equals(name)) {
          throw new SemanticValidationException("Function " + name + " is not supported");
        }

        List<Expr> params = functionCall.getParams();
        
        
        
        OQLIdentifier parameter = (OQLIdentifier) params.get(0);
        System.out.println("Print param");
        System.out.println(params);

        String aliasName = parameter.getAliasref().getName();
        String propertyName = parameter.getProperty().getName();
        System.out.println(aliasName + " " + propertyName);
        Concept concept = aliasConceptMap.get(aliasName);
        Property property = ontology.getProperty(propertyName, concept);
        String dataType = property.getDatatype();
        System.out.println(dataType);
      }
    }
  }
  
  private List<String> validateParametersAndDataTypes(List<Expr> parameters, List<String> inputDataTypes) {
    List<String> dataTypes = new ArrayList<String>();
    for(Expr parameter : parameters) {
      
    }
    return null;
  }
  
}
