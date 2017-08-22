// � Copyright IBM Corp. 2011, 2012
package com.ibm.tatzia.kg.query;

import java.util.ArrayList;

public class OQLNestedQuery extends Expr{
	
	public OQLQuery _query; // always not null
	public OQLNestedQuery _nestedquery; // possibly null
	Boolean union_distinct = null; // UNION or UNION DISTINCT (true)
	
	public OQLQuery get_query() {
		return _query;
	}
	public void set_query(OQLQuery _query) {
		this._query = _query;
	}
	public OQLNestedQuery get_nestedquery() {
		return _nestedquery;
	}
	public void set_nestedquery(OQLNestedQuery _nestedquery) {
		this._nestedquery = _nestedquery;
	}
	public boolean isUnion_distinct() {
		return union_distinct;
	}
	public void setUnion_distinct(boolean union_distinct) {
		this.union_distinct = union_distinct;
	}
	@Override
	public String toString() {
		return "OQLNestedQuery [_query=" + _query + ", _nestedquery=" + _nestedquery + ", uniondistinct=" + union_distinct + "]";
	}
	
		
	
}