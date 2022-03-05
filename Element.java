package com.company;

public interface Element<T extends Entity>
{
	public boolean accept(Visitor<T> visitor);

}
