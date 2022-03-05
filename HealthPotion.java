package com.company;

public class HealthPotion implements Potion
{
	private final int price = 20;
	private final int weight = 1;
	private final int regenValue = 30;

	@Override
	public void usePotion(Entity entity)
	{
		entity.lifeRegen(regenValue);
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
		return "HealthPotion {" +
				"price=" + price +
				", weight=" + weight +
				", regenValue=" + regenValue +
				'}';
	}

}
