package com.ibm.tatzia.query.parser;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.ontology.Ontology;
import com.ibm.tatzia.query.semanticvalidator.ConceptPropertyValidator;
import com.ibm.tatzia.query.semanticvalidator.FromClauseValidator;
import com.ibm.tatzia.query.semanticvalidator.FunctionValidator;
import com.ibm.tatzia.query.semanticvalidator.SelectClauseValidator;
import com.ibm.tatzia.query.semanticvalidator.SemanticValidationException;
import com.ibm.tatzia.query.semanticvalidator.SemanticValidatorNamespace;
import com.ibm.tatzia.query.semanticvalidator.UnsupportedTypeException;
import com.ibm.tatzia.query.semanticvalidator.WhereClauseValidator;
import com.ibm.tatzia.query.semanticvalidator.util.OntologyUtil;

public class OQLValidationOrchestrator {
  
  private SemanticValidatorNamespace validatorNamespace;
  private Ontology ontology;
  
  public void validateOQL(OQLQuery oqlQuery) throws JsonParseException, JsonMappingException, IOException, SemanticValidationException, UnsupportedTypeException {
    ontology = OntologyUtil.getOntology("FI-0.0.7");
    validatorNamespace = new SemanticValidatorNamespace();
    
    //Sequence matters
    FromClauseValidator fromClauseValidator = new FromClauseValidator();
    fromClauseValidator.validate(oqlQuery, validatorNamespace, ontology);
    // add return type in select if one select column
    SelectClauseValidator selectClauseValidator = new SelectClauseValidator();
    selectClauseValidator.validate(oqlQuery, validatorNamespace, ontology);
    WhereClauseValidator whereClauseValidator = new WhereClauseValidator();
    whereClauseValidator.validate(oqlQuery, validatorNamespace, ontology);
    
  }
  
}
