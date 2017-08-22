package com.ibm.tatzia.query.parser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.ibm.tatzia.kg.query.Alias;
import com.ibm.tatzia.kg.query.AliasRef;
import com.ibm.tatzia.kg.query.AndExpression;
import com.ibm.tatzia.kg.query.BinOp;
import com.ibm.tatzia.kg.query.ColumnRef;
import com.ibm.tatzia.kg.query.CompExpr;
import com.ibm.tatzia.kg.query.ConstantVal;
import com.ibm.tatzia.kg.query.Expr;
import com.ibm.tatzia.kg.query.From;
import com.ibm.tatzia.kg.query.FromArgument;
import com.ibm.tatzia.kg.query.FunctionCall;
import com.ibm.tatzia.kg.query.GroupBy;
import com.ibm.tatzia.kg.query.Having;
import com.ibm.tatzia.kg.query.JoinType;
import com.ibm.tatzia.kg.query.ListExpression;
import com.ibm.tatzia.kg.query.NotExpression;
import com.ibm.tatzia.kg.query.OQLIdentifier;
import com.ibm.tatzia.kg.query.OQLNestedQuery;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.tatzia.kg.query.ORExpression;
import com.ibm.tatzia.kg.query.Obj;
import com.ibm.tatzia.kg.query.ObjExpr;
import com.ibm.tatzia.kg.query.OntologyConcept;
import com.ibm.tatzia.kg.query.OntologyProperty;
import com.ibm.tatzia.kg.query.OrderBy;
import com.ibm.tatzia.kg.query.OrderByArgument;
import com.ibm.tatzia.kg.query.OrderByType;
import com.ibm.tatzia.kg.query.ParenExpr;
import com.ibm.tatzia.kg.query.PathExpression;
import com.ibm.tatzia.kg.query.Select;
import com.ibm.tatzia.kg.query.SelectColumn;
import com.ibm.tatzia.kg.query.Where;
import com.ibm.tatzia.ontology.DataType;
import com.ibm.wcs.kg.OQLListener;
import com.ibm.wcs.kg.OQLParser.AliasContext;
import com.ibm.wcs.kg.OQLParser.AliasrefContext;
import com.ibm.wcs.kg.OQLParser.AndExprContext;
import com.ibm.wcs.kg.OQLParser.BExprContext;
import com.ibm.wcs.kg.OQLParser.BinopContext;
import com.ibm.wcs.kg.OQLParser.BoolContext;
import com.ibm.wcs.kg.OQLParser.CompExprContext;
import com.ibm.wcs.kg.OQLParser.ConceptContext;
import com.ibm.wcs.kg.OQLParser.ConstantValContext;
import com.ibm.wcs.kg.OQLParser.FetchContext;
import com.ibm.wcs.kg.OQLParser.FromArgumentContext;
import com.ibm.wcs.kg.OQLParser.FromContext;
import com.ibm.wcs.kg.OQLParser.FuncCallContext;
import com.ibm.wcs.kg.OQLParser.FuncNameContext;
import com.ibm.wcs.kg.OQLParser.GroupByContext;
import com.ibm.wcs.kg.OQLParser.HavingContext;
import com.ibm.wcs.kg.OQLParser.JoinTypeContext;
import com.ibm.wcs.kg.OQLParser.ListExprContext;
import com.ibm.wcs.kg.OQLParser.NestedQueryContext;
import com.ibm.wcs.kg.OQLParser.NotExprContext;
import com.ibm.wcs.kg.OQLParser.ObjExprContext;
import com.ibm.wcs.kg.OQLParser.ObjectContext;
import com.ibm.wcs.kg.OQLParser.OffsetContext;
import com.ibm.wcs.kg.OQLParser.OqlIdentifierContext;
import com.ibm.wcs.kg.OQLParser.OqlQueryContext;
import com.ibm.wcs.kg.OQLParser.OrExprContext;
import com.ibm.wcs.kg.OQLParser.OrderByArgumentContext;
import com.ibm.wcs.kg.OQLParser.OrderByContext;
import com.ibm.wcs.kg.OQLParser.ParenExprContext;
import com.ibm.wcs.kg.OQLParser.PathExprContext;
import com.ibm.wcs.kg.OQLParser.PropertyContext;
import com.ibm.wcs.kg.OQLParser.RelationNameContext;
import com.ibm.wcs.kg.OQLParser.SelectColumnContext;
import com.ibm.wcs.kg.OQLParser.SelectContext;
import com.ibm.wcs.kg.OQLParser.SelectExprContext;
import com.ibm.wcs.kg.OQLParser.UExprContext;
import com.ibm.wcs.kg.OQLParser.VariableContext;
import com.ibm.wcs.kg.OQLParser.VariablerefContext;
import com.ibm.wcs.kg.OQLParser.WhereContext;

class Context{
	Map<String,Alias> defs = new HashMap<String,Alias>();
	Map<String,SelectColumn> columnNames = new HashMap<String,SelectColumn>();
	List<AliasRef> uses = new ArrayList<AliasRef>();	
}

public class GrammarVisitor implements OQLListener {
	Map<Object,Object> infostack = new HashMap<Object,Object>(); 
	Stack<Context> activation_record = new Stack<Context>();
	OQLNestedQuery start = null;
	
	Alias resolveAliasRef(String name){
		int i = activation_record.size();
		while(i>0){
			Alias a = activation_record.get(i-1).defs.get(name);
			if (a!=null){
				return a;
			}
			i--;
		}
		System.err.println(name + " not resolved");
		return null;
	}
	void putDef(String name, Alias ref){
		activation_record.peek().defs.put(name, ref);
	}
	void putColumnName(SelectColumn c){
		activation_record.peek().columnNames.put(c.getAlias(), c);
	}
	SelectColumn resolveColumnName(String columnName){
		for(String u: activation_record.peek().columnNames.keySet()){
			if(u.equalsIgnoreCase(columnName)){
				return activation_record.peek().columnNames.get(u);
			}
		}
		System.err.println(columnName + " columnname not resolved");		
		return null;
	}
	void addUse(AliasRef a){
		activation_record.peek().uses.add(a);
	}
	void resolve(){
		for(AliasRef u: activation_record.peek().uses){
			Alias a = resolveAliasRef(u.getName());
			u.setAlias(a);
		}
	}
	void push(){
		activation_record.push(new Context());
	}
	void pop(){
		activation_record.pop();
	}
	
	@Override
	public void visitTerminal(TerminalNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterSelect(SelectContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitSelect(SelectContext ctx) {
		// TODO Auto-generated method stub
		Select s = new Select();
		if(ctx.DISTINCT()!=null){
			s.sethasDistinct(true);
		}
		ArrayList<SelectColumn> cols = new ArrayList<SelectColumn>();
		for(SelectColumnContext sctx : ctx.selectColumn()){
			cols.add((SelectColumn)infostack.get(sctx));
		}
		s.setSelectColumns(cols);		
		infostack.put(ctx, s);
	}

	@Override
	public void enterOrderByArgument(OrderByArgumentContext ctx) {
		// TODO Auto-generated method stub
	}

	@Override
	public void exitOrderByArgument(OrderByArgumentContext ctx) {
		OrderByArgument a = new OrderByArgument();
		if(ctx.oqlIdentifier()!=null){		
			a.setOqlOrg((OQLIdentifier)infostack.get(ctx.oqlIdentifier()));
			a.setOQLIdentier(true);
		}else{
			a.setOQLIdentier(false);
			a.setStrOrg(ctx.variable().getText());
			a.setCol(resolveColumnName(ctx.variable().getText()));
		}
		if(ctx.ORDERTYPE()!=null){
			if(ctx.ORDERTYPE().getText().equalsIgnoreCase("DESC"))
				a.setOtype(OrderByType.Descending);
			else
				a.setOtype(OrderByType.Ascending);
		}		
		
		infostack.put(ctx, a);
	}

	@Override
	public void enterConcept(ConceptContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitConcept(ConceptContext ctx) {
		OntologyConcept o = new OntologyConcept();
		o.setName(ctx.getText());
		infostack.put(ctx, o);
	}

	@Override
	public void enterObjExpr(ObjExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitObjExpr(ObjExprContext ctx) {
		ObjExpr o = new ObjExpr();
		o.setObject((Obj)infostack.get(ctx.object()));
		ArrayList<String> rs = new ArrayList<String>();
		for(RelationNameContext c: ctx.relationName()){
			rs.add(c.getText());
		}		
		o.setRelations(rs);
		infostack.put(ctx, o);
	}

	@Override
	public void enterOrderBy(OrderByContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitOrderBy(OrderByContext ctx) {
		OrderBy o = new OrderBy();
		for(OrderByArgumentContext o1: ctx.orderByArgument()){
			o.addOrderByArgument((OrderByArgument) infostack.get(o1));
		}		
		infostack.put(ctx, o);
	}

	@Override
	public void enterGroupBy(GroupByContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitGroupBy(GroupByContext ctx) {
		// TODO Auto-generated method stub
		GroupBy g = new GroupBy();
		ArrayList<OQLIdentifier> ids = new ArrayList<OQLIdentifier>();
		for(OqlIdentifierContext o:ctx.oqlIdentifier()){
			ids.add((OQLIdentifier)infostack.get(o));
		}
		g.setGroupByArguments(ids);		
		infostack.put(ctx, g);
	}

	@Override
	public void enterParenExpr(ParenExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitParenExpr(ParenExprContext ctx) {
		if(ctx.LPAREN()!=null){
			ParenExpr p = new ParenExpr();
			p.setExpr((Expr)infostack.get(ctx.bExpr()));
			infostack.put(ctx, p);
		}else{
			infostack.put(ctx, infostack.get(ctx.bExpr()));
		}
	}
	


	@Override
	public void enterFuncName(FuncNameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFuncName(FuncNameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterProperty(PropertyContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitProperty(PropertyContext ctx) {
		OntologyProperty o = new OntologyProperty();
		o.setName(ctx.getText());
		infostack.put(ctx, o);
	}

	@Override
	public void enterAlias(AliasContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAlias(AliasContext ctx) {
		Alias o = new Alias();
		o.setName(ctx.getText());
		infostack.put(ctx, o);
		putDef(o.getName(), o);

	}

	@Override
	public void enterFrom(FromContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFrom(FromContext ctx) {
		From f = new From();
		ArrayList<FromArgument> fas = new ArrayList<FromArgument>();
		f.setFromClause(fas);
		for(FromArgumentContext f1: ctx.fromArgument()){
			fas.add((FromArgument)infostack.get(f1));
		}		
		infostack.put(ctx, f);
	}

	@Override
	public void enterWhere(WhereContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitWhere(WhereContext ctx) {
		Where w = new Where();
		if(ctx.orExpr()!=null){
			w.setExpr((Expr)infostack.get(ctx.orExpr()));
		}
		infostack.put(ctx, w);
	}

	@Override
	public void enterUExpr(UExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitUExpr(UExprContext ctx) {
		if(ctx.selectExpr()!=null){
			infostack.put(ctx, infostack.get(ctx.selectExpr()));			
		}else
		if(ctx.listExpr()!=null){
			infostack.put(ctx, infostack.get(ctx.listExpr()));
		}
		else
		if(ctx.variableref()!=null){
			infostack.put(ctx,infostack.get(ctx.variableref()));
		}
	}

	@Override
	public void enterOqlIdentifier(OqlIdentifierContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitOqlIdentifier(OqlIdentifierContext ctx) {
		OQLIdentifier i = new OQLIdentifier();		
		i.setAliasref((AliasRef)infostack.get(ctx.aliasref()));
		i.setProperty((OntologyProperty)infostack.get(ctx.property()));
		i.getProperty().setAliasref(i.getAliasref());
		infostack.put(ctx, i);
	}

	@Override
	public void enterListExpr(ListExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitListExpr(ListExprContext ctx) {
		ListExpression l = new ListExpression();
		for(UExprContext u: ctx.uExpr()){
			l.addElement((Expr)infostack.get(u));
		}		
		infostack.put(ctx, l);
	}

	@Override
	public void enterHaving(HavingContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitHaving(HavingContext ctx) {
		Having h = new Having();
		h.setExpr((Expr) infostack.get(ctx.orExpr()));
		infostack.put(ctx, h);
	}

	@Override
	public void enterOqlQuery(OqlQueryContext ctx) {
		// TODO Auto-generated method stub
		push();
	}

	@Override
	public void exitOqlQuery(OqlQueryContext ctx) {
		OQLQuery o = new OQLQuery();
		o.setSelect((Select) infostack.get(ctx.select()));
		o.setFrom((From)infostack.get(ctx.from()));
		if(ctx.where()!=null){
			o.setWhereClause((Where)infostack.get(ctx.where()));
		}
		if(ctx.groupBy()!=null){
			o.setGroupBy((GroupBy)infostack.get(ctx.groupBy()));
		}
		if(ctx.having()!=null){
			o.setHavingClause((Having)infostack.get(ctx.having()));
		}
		if(ctx.orderBy()!=null){
			o.setOrderBy((OrderBy)infostack.get(ctx.orderBy()));
		}
		if(ctx.fetch()!=null){
			o.setFetchFirst(((Integer)infostack.get(ctx.fetch())).intValue());
		}		
		if(ctx.offset()!=null){
			o.setOffset(((Integer)infostack.get(ctx.offset())).intValue());
		}		

		infostack.put(ctx, o);
		
		resolve();
		pop();
	}

	@Override
	public void enterConstantVal(ConstantValContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitConstantVal(ConstantValContext ctx) {
		// TODO Auto-generated method stub
		ConstantVal c = new ConstantVal(ctx.getText());
		if(ctx.bool()!=null)
			c.setType(DataType.BOOL);
		else if(ctx.STRING_LITERAL()!=null)
			c.setType(DataType.STRING);
		else if (ctx.NUMERIC_LITERAL()!=null)
			c.setType(DataType.INTEGER);
		
		infostack.put(ctx, c);
	}

	@Override
	public void enterOrExpr(OrExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitOrExpr(OrExprContext ctx) {
		if(ctx.andExpr().size()>1){
			ORExpression or = new ORExpression();
			ArrayList<Expr> as = new ArrayList<Expr>();
			for(AndExprContext a: ctx.andExpr()){
				as.add((Expr)infostack.get(a));
			}
			or.setExprs(as);			
			infostack.put(ctx, or);			
		}else{
			infostack.put(ctx, infostack.get(ctx.andExpr().get(0)));					
		}
		
	}


	@Override
	public void enterBExpr(BExprContext ctx) {
		// TODO Auto-generated method stub
		
	}
//bExpr:     compExpr |  LPAREN orExpr RPAREN | pathExpr;

	@Override
	public void exitBExpr(BExprContext ctx) {
		if(ctx.compExpr()!=null){
			infostack.put(ctx, infostack.get(ctx.compExpr()));
		}
		if(ctx.orExpr()!=null){
			infostack.put(ctx, infostack.get(ctx.orExpr()));
		}
		if(ctx.pathExpr()!=null){
			infostack.put(ctx, infostack.get(ctx.pathExpr()));			
		}
		
	}

	@Override
	public void enterFromArgument(FromArgumentContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFromArgument(FromArgumentContext ctx) {
		FromArgument f = new FromArgument();
		if(ctx.LPAREN()!=null){
			f.setIntermediate(true);
			f.setIntermediateConcept((OQLNestedQuery)infostack.get(ctx.nestedQuery()));
			f.setAlias((Alias)infostack.get(ctx.alias()));
			f.getAlias().setIntermediate(f.getIntermediateConcept());
		}else{
			f.setIntermediate(false);
			f.setAlias((Alias)infostack.get(ctx.alias()));
			f.setConcept((OntologyConcept)infostack.get(ctx.concept()));
			f.getAlias().setO(f.getConcept());
		}
		
		infostack.put(ctx, f);
	}

	@Override
	public void enterNotExpr(NotExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitNotExpr(NotExprContext ctx) {
		if(ctx.NOT()!=null){
			NotExpression n = new NotExpression();
			n.setExpr((Expr)infostack.get(ctx.parenExpr()));
			infostack.put(ctx, n);		

		}else{
			infostack.put(ctx, (Expr)infostack.get(ctx.parenExpr()));					
		}
	}

	@Override
	public void enterRelationName(RelationNameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitRelationName(RelationNameContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterPathExpr(PathExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitPathExpr(PathExprContext ctx) {
		PathExpression p = new PathExpression();
		p.setlObjExpr((ObjExpr)infostack.get(ctx.objExpr(0)));
		if(ctx.joinType()!=null)
		p.setType(JoinType.fromString(ctx.joinType().getText()));
		p.setrObject((ObjExpr)infostack.get(ctx.objExpr(1)));		
		infostack.put(ctx, p);
	}

	@Override
	public void enterFetch(FetchContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFetch(FetchContext ctx) {
		infostack.put(ctx, Integer.parseInt(ctx.NUMERIC_LITERAL().getText()));
	}

	@Override
	public void enterOffset(OffsetContext ctx) {
		
	}

	@Override
	public void exitOffset(OffsetContext ctx) {
		infostack.put(ctx, Integer.parseInt(ctx.NUMERIC_LITERAL().getText()));
	}
	
	@Override
	public void enterFuncCall(FuncCallContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFuncCall(FuncCallContext ctx) {
		FunctionCall c = new FunctionCall();
		c.setFunctionName(ctx.funcName().getText());
		if(ctx.DISTINCT()!=null)
			c.setHasDistinct(true);
		else
			c.setHasDistinct(false);
		ArrayList<Expr> exprs = new ArrayList<Expr>();
		for(SelectExprContext uc: ctx.selectExpr()){
			exprs.add((Expr)infostack.get(uc));
		}
		c.setParams(exprs);
		infostack.put(ctx, c);
	}

	@Override
	public void enterSelectColumn(SelectColumnContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitSelectColumn(SelectColumnContext ctx) {
		// TODO Auto-generated method stub
		SelectColumn s = new SelectColumn();
		if(ctx.variable()!=null){
			s.setAlias(ctx.variable().getText());
			putColumnName(s);
		}
	//	if(ctx.ID()!=null)
	//		s.setAlias(ctx.ID().getText());		
		s.setValue((Expr)infostack.get(ctx.selectExpr()));
		infostack.put(ctx, s);
	}


	@Override
	public void enterAndExpr(AndExprContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAndExpr(AndExprContext ctx) {
		if(ctx.notExpr().size()>1){
			AndExpression and = new AndExpression();
			ArrayList<Expr> ns = new ArrayList<Expr>();
			for(NotExprContext a: ctx.notExpr()){
				ns.add((Expr)infostack.get(a));
			}
			and.setExprs(ns);
			infostack.put(ctx, and);
		}else{
			infostack.put(ctx,  infostack.get(ctx.notExpr().get(0)));			
		}
	}

	@Override
	public void enterObject(ObjectContext ctx) {
		
	}

	@Override
	public void exitObject(ObjectContext ctx) {
		Obj o = new Obj();
		o.setAliasref((AliasRef)infostack.get(ctx.aliasref()));
		if(ctx.nestedQuery()!=null){
			o.setIntermediateQuery(true);
			o.setOqlQuery((OQLNestedQuery) infostack.get(ctx.nestedQuery()));
		}else{
			o.setIntermediateQuery(false);
		}
		infostack.put(ctx, o);
	}

	@Override
	public void enterAliasref(AliasrefContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitAliasref(AliasrefContext ctx) {
		AliasRef a = new AliasRef();
		a.setName(ctx.getText());
		infostack.put(ctx, a);
		addUse(a);
	}
	@Override
	public void enterSelectExpr(SelectExprContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exitSelectExpr(SelectExprContext ctx) {
		// TODO Auto-generated method stub
		if(ctx.funcCall()!=null){
			infostack.put(ctx, infostack.get(ctx.funcCall()));			
		}else
		if(ctx.oqlIdentifier()!=null){
			infostack.put(ctx, infostack.get(ctx.oqlIdentifier()));
		}else
		if(ctx.constantVal()!=null){
			infostack.put(ctx, infostack.get(ctx.constantVal()));
		}else
		if(ctx.nestedQuery()!=null){
			infostack.put(ctx, infostack.get(ctx.nestedQuery()));
		}
	}
	@Override
	public void enterBool(BoolContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exitBool(BoolContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enterBinop(BinopContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exitBinop(BinopContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enterCompExpr(CompExprContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exitCompExpr(CompExprContext ctx) {
		// TODO Auto-generated method stub
		
		CompExpr b = new CompExpr();
		b.setLeftExpr((Expr)infostack.get(ctx.uExpr().get(0)));
		b.setRightExpr((Expr)infostack.get(ctx.uExpr().get(1)));
		String binop = ctx.binop().getText();
		b.setOp(BinOp.fromString(binop));
		infostack.put(ctx, b);

	}
	@Override
	public void enterNestedQuery(NestedQueryContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exitNestedQuery(NestedQueryContext ctx) {
		OQLNestedQuery o;
		if(ctx.UNION()!=null){
			o = new OQLNestedQuery();
			o.set_nestedquery((OQLNestedQuery) infostack.get(ctx.nestedQuery()));
			o.set_query((OQLQuery) infostack.get(ctx.oqlQuery()));
			if(ctx.DISTINCT()!=null){
				o.setUnion_distinct(true);
			}else{
				o.setUnion_distinct(false);			
			}
			infostack.put(ctx, o);
			start = o;

		}else{
			infostack.put(ctx, infostack.get(ctx.oqlQuery()));			
			start = (OQLNestedQuery)infostack.get(ctx.oqlQuery());
		}
	}
	@Override
	public void enterVariable(VariableContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exitVariable(VariableContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enterVariableref(VariablerefContext ctx) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void exitVariableref(VariablerefContext ctx) {
		ColumnRef v=new ColumnRef();
		v.setName(ctx.getText());
		v.setCol(resolveColumnName(ctx.getText()));
		infostack.put(ctx, v);
	}
	@Override
	public void enterJoinType(JoinTypeContext ctx) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void exitJoinType(JoinTypeContext ctx) {
		// TODO Auto-generated method stub
		
	}

}
