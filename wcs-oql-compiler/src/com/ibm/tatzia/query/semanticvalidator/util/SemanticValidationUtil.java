package com.ibm.tatzia.query.semanticvalidator.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.tatzia.kg.query.AndExpression;
import com.ibm.tatzia.kg.query.CompExpr;
import com.ibm.tatzia.kg.query.ConstantVal;
import com.ibm.tatzia.kg.query.Expr;
import com.ibm.tatzia.kg.query.Expression;
import com.ibm.tatzia.kg.query.FunctionCall;
import com.ibm.tatzia.kg.query.ListExpression;
import com.ibm.tatzia.kg.query.NotExpression;
import com.ibm.tatzia.kg.query.OQLIdentifier;
import com.ibm.tatzia.kg.query.ORExpression;
import com.ibm.tatzia.kg.query.OntologyProperty;
import com.ibm.tatzia.kg.query.PathExpression;
import com.ibm.tatzia.ontology.Concept;
import com.ibm.tatzia.ontology.Ontology;
import com.ibm.tatzia.ontology.Property;
import com.ibm.tatzia.query.functioncatalog.Function;
import com.ibm.tatzia.query.functioncatalog.FunctionCatalogManager;
import com.ibm.tatzia.query.functioncatalog.FunctionParameterOption;
import com.ibm.tatzia.query.semanticvalidator.DataType;
import com.ibm.tatzia.query.semanticvalidator.SemanticValidationException;
import com.ibm.tatzia.query.semanticvalidator.SemanticValidatorNamespace;
import com.ibm.tatzia.query.semanticvalidator.UnsupportedTypeException;

public class SemanticValidationUtil {

  private static FunctionCatalogManager functionCatalogManager;

  
  public static boolean validateExpr(Expr expr,
      SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException, JsonParseException, JsonMappingException, IOException, UnsupportedTypeException {
    
    if(expr instanceof ORExpression){
      validateOrExpr((ORExpression)expr, validatorNamespace, ontology);
    }
    else if(expr instanceof AndExpression){
      validateAndExpr((AndExpression)expr, validatorNamespace, ontology);
    }
    else if(expr instanceof NotExpression){
      validateNotExpr((NotExpression)expr, validatorNamespace, ontology);
    }
    else if(expr instanceof CompExpr){
      validateCompExpr((CompExpr)expr, validatorNamespace, ontology);
    }
    else if(expr instanceof PathExpression){
      validatePathExpr((PathExpression)expr, validatorNamespace, ontology);;
    }
    else{
      String message = "Unsupported type: " + expr.getClass() + " " + expr;
      throw new UnsupportedTypeException(message);
    }
    return true;
  }
  
  private static boolean validateOrExpr(ORExpression orExpression,
      SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException, JsonParseException, JsonMappingException, IOException, UnsupportedTypeException {
    List<Expr> exprs = orExpression.getExprs();
    for(Expr expr : exprs){
      validateExpr(expr, validatorNamespace, ontology);
    }
    return true;
  }
  
  private static boolean validateAndExpr(AndExpression andExpr,
      SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException, JsonParseException, JsonMappingException, IOException, UnsupportedTypeException {
    List<Expr> exprs = andExpr.getExprs();
    for(Expr expr : exprs){
      validateExpr(expr, validatorNamespace, ontology);
    }
    return true;
  }
  
  private static boolean validateNotExpr(NotExpression notExpr,
      SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException, JsonParseException, JsonMappingException, IOException, UnsupportedTypeException {
    Expr expr = notExpr.getExpr();
    validateExpr(expr, validatorNamespace, ontology);
    return true;
  }
  
  private static boolean validateCompExpr(CompExpr expr,
      SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException, JsonParseException, JsonMappingException, IOException, UnsupportedTypeException {
    System.out.println("Validating: " + expr);
    Expr lexp = expr.getLeftExpr();
    Expr rexp = expr.getRightExpr();
    String lreturn = "", rreturn = "";
    lreturn = validateUExprAndGetType(lexp, validatorNamespace, ontology);
    rreturn = validateUExprAndGetType(rexp, validatorNamespace, ontology);
    System.out.println(" Trying to validate LHS: " + lreturn + " RHS: " + rreturn);
    if(lreturn != null && rreturn != null && !isTypeCompatible(lreturn, rreturn)){
        System.err.println("Comp expression LHS: " + lreturn + " RHS: " + rreturn +" return type does not match.");
        System.err.println(expr);
        return false; 
    }
    return true;
  }
  
  private static boolean validatePathExpr(PathExpression pathExpression,
      SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException, JsonParseException, JsonMappingException, IOException {
    pathExpression.getlObjExpr();
    return true;
  }
  
  private static String validateUExprAndGetType(Expr expr, SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException, JsonParseException, JsonMappingException, IOException, UnsupportedTypeException {
   // System.out.println(expr.getClass());
    String rtype = null;// untill all types are not supported
    if(expr instanceof FunctionCall){
      FunctionCall functionCall = (FunctionCall)expr;
      rtype = validateFunctionAndGetDataType(functionCall, validatorNamespace, ontology);
      return rtype;
    }
    else if(expr instanceof OQLIdentifier){
      OQLIdentifier oqlIdentifier = (OQLIdentifier) expr;
      rtype = validateConceptPropertyAndGetType(oqlIdentifier, validatorNamespace, ontology);
    }
    else if(expr instanceof ListExpression){
      List<Expr> exprs = ((ListExpression) expr).getElements();
      for(Expr innerexpr : exprs){
        // All expr should be of same type here
        rtype = validateUExprAndGetType(innerexpr, validatorNamespace, ontology);
        // ***** THIS IS WRONG, FIX IT: listExpr:  LPAREN uExpr (COMMA uExpr)* RPAREN ;
      }
     }
    else if(expr instanceof ConstantVal){
      ConstantVal cExpr = (ConstantVal) expr;
      rtype = cExpr.getType().toString();
    //  System.out.println("Inside constant " + cExpr.getValue() + " " + rtype);

    }
    else{
    System.err.println("Expr type " + expr.getClass()  + " is not supported.");
    throw new UnsupportedTypeException("Expr type " + expr.getClass()  + " is not supported.");
    }
    return rtype;
  }

  public static String validateConceptPropertyAndGetType(OQLIdentifier oqlIdentifier,
      SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException {
    String aliasName = oqlIdentifier.getAliasref().getName();
    HashMap<String, Concept> aliasConceptMap = validatorNamespace.getAliasConceptMap();
    // Validate select column alias refers correctly to from alias
    if (!aliasConceptMap.containsKey(aliasName)) {
      throw new SemanticValidationException(
          "From alias:: " + aliasName + " is not present in from clause.");
    }

    OntologyProperty ontologyProperty = oqlIdentifier.getProperty();
    String propertyAliasName = ontologyProperty.getAliasref().getName();
    String propertyName = ontologyProperty.getName();
    // check ontology propertyAliasname concept has a property here
    // String propertyConceptName = aliasConceptMap.get(propertyAliasName).getName();
    // System.err.println(propertyAliasName + " " + propertyName + " " + propertyConceptName);
    // Concept propConcept = ontology.getConcept(propertyConceptName);
    Concept propConcept = aliasConceptMap.get(propertyAliasName);
    Property p = ontology.getProperty(propertyName.toLowerCase(), propConcept);
    if (null == p) {
      throw new SemanticValidationException(
          propertyName + " is not present for concept " + propConcept.getName());
    }
    String rType = p.getDatatype();
    return rType;
  }
  
  public static boolean validateConceptPropertyForSelect(OQLIdentifier oqlIdentifier,
      SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException {
    String aliasName = oqlIdentifier.getAliasref().getName();
    HashMap<String, Concept> aliasConceptMap = validatorNamespace.getAliasConceptMap();
    // Validate select column alias refers correctly to from alias
    if (!aliasConceptMap.containsKey(aliasName)) {
      throw new SemanticValidationException(
          "From alias:: " + aliasName + " is not present in from clause.");
    }

    OntologyProperty ontologyProperty = oqlIdentifier.getProperty();
    String propertyAliasName = ontologyProperty.getAliasref().getName();
    String propertyName = ontologyProperty.getName();
    // check ontology propertyAliasname concept has a property here
    // String propertyConceptName = aliasConceptMap.get(propertyAliasName).getName();
    // System.err.println(propertyAliasName + " " + propertyName + " " + propertyConceptName);
    // Concept propConcept = ontology.getConcept(propertyConceptName);
    Concept propConcept = aliasConceptMap.get(propertyAliasName);
    Property p = ontology.getProperty(propertyName.toLowerCase(), propConcept);
    if (null == p) {
      throw new SemanticValidationException(
          propertyName + " is not present for concept " + propConcept.getName());
    }
    return true;
  }

  public static String validateFunctionAndGetDataType(FunctionCall functionCall,
      SemanticValidatorNamespace validatorNamespace, Ontology ontology)
      throws SemanticValidationException, JsonParseException, JsonMappingException, IOException {

    if (null == functionCatalogManager) {
      functionCatalogManager = new FunctionCatalogManager();
    }
    String functionName = functionCall.getFunctionName();
    Function function = functionCatalogManager.getFunction(functionName);

    if (null == function || !function.getName().equals(functionName)) {
      throw new SemanticValidationException("Function " + functionName + " is not supported");
    }

    HashMap<String, Concept> aliasConceptMap = validatorNamespace.getAliasConceptMap();

    List<String> inputTypes = new ArrayList<String>();


    List<Expr> params = functionCall.getParams();
    for (Expr param : params) {
      OQLIdentifier parameter = (OQLIdentifier) param;
      String aliasName = parameter.getAliasref().getName();
      String propertyName = parameter.getProperty().getName();
      Concept concept = aliasConceptMap.get(aliasName);
      if (null == concept) {
        throw new SemanticValidationException(
            "Alias " + aliasName + " in function " + functionName + " is not in select list.");
      }
      Property property = ontology.getProperty(propertyName.toLowerCase(), concept);
      if (null == property) {
        throw new SemanticValidationException(
            "Property " + propertyName + " in function " + functionName + " is not valid.");
      }
      String dataType = property.getDatatype();
      inputTypes.add(dataType);
    }
    List<FunctionParameterOption> parameterOptions = function.getParameterOptions();
    for (FunctionParameterOption parameterOption : parameterOptions) {
      List<String> functionInputTypes = parameterOption.getInput();
      if (listEqualIgnoreCase(inputTypes, parameterOption.getInput())) {
        String functionReturnType = parameterOption.getOutput();
        return functionReturnType;
      }
    }
    throw new SemanticValidationException(
        "Input parameters " + inputTypes + " for function " + functionName + " is not supported.");
  }


  private static boolean listEqualIgnoreCase(List<String> list1, List<String> list2) {
    if (list1.size() != list2.size())
      return false;
    for (int i = 0; i < list1.size(); i++) {
      if (!list1.get(i).equalsIgnoreCase(list2.get(i))) {
        return false;
      }
    }
    return true;
  }
  
  private static boolean isTypeCompatible(String lhs, String rhs){
    String baseLhs = getBaseType(lhs);
    String baseRhs = getBaseType(rhs);
    if(baseLhs.equals(baseRhs)){
      return true;
    }
    return false;
  }
  
  private static String getBaseType(String type) {
    List<String> numericType = new ArrayList<String>(
        Arrays.asList("long", "double", "integer","float"));
    if(numericType.contains(type.toLowerCase()))
      return "Numeric";
    else
      return type;
  }

}
