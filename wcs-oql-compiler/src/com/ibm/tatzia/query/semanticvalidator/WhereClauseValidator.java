package com.ibm.tatzia.query.semanticvalidator;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.tatzia.kg.query.Expr;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.kg.query.ORExpression;
import com.ibm.tatzia.kg.query.Where;
import com.ibm.tatzia.ontology.Ontology;
import com.ibm.tatzia.query.semanticvalidator.util.SemanticValidationUtil;

public class WhereClauseValidator implements SemanticValidator{

  @Override
  public void validate(OQLQuery oqlQuery, SemanticValidatorNamespace validatorNamespace,
      Ontology ontology)
      throws SemanticValidationException, JsonParseException, JsonMappingException, IOException, UnsupportedTypeException {
    Where where = oqlQuery.getWhereClause();
    Expr expr = where.getExpr();
    SemanticValidationUtil.validateExpr(expr, validatorNamespace, ontology); 
  }

}
