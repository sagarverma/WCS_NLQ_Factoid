package com.ibm.tatzia.query.semanticvalidator.util;

import com.ibm.tatzia.api.consume.APIConsume;
import com.ibm.tatzia.ontology.Concept;
import com.ibm.tatzia.ontology.Ontology;


public class OntologyUtil {

  private static Ontology ontology;
  private static String version;

  public OntologyUtil(String version) {
    this.version = version;
    APIConsume.setVersion(this.version);
    ontology = new Ontology(APIConsume.getAllConcepts(), APIConsume.getAllRelations(), null,
        APIConsume.getDomainRules());

  }

  public static Ontology getOntology(String version) {
    APIConsume.setVersion(version);
    if (ontology == null) {
      ontology = new Ontology(APIConsume.getAllConcepts(), APIConsume.getAllRelations(), null,
          APIConsume.getDomainRules());
    }
    return ontology;
  }


  public boolean checkConceptExist(String name) {
    if (null == ontology.getConcept(name)) {
      return false;
    } else {
      return true;
    }
  }

  public static void main(String[] args) {
    APIConsume.setVersion("FI-2.0.2");
    Concept[] a = APIConsume.getAllConcepts();
    for (Concept c : a) {
      System.out.println(c);
    }

  }
}
