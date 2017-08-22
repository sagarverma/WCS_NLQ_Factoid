package com.ibm.tatzia.kg.query;

import java.util.List;

import com.ibm.tatzia.kg.query.FromArgument;

public class From {
	List<FromArgument> fromClause;
	
	public List<FromArgument> getFromClause() {
		return fromClause;
	}

	public void setFromClause(List<FromArgument> fromClause) {
		this.fromClause = fromClause;
	}

	public From() {
		super();
		
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fromClause == null) ? 0 : fromClause.hashCode());
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
		From other = (From) obj;
		if (fromClause == null) {
			if (other.fromClause != null)
				return false;
		} else if (!fromClause.equals(other.fromClause))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "From [fromClause=" + fromClause + "]";
	}

	
	
}
