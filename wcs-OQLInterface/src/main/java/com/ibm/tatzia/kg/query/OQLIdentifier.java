// � Copyright IBM Corp. 2011, 2012
package com.ibm.tatzia.kg.query;


public class OQLIdentifier extends Expr {
	
	private AliasRef aliasref;
	private OntologyProperty property;
	
	public void setAliasref(AliasRef aliasref) {
		this.aliasref = aliasref;
	}
	public AliasRef getAliasref() {
		return aliasref;
	}
	public OntologyProperty getProperty() {
		return property;
	}
	public void setProperty(OntologyProperty property) {
		this.property = property;
	}
	@Override
	public String toString() {
		return "OQLIdentifier [aliasref=" + aliasref + ", property=" + property + "]";
	}
	
	
	
	
} // end class 

