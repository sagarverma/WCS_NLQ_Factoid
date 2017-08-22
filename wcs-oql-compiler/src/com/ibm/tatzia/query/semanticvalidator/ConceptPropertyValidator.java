package com.ibm.tatzia.query.semanticvalidator;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibm.tatzia.kg.query.AggType;
import com.ibm.tatzia.kg.query.Expression;
import com.ibm.tatzia.kg.query.From;
import com.ibm.tatzia.kg.query.FromArgument;
import com.ibm.tatzia.kg.query.FromClause;
import com.ibm.tatzia.kg.query.FunctionCall;
import com.ibm.tatzia.kg.query.KnowledgeGraphQuery;
import com.ibm.tatzia.kg.query.OQLIdentifier;
import com.ibm.tatzia.kg.query.OQLNestedQuery;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.kg.query.OntologyConcept;
import com.ibm.tatzia.kg.query.OntologyProperty;
import com.ibm.tatzia.kg.query.SQLIdentifier;
import com.ibm.tatzia.kg.query.Select;
import com.ibm.tatzia.kg.query.SelectColumn;
//import com.ibm.tatzia.kg.query.parser.KGQLQueryGenerator;
//import com.ibm.tatzia.kg.query.parser.ParseException;
//import com.ibm.tatzia.kg.ti.TIException;
import com.ibm.tatzia.ontology.Concept;
import com.ibm.tatzia.ontology.Ontology;
import com.ibm.tatzia.ontology.Property;
import com.ibm.tatzia.query.functioncatalog.FunctionCatalogManager;
import com.ibm.tatzia.query.parser.OQLParseHelper;
import com.ibm.tatzia.query.semanticvalidator.util.OntologyUtil;

public class ConceptPropertyValidator implements SemanticValidator{

	//private OQLQuery oqlQuery;
	//private Ontology ontology;


	public ConceptPropertyValidator() {
	  //ontology = OntologyUtil.getOntology("FI-2.0.2");
	}

  @Override
  public void validate(OQLQuery oqlQuery, SemanticValidatorNamespace semanticValidatorResponse, Ontology ontology) throws SemanticValidationException {
    // Validate From Clause and create the concept/object hashMap
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
      
      semanticValidatorResponse.setAliasConceptMap(aliasConceptMap);
     
      Select select = oqlQuery.getSelect();
      List<SelectColumn> sColumns = select.getSelectColumns();
      for (SelectColumn sColumn : sColumns) {
        Object selectValueObject = sColumn.getValue();
        if (selectValueObject instanceof OQLIdentifier) {
          OQLIdentifier oqlIdentifier = (OQLIdentifier) selectValueObject;
          String aliasName = oqlIdentifier.getAliasref().getName();

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
          Property p = ontology.getProperty(propertyName, propConcept);
          if(null == p){
            throw new SemanticValidationException(propertyName  + " is not present for concept " + propConcept.getName());
          }
          else{
            System.out.println(p.toString());
          }
        }
      }
    }
	  /*
	  
	  From from=kgq.getFrom();
		List<FromClause> fromList=from.getFromClause();
		HashMap<String, String> conceptObjectMap=new HashMap<>();
		for(int i=0;i<fromList.size();i++)
		{
			FromClause fc=fromList.get(i);
			if(onto.getConcept(fc.getExpr().toString())!=null)
			{
			conceptObjectMap.put(fc.getAlias(),fc.getExpr().toString());
			}
			else
			{
				throw new SemanticValidationException("Concept Name ::"+fc.getExpr().toString()+" :: in the From Clause is Invalid");
			}
		}
		
		//Validate Select Clause and for valid property projections
		
		Select select=kgq.getSelect();
		List<SelectColumn> sc=select.getSelectColumns();
		if(sc!=null || sc.size()>0)
		{
			for(int j=0;j<sc.size();j++)
				
			{
				//System.out.println(sc.get(j).toString());
				if(sc.get(j).getValue() instanceof FunctionCall)
				{
					FunctionCall fc=(FunctionCall) sc.get(j).getValue();
					if(AggType.fromString(fc.getFunctionName().toString())==null)
					{
						throw new SemanticValidationException("Invalid Aggregate function in the selection arguments::"+fc.getFunctionName());
						
					}
					ArrayList<Expression> exps=fc.getParams();
					if(exps==null || exps.size()==0)
					{
						throw new SemanticValidationException("Invalid aggregation argument: aggregation should have some argument specified"+exps.toString());
						
					}
					for(int k=0;k<exps.size();k++)
					{
						Expression fe=exps.get(k);
						if(fe instanceof SQLIdentifier)
						{
							SQLIdentifier sqlID=(SQLIdentifier)fe;
							String tableName=sqlID.getTableName();
							String concept=conceptObjectMap.get(tableName);
							String attribute=sqlID.getAttrName();
							if(concept==null)
							{
								throw new SemanticValidationException("Invalid Object Reference in Aggregation Argument:"+sqlID.toString());
							}
								
							Concept conc=onto.getConcept(concept);
							if(attribute.equalsIgnoreCase(concept+"id"))
							{
								continue;
							}
							Property prop=onto.getProperty(attribute,conc);
							if(prop!=null)
							{
								continue;
							}
							else
							{
								throw new SemanticValidationException("Invalid Aggregation property reference in ::"+sqlID.toString());
							}
						}
					}
				}
				else if(sc.get(j).getValue() instanceof SQLIdentifier)
				{
					
					SQLIdentifier sqlID=(SQLIdentifier)sc.get(j).getValue();
					//System.out.println(sqlID.toString());
					String tableName=sqlID.getTableName();
					String concept=conceptObjectMap.get(tableName);
					String attribute=sqlID.getAttrName();
					if(concept==null)
					{
						throw new SemanticValidationException("Invalid Object Reference in Selection Argument:"+sqlID.toString());
					}
						
					Concept conc=onto.getConcept(concept);
					if(attribute.equalsIgnoreCase(concept+"id"))
					{
						continue;
					}
					Property prop=onto.getProperty(attribute,conc);
					if(prop!=null)
					{
						continue;
					}
					else
					{
						throw new SemanticValidationException("Invalid Selection  property reference in ::"+sqlID.toString());
					}
				}
			}
			
		}
			*/	
				
	}
			
		
		//
		
		

	
	

	public static void main(String[] args) {
		String testKGQuery32="SELECT avg(obj.sic_major_group_code) , obj.sic_division_code , obj.sic_major_group , obj.sic_code , obj.sic_group , obj.sic_products , obj.sic_division FROM Industry obj WHERE obj.sic_major_group IN ('TRANSPORTATION BY AIR')";
		try {
		     OQLParseHelper tr = new OQLParseHelper();
		     System.out.println("parsing");
			OQLNestedQuery parsed_query = tr.parse(testKGQuery32);
			System.out.println("Printing parsed query");
	        System.out.println(parsed_query);
	        
	        OQLQuery oqlQuery = null;
	        if(parsed_query instanceof OQLQuery)
	          oqlQuery= (OQLQuery)parsed_query;
	        else
	          oqlQuery=parsed_query.get_query();
	        System.out.println(oqlQuery.toString());
			ConceptPropertyValidator v = new ConceptPropertyValidator();
			//v.validate(oqlQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
