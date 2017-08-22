package com.ibm.tatzia.query.semanticvalidator.test;

import java.util.*;

import com.ibm.tatzia.kg.query.OQLNestedQuery;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.query.parser.OQLParseHelper;
import com.ibm.tatzia.query.parser.OQLValidationOrchestrator;
import com.ibm.tatzia.query.semanticvalidator.ConceptPropertyValidator;
import com.ibm.tatzia.query.semanticvalidator.FunctionValidator;
import com.ibm.tatzia.query.semanticvalidator.UnsupportedTypeException;

public class ValidatorTest {
  

	  public static void main(String[] args) {
	    validateAll();
	    String testQuery1 =
	        "SELECT avg(obj.sic_major_group_code) , obj.sic_division_code , obj.sic_major_group , obj.sic_code , obj.sic_group , obj.sic_products , obj.sic_division , obj.Industryid FROM Industry obj WHERE obj.sic_major_group IN ('TRANSPORTATION BY AIR')";
	    String testQuery2 =
	        "SELECT avg(obj.sic_major_group_code), max(obj.sic_major_group_code) , obj.sic_division_code , obj.sic_major_group , obj.sic_code , obj.sic_group , obj.sic_products , obj.sic_division  FROM Industry obj WHERE obj.sic_major_group IN ('TRANSPORTATION BY AIR') or obj.sic_major_group IN ('TRANSPORTATION BY AIR')";
	    String testQuery52="select obj.metric_value as metric_value, obj.metric_string_value as metric_string_value, obj.metric_unit as metric_unit,   obj.metric_start_year as metric_start_year,obj.metric_end_year as metric_end_year,  obj.metric_start_quarter as metric_start_quarter, obj.metric_end_quarter as metric_end_quarter,    obj.is_computed as is_computed, c.eid as company_id, c.name as company_name  FROM PublicMetricData obj, PublicCompany pc , PublicMetric pm, Company c, "
	        + "Industry i WHERE pm.eid = 'http://fasb.org/us-gaap_Revenues' AND i.eid = '4512' AND c->toIndustry = i     "
	        + "AND obj->forPublicCompany = pc AND obj->forMetric = pm AND pc->Company_isa_PublicCompany = c   "
	        + "AND obj.metric_start_year = 2014 AND obj.metric_start_quarter = 'Q1' order by obj.metric_start_year DESC, obj.metric_start_quarter DESC";
	    String testQ = testQuery52;
	    try {
	      OQLParseHelper tr = new OQLParseHelper();
	      System.out.println("parsing");
	      OQLNestedQuery parsed_query = tr.parse(testQ);
	      System.out.println("Printing parsed query");
	      System.out.println(parsed_query);
	// Parser does not throw proper exception
	      OQLQuery oqlQuery = null;
	      if (parsed_query instanceof OQLQuery)
	        oqlQuery = (OQLQuery) parsed_query;
	      else
	        oqlQuery = parsed_query.get_query();
	      System.out.println(oqlQuery.toString());
	      OQLValidationOrchestrator oqlValidationOrchestrator = new OQLValidationOrchestrator();
	      
	    //  oqlValidationOrchestrator.validateOQL(oqlQuery);
	      
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	  }
	  
  private static void validateAll() {
    //List<String> queries = FileHelper.getLines("validation-test-queries-positive.txt");
    List<String> queries = FileHelper.getLines("test-data/testQueries.txt");
   // List<String> queries = FileHelper.getLines("test-data/queries-from-parse-helper.txt");
System.out.println(queries.size());
    int count = 0;
    for(String q : queries) {
      System.out.printf("*************Validating query number %d \n", ++count);
      try {
        String testQ = q;
        OQLParseHelper tr = new OQLParseHelper();
        //System.out.println("parsing");
        OQLNestedQuery parsed_query = tr.parse(testQ);
       // System.out.println("Printing parsed query");
       // System.out.println(parsed_query);
  // Parser does not throw proper exception
        OQLQuery oqlQuery = null;
        if (parsed_query instanceof OQLQuery)
          oqlQuery = (OQLQuery) parsed_query;
        else
          oqlQuery = parsed_query.get_query();
        //System.out.println(oqlQuery.toString());
       // OQLValidationOrchestrator oqlValidationOrchestrator = new OQLValidationOrchestrator();
        
      //  oqlValidationOrchestrator.validateOQL(oqlQuery);
        
      }
     // catch(UnsupportedTypeException ue){
      //  System.err.println(ue.getMessage());
      //}
      catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getMessage());
      }
      System.out.printf("**********Validated query number %d\n", count);

    }
  }
  
  

}
