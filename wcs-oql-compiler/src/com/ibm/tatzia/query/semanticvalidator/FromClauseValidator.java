package com.ibm.tatzia.query.semanticvalidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibm.tatzia.kg.query.From;
import com.ibm.tatzia.kg.query.FromArgument;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.kg.query.OntologyConcept;
import com.ibm.tatzia.ontology.Concept;
import com.ibm.tatzia.ontology.Ontology;

public class FromClauseValidator implements SemanticValidator {

  @Override
  public void validate(OQLQuery oqlQuery, SemanticValidatorNamespace validatorNamespace,
      Ontology ontology) throws SemanticValidationException {

    if(null == oqlQuery) {
      throw new IllegalArgumentException("Oql query to be validated cannot be null");
    }
    From from = oqlQuery.getFrom();
    List<FromArgument> fromArgumentList = from.getFromClause();

    HashMap<String, Concept> aliasConceptMap = new HashMap<String, Concept>();
    List<String> fromConceptList = new ArrayList<String>();

    for (FromArgument fromArgument : fromArgumentList) {
      OntologyConcept ontologyConcept = fromArgument.getConcept();
      String conceptName = ontologyConcept.getName();
      String conceptAlias = fromArgument.getAlias().getName();
      Concept concept = ontology.getConcept(conceptName);

      // Check concept exist in ontology
      if (ontology.getConcept(conceptName) != null) {
        aliasConceptMap.put(conceptAlias, concept);
        fromConceptList.add(conceptName);
      } else {
        throw new SemanticValidationException(
            "Concept Name :: " + conceptName + " :: in the From Clause is Invalid");
      }
      validatorNamespace.setAliasConceptMap(aliasConceptMap);
    }
  }

}
