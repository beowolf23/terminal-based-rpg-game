package com.company;

public class ManaPotion implements Potion
{

	private final int price = 20;
	private final int weight = 1;
	private final int regenValue = 60;

	@Override
	public void usePotion(Entity entity)
	{
		entity.manaRegen(regenValue);
	}

	@Override
	public int getPrice()
	{
		return price;
	}

	@Override
	public int getRegenValue()
	{
		return regenValue;
	}

	@Override
	public int getWeightValue()
	{
		return weight;
	}

	@Override
	public String toString()
	{
		return "ManaPotion   {" +
				"price=" + price +
				", weight=" + weight +
				", regenValue=" + regenValue +
				'}';
	}

}
