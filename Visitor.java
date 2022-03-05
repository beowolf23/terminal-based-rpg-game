package com.company;

public interface Visitor<T extends Entity>
{
	boolean visit(T entity);

}
