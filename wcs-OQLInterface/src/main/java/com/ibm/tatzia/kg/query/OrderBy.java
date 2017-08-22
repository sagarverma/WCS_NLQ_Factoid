package com.ibm.tatzia.kg.query;

import java.util.ArrayList;
import java.util.List;

public class OrderBy {

	List<OrderByArgument> orderByArguments;
	transient String orderByNlString;
	
	public OrderBy() {
		super();
		this.orderByArguments = new ArrayList<OrderByArgument>();
	}
	public List<OrderByArgument> getOrderByArguments() {
		return orderByArguments;
	}
	public void addOrderByArgument(OrderByArgument orderByArgument) {
		this.orderByArguments.add(orderByArgument);
	}
	public void setOrderByArguments(List<OrderByArgument> orderByArguments) {
		this.orderByArguments = orderByArguments;
	}
	public String getOrderByNlString() {
		return orderByNlString;
	}
	public void setOrderByNlString(String orderByNlString) {
		this.orderByNlString = orderByNlString;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderByArguments == null) ? 0 : orderByArguments.hashCode());
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
		OrderBy other = (OrderBy) obj;
		if (orderByArguments == null) {
			if (other.orderByArguments != null)
				return false;
		} else if (!orderByArguments.equals(other.orderByArguments))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OrderBy [orderByArguments=" + orderByArguments + "]";
	}
	
	
}
