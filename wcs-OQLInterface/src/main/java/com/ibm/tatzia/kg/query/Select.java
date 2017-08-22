package com.ibm.tatzia.kg.query;

import java.util.ArrayList;
import java.util.List;

public class Select {
	List<SelectColumn> selectColumns;
	boolean hasDistinct;
	public boolean ishasDistinct() {
		return hasDistinct;
	}

	public void sethasDistinct(boolean hasDistinct) {
		this.hasDistinct = hasDistinct;
	}

	
		public List<SelectColumn> getSelectColumns() {
		return selectColumns;
	}

	public void setSelectColumns(List<SelectColumn> selectColumns) {
		this.selectColumns = selectColumns;
	}

		transient String selectNlString;

	public Select() {
		super();
		selectColumns = new ArrayList<SelectColumn>();
	}

	public String getSelectNlString() {
		return selectNlString;
	}
	
	public void setSelectNlString(String selectNlString) {
		this.selectNlString = selectNlString;
	}


	public void addselectColumn(SelectColumn sc){
		if(!this.selectColumns.contains(sc))
			this.selectColumns.add(sc);
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((selectColumns == null) ? 0 : selectColumns.hashCode());
		
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
		Select other = (Select) obj;
		if (selectColumns == null) {
			if (other.selectColumns != null)
				return false;
		} else if (!selectColumns.equals(other.selectColumns))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Select [selectColumns=" + selectColumns + ", hasDistinct=" + hasDistinct + "]";
	}
	
	
}
