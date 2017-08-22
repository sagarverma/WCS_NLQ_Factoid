package com.ibm.tatzia.kg.query;

//import java.util.StringJoiner;
import java.util.HashMap;


public class OQLQuery  extends OQLNestedQuery{

	Select select;
	From from;
	Where whereClause;
	GroupBy groupBy;
	Having havingClause;
	OrderBy orderBy;
	int fetchFirst;
	int offset;
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getOffset() {
		return offset;
	}
	public int getFetchFirst() {
		return fetchFirst;
	}

	public void setFetchFirst(int fetchFirst) {
		this.fetchFirst = fetchFirst;
	}

	public OQLQuery() {
		super();
	}
	
	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}

	public Select getSelect() {
		return select;
	}

	public void setSelect(Select select) {
		this.select = select;
	}
	
	public From getFrom() {
		return from;
	}

	public void setFrom(From from) {
		this.from = from;
	}


	public GroupBy getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(GroupBy groupBy) {
		this.groupBy = groupBy;
	}

		
	/**
	 * @Abdul: Added a method to compute the concept mapping from the From Arguments
	 * @param _kgq
	 * @return
	 */
	public HashMap<String,String> getConceptMapping(){
		HashMap<String, String> conceptMapping = new HashMap<String, String>();
		for(FromArgument fmArgument: from.getFromClause()){
			if(!fmArgument.isIntermediate()){
				conceptMapping.put(fmArgument.getAlias().name,fmArgument.getConcept().getName());
			}
		}
		return conceptMapping;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((groupBy == null) ? 0 : groupBy.hashCode());
		result = prime * result + ((havingClause == null) ? 0 : havingClause.hashCode());
		result = prime * result + ((select == null) ? 0 : select.hashCode());
		result = prime * result + ((whereClause == null) ? 0 : whereClause.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OQLQuery other = (OQLQuery) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (groupBy == null) {
			if (other.groupBy != null)
				return false;
		} else if (!groupBy.equals(other.groupBy))
			return false;
		if (havingClause == null) {
			if (other.havingClause != null)
				return false;
		} else if (!havingClause.equals(other.havingClause))
			return false;
		if (select == null) {
			if (other.select != null)
				return false;
		} else if (!select.equals(other.select))
			return false;
		if (whereClause == null) {
			if (other.whereClause != null)
				return false;
		} else if (!whereClause.equals(other.whereClause))
			return false;
		return true;
	}
	
	public Where getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(Where whereClause) {
		this.whereClause = whereClause;
	}

	public Having getHavingClause() {
		return havingClause;
	}

	public void setHavingClause(Having havingClause) {
		this.havingClause = havingClause;
	}
	@Override
	public String toString() {
		return "OQLQuery [select=" + select + ", from=" + from + ", whereClause=" + whereClause + ", groupBy=" + groupBy
				+ ", havingClause=" + havingClause + ", orderBy=" + orderBy + ", fetchFirst=" + fetchFirst + ", offset="
				+ offset + "]";
	}


	/*public void buildKGQL() {
		StringBuffer kgqlBuf = new StringBuffer();
	//	StringJoiner sj = null;
		
		//SELECT
		kgqlBuf.append("SELECT");
		kgqlBuf.append(" ");
		
	//	sj = new StringJoiner(",");
		ArrayList<String> joinStrs = new ArrayList<String>();
		for(SelectColumn agg: select.getSelectColumns()){
		//	sj.add(agg.getSyntax());
			joinStrs.add(agg.getSyntax());
		}
		for(DisplayArgument da: select.getDisplay().getDisplayArguments()){
		//	sj.add(da.getSyntax());
			joinStrs.add(da.getSyntax());
		}
		String [] joinArr = new String[joinStrs.size()];
		joinArr  = joinStrs.toArray(joinArr);
		
	//	kgqlBuf.append(sj.toString());
		kgqlBuf.append(StringUtils.join(joinArr, " , "));
		kgqlBuf.append(" ");
		 
		//FROM
		kgqlBuf.append("FROM");
		kgqlBuf.append(" ");
		
	//	sj = new StringJoiner(",");
		joinStrs = new ArrayList<String>();
		for(FromClause fa: from.getFromClause())
		//	sj.add(fa.getSyntax());
			joinStrs.add(fa.getSyntax());
		}
		joinArr = new String[joinStrs.size()];
		joinArr  = joinStrs.toArray(joinArr);
	//	kgqlBuf.append(sj.toString());
		kgqlBuf.append(StringUtils.join(joinArr, " , "));
		kgqlBuf.append(" ");
		
		//WHERE
		if(whereClause!=null){
			if(!(whereClause.getExpr()!=null)
					|| !whereClause.getBinExpr2s().isEmpty()){

				kgqlBuf.append("WHERE");
				kgqlBuf.append(" ");
 
			//	sj = new StringJoiner(" AND ");
				joinStrs = new ArrayList<String>();
				if(!(whereClause.getExpr()!=null)){
						joinStrs.add(whereClause.getExpr().toString());
					}
				
				if(!whereClause.getBinExpr2s().isEmpty()){
					for(BinExpr2 binexpr2: whereClause.getBinExpr2s()){
					//	sj.add(binexpr2.getSyntax());
						joinStrs.add(binexpr2.getSyntax());
					}
				}
				joinArr = new String[joinStrs.size()];
				joinArr  = joinStrs.toArray(joinArr);
		//		kgqlBuf.append(sj.toString());
				kgqlBuf.append(StringUtils.join(joinArr, " AND "));
				kgqlBuf.append(" ");	
			}
		}
		
		//GROUPBY
		if(groupBy !=null 
				&& !groupBy.getGroupByArguments().isEmpty()){
			kgqlBuf.append("GROUP BY");
			kgqlBuf.append(" ");
			
		//	sj = new StringJoiner(",");
			joinStrs = new ArrayList<String>();
			for(GroupByArgument gba: groupBy.getGroupByArguments()){
			//	sj.add(gba.getSyntax());
				joinStrs.add(gba.getSyntax());
			}
			joinArr = new String[joinStrs.size()];
			joinArr  = joinStrs.toArray(joinArr);
			//	kgqlBuf.append(sj.toString());
			kgqlBuf.append(StringUtils.join(joinArr, " , "));
		
			kgqlBuf.append(" ");
		}
		
		this.KGQL = kgqlBuf.toString();
	}
*/

	
}
