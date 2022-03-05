package com.company;

import java.util.ArrayList;

public class Inventory
{
	public Integer maxWeight;
	public Integer currency;
	public Integer currentWeight;
	ArrayList<Potion> potionsList;

	public Inventory(int maxWeight)
	{
		potionsList = new ArrayList<Potion>();
		currency = 40;
		currentWeight = 0;
		this.maxWeight = 10;
	}

	public void addPotion(Potion potion)
	{
		int potionWeight = potion.getWeightValue();
		currentWeight += potionWeight;
		potionsList.add(potion);
	}

	public void removePotion(Potion potion)
	{
		if (potionsList.contains((Potion) potion))
		{
			int index = potionsList.indexOf((Potion) potion);
			Potion toBeRemoved = potionsList.get(index);
			potionsList.remove((Potion) potion);
		}
	}

	public int leftWeight()
	{
		return maxWeight - currentWeight;
	}

}
