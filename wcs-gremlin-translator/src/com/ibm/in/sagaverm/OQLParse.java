package com.ibm.in.sagaverm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibm.in.sagaverm.gremlin.FilterGremlin;
import com.ibm.in.sagaverm.gremlin.FromGremlin;
import com.ibm.in.sagaverm.gremlin.Gremlin;
import com.ibm.in.sagaverm.gremlin.GroupByGremlin;
import com.ibm.in.sagaverm.gremlin.HavingGremlin;
import com.ibm.in.sagaverm.gremlin.OrderByGremlin;
import com.ibm.in.sagaverm.gremlin.RelationGremlin;
import com.ibm.in.sagaverm.gremlin.SelectGremlin;
import com.ibm.tatzia.kg.query.AndExpression;
import com.ibm.tatzia.kg.query.CompExpr;
import com.ibm.tatzia.kg.query.ConstantVal;
import com.ibm.tatzia.kg.query.Expr;
import com.ibm.tatzia.kg.query.From;
import com.ibm.tatzia.kg.query.FromArgument;
import com.ibm.tatzia.kg.query.FunctionCall;
import com.ibm.tatzia.kg.query.GroupBy;
import com.ibm.tatzia.kg.query.Having;
import com.ibm.tatzia.kg.query.ListExpression;
import com.ibm.tatzia.kg.query.OQLIdentifier;
import com.ibm.tatzia.kg.query.OQLNestedQuery;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.kg.query.OrderBy;
import com.ibm.tatzia.kg.query.PathExpression;
import com.ibm.tatzia.kg.query.Select;
import com.ibm.tatzia.kg.query.SelectColumn;
import com.ibm.tatzia.kg.query.Where;
import com.ibm.tatzia.query.parser.OQLParseException;
import com.ibm.tatzia.query.parser.OQLParseHelper;


public class OQLParse {
	
	private static final HashMap<String, String> opMap = new HashMap<String, String>();
	
	static{
		opMap.put("<=", "lte");
		opMap.put(">=", "gte");
		opMap.put("<", "lt");
		opMap.put(">", "gt");
		opMap.put("eq", "eq");
		opMap.put("=", "eq");
		opMap.put("<>", "neq");
		opMap.put("NOT", "neq");
		opMap.put("IN", "within");
		opMap.put("ASC", "incr");
		opMap.put("DESC", "decr");
		opMap.put("COUNT", "count");
		opMap.put("MAX", "max");
		opMap.put("MIN", "min");
		opMap.put("MAX", "max");
	}
	
	//Get OQL Objects
	public OQLQuery getOQLQuery(String oqlQuery)
	{
		OQLParseHelper tr = new OQLParseHelper();
		OQLNestedQuery parsed_query = new OQLNestedQuery();
		try {
			parsed_query = tr.parse(oqlQuery);
		} catch (OQLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(parsed_query);
		OQLQuery queryObject=null;
		if(parsed_query instanceof OQLQuery)
			 queryObject = (OQLQuery)parsed_query;
		else
			queryObject = parsed_query.get_query();
		
		return queryObject;
		
	}
	
	//Create from gremlin list that contains labels and their corresponding aliases from OQL from clause
	public static ArrayList<FromGremlin> getFromList(From from, HashMap<String, String> newConcepts){
		ArrayList<FromGremlin> fromGremlinList = new ArrayList<FromGremlin>();
		
		List<FromArgument> fromArguments = from.getFromClause();
		
		for(int i=0; i < fromArguments.size(); i++){
			FromGremlin fromGremlin = new FromGremlin();
			fromGremlin.setAlias(fromArguments.get(i).getAlias().getName().toString()); //get alias and set to gremlin from object
			fromGremlin.setLabel(newConcepts.get(fromArguments.get(i).getConcept().getName().toString())); //get label and set to gremlin from object
			fromGremlinList.add(fromGremlin);
			System.out.println(fromGremlin.getLabel());
		}
		
		//System.out.println(fromGremlinList.toString());
		return fromGremlinList;
	}
	
	//Create select gremlin list that contains label, alias and property from OQL select clause
	public static ArrayList<SelectGremlin> getSelectList(Select select, HashMap<String, String> labelAliasMap){
		ArrayList<SelectGremlin> selectGremlinList = new ArrayList<SelectGremlin>();
		
		List<SelectColumn> selectColumns = select.getSelectColumns();
		
		for(int i=0; i < selectColumns.size(); i++){
			SelectGremlin selectGremlin = new SelectGremlin();
			if(selectColumns.get(i).getValue() instanceof FunctionCall){
				FunctionCall funcCall = (FunctionCall) selectColumns.get(i).getValue();
				selectGremlin.setFuncCall(opMap.get(funcCall.getFunctionName().toString()));
				selectGremlin.setDistinct(funcCall.getHasDistinct());
				
				OQLIdentifier oqlId = (OQLIdentifier)funcCall.getParams().get(0);
				selectGremlin.setProperty(oqlId.getProperty().getName().toString()); //get property and set to gremlin filter object
				selectGremlin.setAlias(oqlId.getAliasref().getName().toString()); //get alias and set to gremlin filter object
				selectGremlin.setLabel(labelAliasMap.get(selectGremlin.getAlias().toString())); //get label name from alias through hashmap and set to gremlin filter object 
				
				if(selectColumns.get(i).getAlias() != null){
					selectGremlin.setAsAlias(selectColumns.get(i).getAlias().toString());
				}
				else{
					if(selectGremlin.getDistinct()){
						selectGremlin.setAsAlias(selectGremlin.getAlias() + "_" + selectGremlin.getProperty() + "_" +
						selectGremlin.getFuncCall() + "_DISTINCT");
					}
					else{
						selectGremlin.setAsAlias(selectGremlin.getAlias() + "_" + selectGremlin.getProperty() + "_" +
								selectGremlin.getFuncCall());
					}
				}
			}
			else{
				OQLIdentifier oqlId = (OQLIdentifier) selectColumns.get(i).getValue();
				selectGremlin.setProperty(oqlId.getProperty().getName().toString()); //get property and set to gremlin filter object
				selectGremlin.setAlias(oqlId.getAliasref().getName().toString()); //get alias and set to gremlin filter object
				selectGremlin.setLabel(labelAliasMap.get(selectGremlin.getAlias().toString())); //get label name from alias through hashmap and set to gremlin filter object 
				selectGremlin.setDistinct(false);
				selectGremlin.setFuncCall(null);
				selectGremlin.setAsAlias(selectGremlin.getAlias() + "_" + selectGremlin.getProperty());
			}
			selectGremlinList.add(selectGremlin);
		}
		return selectGremlinList;
	}
	
	//Create filter gremlin list that contains label, alias, property, operator, values from OQL where clause
	public static ArrayList<FilterGremlin> getFilterList(Where whereClause, HashMap<String, String> labelAliasMap){
		
		if(whereClause.getExpr() instanceof PathExpression){
			return null;
		}
		else{
			ArrayList<FilterGremlin> filterGremlinList = new ArrayList<FilterGremlin>();
			
			
			if(whereClause.getExpr() instanceof CompExpr){
				CompExpr compExp = (CompExpr) whereClause.getExpr();
				FilterGremlin filterGremlin = new FilterGremlin();
	
				OQLIdentifier oqlId = (OQLIdentifier) compExp.getLeftExpr();
				
				filterGremlin.setAlias(oqlId.getAliasref().getName().toString()); //get alias set to gremlin filter
				filterGremlin.setProperty(oqlId.getProperty().getName().toString()); //get property set to gremlin filter
				filterGremlin.setLabel(labelAliasMap.get(filterGremlin.getAlias().toString())); //get label through hashmap set to gremlin filter
				filterGremlin.setOperator(opMap.get(compExp.getOp().getSymbol().toString())); //get operator through opmap hashmap set to gremlin filter
				
				if(compExp.getRightExpr() instanceof ConstantVal){
					ConstantVal constVal = (ConstantVal) compExp.getRightExpr();
					//check if value is of type string
					if(constVal.getType().toString().equals("STRING")){
						//check if value is a date
						if(isDate(constVal.getValue().toString())){
							filterGremlin.setValue(Integer.parseInt(constVal.getValue().toString().replaceAll("[-+.^:,']",""))); //remove - from date stringS
						}
						else{
							filterGremlin.setValue((String)constVal.getValue().toString()); //normal string
						}
					}
					else{
						filterGremlin.setValue(Integer.parseInt(constVal.getValue().toString())); //is an integer
					}						
				}
				else{
					ListExpression listExpression = (ListExpression) compExp.getRightExpr();
					ArrayList<Expr> listExpressionValue = (ArrayList<Expr>)listExpression.getElements();
					
					ArrayList<Object> withinValueList = new ArrayList<Object>();
					
					//get values from list expression for IN clause and make a string for gremlin ('A','B','C','D') (1,2,3,4)
					for(int j = 0; j < listExpressionValue.size(); j++){
						ConstantVal value = (ConstantVal) listExpressionValue.get(j);
						if(value.getType().toString().equals("STRING")){
							withinValueList.add((String)value.getValue().toString());
						}
						else{
							withinValueList.add(Integer.parseInt(value.getValue().toString()));
						}
					}
					filterGremlin.setValue(withinValueList);
				}
				filterGremlinList.add(filterGremlin);
			}
			else{
				AndExpression andExpression = (AndExpression) whereClause.getExpr();
				List<Expr> expressionList = (List<Expr>) andExpression.getExprs();
			
				//get all expressions in where clause
				for(int i=0; i < expressionList.size(); i++){
					//we only want filters not path so checking for CompExpr type of object
					if(expressionList.get(i) instanceof CompExpr){
						FilterGremlin filterGremlin = new FilterGremlin();
						
						CompExpr compExp = (CompExpr) expressionList.get(i);
						OQLIdentifier oqlId = (OQLIdentifier) compExp.getLeftExpr();
						
						filterGremlin.setAlias(oqlId.getAliasref().getName().toString()); //get alias set to gremlin filter
						filterGremlin.setProperty(oqlId.getProperty().getName().toString()); //get property set to gremlin filter
						filterGremlin.setLabel(labelAliasMap.get(filterGremlin.getAlias().toString())); //get label through hashmap set to gremlin filter
						filterGremlin.setOperator(opMap.get(compExp.getOp().getSymbol().toString())); //get operator through opmap hashmap set to gremlin filter
						
						if(compExp.getRightExpr() instanceof ConstantVal){
							ConstantVal constVal = (ConstantVal) compExp.getRightExpr();
							//check if value is of type string
							if(constVal.getType().toString().equals("STRING")){
								//check if value is a date
								if(isDate(constVal.getValue().toString())){
									filterGremlin.setValue(Integer.parseInt(constVal.getValue().toString().replaceAll("[-+.^:,']",""))); //remove - from date stringS
								}
								else{
									filterGremlin.setValue((String)constVal.getValue().toString()); //normal string
								}
							}
							else{
								filterGremlin.setValue(Integer.parseInt(constVal.getValue().toString())); //is an integer
							}						
						}
						else{
							ListExpression listExpression = (ListExpression) compExp.getRightExpr();
							ArrayList<Expr> listExpressionValue = (ArrayList<Expr>)listExpression.getElements();
							
							ArrayList<Object> withinValueList = new ArrayList<Object>();
							
							//get values from list expression for IN clause and make a string for gremlin ('A','B','C','D') (1,2,3,4)
							for(int j = 0; j < listExpressionValue.size(); j++){
								ConstantVal value = (ConstantVal) listExpressionValue.get(j);
								if(value.getType().toString().equals("STRING")){
									withinValueList.add((String)value.getValue().toString());
								}
								else{
									withinValueList.add(Integer.parseInt(value.getValue().toString()));
								}
							}
							filterGremlin.setValue(withinValueList);
						}
						filterGremlinList.add(filterGremlin);
					}
					
				}
			}
			return filterGremlinList;
		}
	}
	
	//Create relation gremlin list that contains label, alias, relation, label, alias from OQL where clause
	public static ArrayList<RelationGremlin> getRelationList(Where whereClause, HashMap<String,String> labelAliasMap, HashMap<String, ArrayList<String>> subgraphRelations){
		if(whereClause.getExpr() instanceof CompExpr){
			return null;
		}
		else if(whereClause.getExpr() instanceof PathExpression){
			ArrayList<RelationGremlin> relationGremlinList = new ArrayList<RelationGremlin>();
			
			PathExpression pathExp = (PathExpression) whereClause.getExpr();
			//System.out.print(pathExp.toString());
			RelationGremlin relationGremlin = new RelationGremlin();
			List<String> relations = pathExp.getlObjExpr().getRelations(); //get and set relation
			
			List<String> tempRelations = new ArrayList<String>() ;
			
			for(int rel=0; rel<relations.size(); rel++){
				if(!relations.get(rel).contains("isa")){
					tempRelations.add(relations.get(rel));
				}
			}
			
			String currentRelation = tempRelations.get(0);
			String sourceConcept = subgraphRelations.get(currentRelation).get(0);
			String destConcept = subgraphRelations.get(currentRelation).get(1);
			String currentAlias = pathExp.getlObjExpr().getObject().getAliasref().getName().toString();
			String currentConcept = labelAliasMap.get(currentAlias);
			int currentAliasNo = 1000;
			
			relationGremlin.setRelation(currentRelation);
			if(sourceConcept.equals(currentConcept)){
				relationGremlin.setAliasLeft(currentAlias); //get left alias from oql set to relation gremlin
				relationGremlin.setLabelLeft(currentConcept); //get left label and set to relation gremlin
				if(tempRelations.size() == 1){
					relationGremlin.setAliasRight(pathExp.getrObjExpr().getObject().getAliasref().getName().toString());
					relationGremlin.setLabelRight(relationGremlin.getAliasRight());
				}
				else{
					relationGremlin.setLabelRight(destConcept);
					relationGremlin.setAliasRight("o"+destConcept+Integer.toString(currentAliasNo));
					currentAlias = "o"+destConcept+Integer.toString(currentAliasNo);
					currentConcept = destConcept;
					currentAliasNo += 1;
				}
			}
			else{
				relationGremlin.setAliasRight(currentAlias);
				relationGremlin.setLabelRight(currentConcept);
				if(tempRelations.size() == 1){
					relationGremlin.setAliasLeft(pathExp.getlObjExpr().getObject().getAliasref().getName().toString());
					relationGremlin.setLabelLeft(relationGremlin.getAliasLeft());
				}
				else{
					relationGremlin.setLabelLeft(sourceConcept);
					relationGremlin.setAliasLeft("o"+sourceConcept+Integer.toString(currentAliasNo));
					currentAlias = "o"+sourceConcept+Integer.toString(currentAliasNo);
					currentConcept = sourceConcept;
					currentAliasNo += 1;
				}
			}
			
			relationGremlinList.add(relationGremlin);
			
			for(int j=1; j<tempRelations.size()-1; j++){
				relationGremlin = new RelationGremlin();
				currentRelation = tempRelations.get(j);
				sourceConcept = subgraphRelations.get(currentRelation).get(0);
				destConcept = subgraphRelations.get(currentRelation).get(1);
				
				relationGremlin.setRelation(currentRelation);
				
				if(sourceConcept.equals(currentConcept)){
					relationGremlin.setAliasLeft(currentAlias); //get left alias from oql set to relation gremlin
					relationGremlin.setLabelLeft(currentConcept); //get left label and set to relation gremlin
					relationGremlin.setLabelRight(destConcept);
					relationGremlin.setAliasRight("o"+destConcept+Integer.toString(currentAliasNo));
					currentAlias = "o"+destConcept+Integer.toString(currentAliasNo);
					currentConcept = destConcept;
					currentAliasNo += 1;
				}
				else{
					relationGremlin.setAliasRight(currentAlias);
					relationGremlin.setLabelRight(currentConcept);
					relationGremlin.setLabelLeft(sourceConcept);
					relationGremlin.setAliasLeft("o"+sourceConcept+Integer.toString(currentAliasNo));
					currentAlias = "o"+sourceConcept+Integer.toString(currentAliasNo);
					currentConcept = sourceConcept;
					currentAliasNo += 1;
				}
				
				relationGremlinList.add(relationGremlin);
			}
			
			if(tempRelations.size() >= 2){
				relationGremlin = new RelationGremlin();
				currentRelation = tempRelations.get(tempRelations.size()-1);
				sourceConcept = subgraphRelations.get(currentRelation).get(0);
				destConcept = subgraphRelations.get(currentRelation).get(1);
				
				relationGremlin.setRelation(currentRelation);
				
				if(sourceConcept.equals(currentConcept)){
					relationGremlin.setAliasLeft(currentAlias); //get left alias from oql set to relation gremlin
					relationGremlin.setLabelLeft(currentConcept); //get left label and set to relation gremlin
					relationGremlin.setAliasRight(pathExp.getrObjExpr().getObject().getAliasref().getName().toString());
					relationGremlin.setLabelRight(labelAliasMap.get(relationGremlin.getAliasRight()));
				}
				else{
					relationGremlin.setAliasRight(currentAlias);
					relationGremlin.setLabelRight(currentConcept);
					relationGremlin.setAliasLeft(pathExp.getrObjExpr().getObject().getAliasref().getName().toString());
					relationGremlin.setLabelLeft(labelAliasMap.get(relationGremlin.getAliasLeft()));
				}
				
				relationGremlinList.add(relationGremlin);
			}
			return relationGremlinList;
		}
		else{
			
			ArrayList<RelationGremlin> relationGremlinList = new ArrayList<RelationGremlin>();
			
			AndExpression andExpression = (AndExpression) whereClause.getExpr();
			List<Expr> expressionList = (List<Expr>) andExpression.getExprs();
			
			int currentAliasNo = 1000;
			//get all expressions in where clause
			for(int i=0; i < expressionList.size(); i++){
				//we only want filters not path so checking for CompExpr type of object
				if(expressionList.get(i) instanceof PathExpression){
					
					RelationGremlin relationGremlin = new RelationGremlin();
					
					PathExpression pathExp = (PathExpression) expressionList.get(i);
					
					List<String> relations = pathExp.getlObjExpr().getRelations(); //get and set relation
					
					List<String> tempRelations = new ArrayList<String>() ;
					
					for(int rel=0; rel<relations.size(); rel++){
						if(!relations.get(rel).contains("isa")){
							tempRelations.add(relations.get(rel));
						}
					}
					
					String currentRelation = tempRelations.get(0);
					String sourceConcept = subgraphRelations.get(currentRelation).get(0);
					String destConcept = subgraphRelations.get(currentRelation).get(1);
					String currentAlias = pathExp.getlObjExpr().getObject().getAliasref().getName().toString();
					String currentConcept = labelAliasMap.get(currentAlias);
					
					relationGremlin.setRelation(currentRelation);
					
					if(sourceConcept.equals(currentConcept)){
						relationGremlin.setAliasLeft(currentAlias); //get left alias from oql set to relation gremlin
						relationGremlin.setLabelLeft(currentConcept); //get left label and set to relation gremlin
						if(tempRelations.size() == 1){
							relationGremlin.setAliasRight(pathExp.getrObjExpr().getObject().getAliasref().getName().toString());
							relationGremlin.setLabelRight(relationGremlin.getAliasRight());
						}
						else{
							relationGremlin.setLabelRight(destConcept);
							relationGremlin.setAliasRight("o"+destConcept+Integer.toString(currentAliasNo));
							currentAlias = "o"+destConcept+Integer.toString(currentAliasNo);
							currentConcept = destConcept;
							currentAliasNo += 1;
						}
					}
					else{
						relationGremlin.setAliasRight(currentAlias);
						relationGremlin.setLabelRight(currentConcept);
						if(tempRelations.size() == 1){
							relationGremlin.setAliasLeft(pathExp.getlObjExpr().getObject().getAliasref().getName().toString());
							relationGremlin.setLabelLeft(relationGremlin.getAliasLeft());
						}
						else{
							relationGremlin.setLabelLeft(sourceConcept);
							relationGremlin.setAliasLeft("o"+sourceConcept+Integer.toString(currentAliasNo));
							currentAlias = "o"+sourceConcept+Integer.toString(currentAliasNo);
							currentConcept = sourceConcept;
							currentAliasNo += 1;
						}
					}
					
					relationGremlinList.add(relationGremlin);
					
					for(int j=1; j<tempRelations.size()-1; j++){
						relationGremlin = new RelationGremlin();
						currentRelation = tempRelations.get(j);
						sourceConcept = subgraphRelations.get(currentRelation).get(0);
						destConcept = subgraphRelations.get(currentRelation).get(1);
						
						relationGremlin.setRelation(currentRelation);
						
						if(sourceConcept.equals(currentConcept)){
							relationGremlin.setAliasLeft(currentAlias); //get left alias from oql set to relation gremlin
							relationGremlin.setLabelLeft(currentConcept); //get left label and set to relation gremlin
							relationGremlin.setLabelRight(destConcept);
							relationGremlin.setAliasRight("o"+destConcept+Integer.toString(currentAliasNo));
							currentAlias = "o"+destConcept+Integer.toString(currentAliasNo);
							currentConcept = destConcept;
							currentAliasNo += 1;
						}
						else{
							relationGremlin.setAliasRight(currentAlias);
							relationGremlin.setLabelRight(currentConcept);
							relationGremlin.setLabelLeft(sourceConcept);
							relationGremlin.setAliasLeft("o"+sourceConcept+Integer.toString(currentAliasNo));
							currentAlias = "o"+sourceConcept+Integer.toString(currentAliasNo);
							currentConcept = sourceConcept;
							currentAliasNo += 1;
						}
						
						relationGremlinList.add(relationGremlin);
					}
					
					if(tempRelations.size() >= 2){
						relationGremlin = new RelationGremlin();
						currentRelation = tempRelations.get(tempRelations.size()-1);
						sourceConcept = subgraphRelations.get(currentRelation).get(0);
						destConcept = subgraphRelations.get(currentRelation).get(1);
						
						relationGremlin.setRelation(currentRelation);
						
						if(sourceConcept.equals(currentConcept)){
							relationGremlin.setAliasLeft(currentAlias); //get left alias from oql set to relation gremlin
							relationGremlin.setLabelLeft(currentConcept); //get left label and set to relation gremlin
							relationGremlin.setAliasRight(pathExp.getrObjExpr().getObject().getAliasref().getName().toString());
							relationGremlin.setLabelRight(labelAliasMap.get(relationGremlin.getAliasRight()));
						}
						else{
							relationGremlin.setAliasRight(currentAlias);
							relationGremlin.setLabelRight(currentConcept);
							relationGremlin.setAliasLeft(pathExp.getrObjExpr().getObject().getAliasref().getName().toString());
							relationGremlin.setLabelLeft(labelAliasMap.get(relationGremlin.getAliasLeft()));
						}
						
						relationGremlinList.add(relationGremlin);
					}
				}
			}
			return relationGremlinList;
		}
						
		
	}
	
	//Create groupby gremlin list that contains label, alias, property from OQL Group By clause
	public static ArrayList<GroupByGremlin> getGroupByList(GroupBy groupBy, HashMap<String,String> labelAliasMap){
		ArrayList<GroupByGremlin> groupByGremlinList = new ArrayList<GroupByGremlin>();
		

		for(int i = 0; i < groupBy.getGroupByArguments().size(); i++){
			GroupByGremlin groupByGremlin = new GroupByGremlin();
			
			groupByGremlin.setAlias(groupBy.getGroupByArguments().get(i).getAliasref().getName().toString()); // get and set alias
			groupByGremlin.setLabel(labelAliasMap.get(groupByGremlin.getAlias())); //get and set label
			groupByGremlin.setProperty(groupBy.getGroupByArguments().get(i).getProperty().getName().toString()); //get and set property
			
			groupByGremlinList.add(groupByGremlin);
		}
		return groupByGremlinList;
	}
	
	//Create orderby gremlin list that contains label, alias, property and order type from OQL Order by clause
	public static ArrayList<OrderByGremlin> getOrderByList(OrderBy orderBy, HashMap<String,String> labelAliasMap){
		ArrayList<OrderByGremlin> orderByGremlinList = new ArrayList<OrderByGremlin>();
		
		for(int i=0; i < orderBy.getOrderByArguments().size(); i++){
			OrderByGremlin orderByGremlin = new OrderByGremlin();
			
			orderByGremlin.setAlias(orderBy.getOrderByArguments().get(i).getOqlOrg().getAliasref().getName().toString()); //get and set alias
			orderByGremlin.setLabel(labelAliasMap.get(orderByGremlin.getAlias())); //get and set label
			orderByGremlin.setProperty(orderBy.getOrderByArguments().get(i).getOqlOrg().getProperty().getName().toString()); //get and set property
			if(orderBy.getOrderByArguments().get(i).getOtype() == null){
				orderByGremlin.setOrderType(opMap.get("ASC"));
			}
			else{
				orderByGremlin.setOrderType(opMap.get(orderBy.getOrderByArguments().get(i).getOtype().getSymbol().toString())); //get and set operator
			}
			
			orderByGremlinList.add(orderByGremlin);
		}
		
		return orderByGremlinList;
	}
	
	public static ArrayList<HavingGremlin> getHavingList(Having having, HashMap<String, String> labelAliasMap){
		ArrayList<HavingGremlin> havingGremlinList = new ArrayList<HavingGremlin>();
		
		HavingGremlin havingGremlin = new HavingGremlin();
		
		CompExpr compExpr = (CompExpr) having.getExpr();
		FunctionCall funcCall = (FunctionCall) compExpr.getLeftExpr();
		havingGremlin.setFunction(opMap.get(funcCall.getFunctionName().toString()));
		
		OQLIdentifier oqlId = (OQLIdentifier) funcCall.getParams().get(0);
		havingGremlin.setAlias(oqlId.getAliasref().getName().toString());
		havingGremlin.setLabel(labelAliasMap.get(havingGremlin.getAlias()));
		havingGremlin.setProperty(oqlId.getProperty().getName().toString());
		havingGremlin.setOperator(opMap.get(compExpr.getOp().getSymbol().toString()));
		
		if(compExpr.getRightExpr() instanceof ConstantVal){
			ConstantVal constVal = (ConstantVal) compExpr.getRightExpr();
			//check if value is of type string
			if(constVal.getType().toString().equals("STRING")){
				//check if value is a date
				if(isDate(constVal.getValue().toString())){
					havingGremlin.setValue(Integer.parseInt(constVal.getValue().toString().replaceAll("[-+.^:,']",""))); //remove - from date stringS
				}
				else{
					havingGremlin.setValue((String)constVal.getValue().toString()); //normal string
				}
			}
			else{
				havingGremlin.setValue(Integer.parseInt(constVal.getValue().toString())); //is an integer
			}						
		}
		else{
			ListExpression listExpression = (ListExpression) compExpr.getRightExpr();
			ArrayList<Expr> listExpressionValue = (ArrayList<Expr>)listExpression.getElements();
			
			ArrayList<Object> withinValueList = new ArrayList<Object>();
			
			//get values from list expression for IN clause and make a string for gremlin ('A','B','C','D') (1,2,3,4)
			for(int j = 0; j < listExpressionValue.size(); j++){
				ConstantVal value = (ConstantVal) listExpressionValue.get(j);
				if(value.getType().toString().equals("STRING")){
					withinValueList.add((String)value.getValue().toString());
				}
				else{
					withinValueList.add(Integer.parseInt(value.getValue().toString()));
				}
			}
			havingGremlin.setValue(withinValueList);
		}
		
		havingGremlinList.add(havingGremlin);
		
		return havingGremlinList;
	}
	
	public static HashMap<String, ArrayList<String>> getSubgraphRelations(ArrayList<ArrayList<String>> paths,HashMap<String, String> newConcepts){
		HashMap<String, ArrayList<String>> subgraphRelations = new HashMap<String, ArrayList<String>>();
		for(int i=0; i<paths.size(); i++){
			if(!paths.get(i).get(1).contains("isa")){
				ArrayList<String> sourceDest = new ArrayList<String>();
				sourceDest.add(newConcepts.get(paths.get(i).get(0)));
				sourceDest.add(newConcepts.get(paths.get(i).get(2)));
				subgraphRelations.put(paths.get(i).get(1), sourceDest);
			}
		}
		return subgraphRelations;
	}
	
	public static HashMap<String, String> getConcepts(ArrayList<String> concepts){
		HashMap<String, String> newConcepts = new HashMap<String, String>();
		for(int i=0; i<concepts.size(); i++){
			newConcepts.put(concepts.get(i), concepts.get(i));
		}
		return newConcepts;
	}
	
	public static HashMap<String, String> getNewConcepts(HashMap<String, String> newConcepts, ArrayList<ArrayList<String>> paths){
		for(int i=0; i<paths.size(); i++){
			if(paths.get(i).get(1).contains("isa")){
				newConcepts.put(paths.get(i).get(0), paths.get(i).get(0)+"','"+paths.get(i).get(2));
				newConcepts.put(paths.get(i).get(2), paths.get(i).get(0)+"','"+paths.get(i).get(2));
			}
			else{
				newConcepts.put(paths.get(i).get(0), paths.get(i).get(0));
				newConcepts.put(paths.get(i).get(2), paths.get(i).get(2));
			}
		}
		System.out.println(newConcepts.toString());
		return newConcepts;
	}
	
	//Create gremlin steps' objects from OQL objects and pack it in single gremlin object
	public Gremlin getGremlin(OQLQuery oqlQuery, ArrayList<ArrayList<String>> paths, ArrayList<String> concepts){
		ArrayList<SelectGremlin> selectGremlinList = new ArrayList<SelectGremlin>();
		ArrayList<FromGremlin> fromGremlinList = new ArrayList<FromGremlin>();
		ArrayList<FilterGremlin> filterGremlinList = new ArrayList<FilterGremlin>();
		ArrayList<RelationGremlin> relationGremlinList = new ArrayList<RelationGremlin>();
		ArrayList<GroupByGremlin> groupByGremlinList = new ArrayList<GroupByGremlin>();
		ArrayList<OrderByGremlin> orderByGremlinList = new ArrayList<OrderByGremlin>();
		ArrayList<HavingGremlin> havingGremlinList = new ArrayList<HavingGremlin>();
		HashMap<String, String> newConcepts = new HashMap<String, String>();
		
		HashMap<String, ArrayList<String>> subgraphRelations = new HashMap<String, ArrayList<String>>();
		
		newConcepts = getConcepts(concepts);
		newConcepts = getNewConcepts(newConcepts, paths);
		
		subgraphRelations = getSubgraphRelations(paths,newConcepts);
		
		Gremlin gremlin = new Gremlin();
		
		HashMap<String,String> labelAliasMap = new HashMap<String,String>();
		
		fromGremlinList = getFromList(oqlQuery.getFrom(),newConcepts);
		
		//create hasmap of label and alias for future references from fromList
		for(int i=0; i < fromGremlinList.size(); i++){
			//System.out.println(fromList.get(i).getAlias());
			//System.out.println(fromList.get(i).getLabel());
			labelAliasMap.put(fromGremlinList.get(i).getAlias(), fromGremlinList.get(i).getLabel());
		}
		
		selectGremlinList = getSelectList(oqlQuery.getSelect(), labelAliasMap);
		/*for(int i=0; i < selectList.size(); i++){
			System.out.println(selectList.get(i).getAlias());
			System.out.println(selectList.get(i).getLabel());
			System.out.println(selectList.get(i).getProperty());
		}*/
		
		
		if(oqlQuery.getWhereClause() != null){
			filterGremlinList = getFilterList(oqlQuery.getWhereClause(), labelAliasMap);
			if(filterGremlinList != null){
				for(int i=0; i < filterGremlinList.size(); i++){
					//System.out.print(filterList.get(i).getLabel() + " " + filterList.get(i).getAlias() + " " + filterList.get(i).getProperty()
					//		+ " " + filterList.get(i).getOperator() + " " + filterList.get(i).getValue().toString() + "\n");
				}
			}
		}
		else{
			filterGremlinList = null;
		}
		
		if(oqlQuery.getWhereClause() != null){
			relationGremlinList = getRelationList(oqlQuery.getWhereClause(), labelAliasMap, subgraphRelations);
			if(relationGremlinList != null){
				for(int i =0; i < relationGremlinList.size(); i++){
					//System.out.println(relationGremlinList.get(i).getAliasLeft() + " " + relationGremlinList.get(i).getLabelLeft() + " " +
					//			relationGremlinList.get(i).getRelation() + " " + relationGremlinList.get(i).getAliasRight() + " " + relationGremlinList.get(i).getLabelRight());
				}
			}
		}
		else{
			relationGremlinList = null;
		}
		
		if(oqlQuery.getGroupBy() != null){
			groupByGremlinList = getGroupByList(oqlQuery.getGroupBy(), labelAliasMap);
			//System.out.println(groupByList.toString());
		}
		else{
			groupByGremlinList = null;
		}
		
		if(oqlQuery.getOrderBy() != null){
			orderByGremlinList = getOrderByList(oqlQuery.getOrderBy(), labelAliasMap);
			//System.out.println(orderByList.toString());
		}
		else{
			orderByGremlinList = null;
		}
		
		if(oqlQuery.getHavingClause() != null){
			havingGremlinList = getHavingList(oqlQuery.getHavingClause(), labelAliasMap);
		}
		else{
			havingGremlinList = null;
		}
		
		gremlin.setLabelAliasMap(labelAliasMap);
		gremlin.setSelectGremlinList(selectGremlinList);
		gremlin.setFromGremlinList(fromGremlinList);
		gremlin.setRelationGremlinList(relationGremlinList);
		gremlin.setFilterGremlinList(filterGremlinList);
		gremlin.setGroupByGremlinList(groupByGremlinList);
		gremlin.setOrderByGremlinList(orderByGremlinList);
		gremlin.setHavingGremlinList(havingGremlinList);
		
		return gremlin;
		
	}
	
	
	
	public static boolean isDate(String value){
	
		if(value.substring(4, 5).equals("-") && value.substring(7, 8).equals("-")){
			return true;
		}
		else{
			return false;
		}
	}
}
