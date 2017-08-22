package com.ibm.tatzia.kg.query;

import java.util.ArrayList;
import java.util.List;

public class GroupBy {
	List<OQLIdentifier> groupByArguments;
	

	

	public GroupBy() {
		super();
		groupByArguments = new ArrayList<OQLIdentifier>();
	}
	

	public List<OQLIdentifier> getGroupByArguments() {
		return groupByArguments;
	}

	public void addGroupByArgument(OQLIdentifier groupByArgument) {
		this.groupByArguments.add(groupByArgument);
	}
	public void setGroupByArguments(List<OQLIdentifier> groupByArguments) {
		this.groupByArguments = groupByArguments;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((groupByArguments == null) ? 0 : groupByArguments.hashCode());
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
		GroupBy other = (GroupBy) obj;
		if (groupByArguments == null) {
			if (other.groupByArguments != null)
				return false;
		} else if (!groupByArguments.equals(other.groupByArguments))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "GroupBy [groupByArguments=" + groupByArguments + "]";
	}
	
	
	
}
