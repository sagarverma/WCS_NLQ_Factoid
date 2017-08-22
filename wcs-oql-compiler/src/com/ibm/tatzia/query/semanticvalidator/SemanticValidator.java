package com.ibm.tatzia.query.semanticvalidator;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.ontology.Ontology;

public interface SemanticValidator {

	void validate(OQLQuery oqlQuery, SemanticValidatorNamespace validatorNamespace, Ontology ontology) throws SemanticValidationException, JsonParseException, JsonMappingException, IOException, UnsupportedTypeException;

}
