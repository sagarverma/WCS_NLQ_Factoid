package com.ibm.tatzia.query.semanticvalidator;

import java.util.HashMap;

import com.ibm.tatzia.kg.query.OntologyConcept;
import com.ibm.tatzia.ontology.Concept;

public class SemanticValidatorNamespace {
  
  HashMap<String, Concept> aliasConceptMap;

  public HashMap<String, Concept> getAliasConceptMap() {
    return aliasConceptMap;
  }

  public void setAliasConceptMap(HashMap<String, Concept> aliasConceptMap) {
    this.aliasConceptMap = aliasConceptMap;
  }

}
