package com.company;

import java.util.Random;

public class Warrior extends Character
{
	// method for creating a Warrior
	public Warrior(String name, int currentXp, int currentLevel)
	{
		super(name, currentXp, currentLevel);
		this.currentLife = 100;
		this.currentMana = 100;
		this.fireProtection = true;
		this.iceProtection = false;
		this.earthProtection = false;
		this.inventory = new Inventory(10);
		this.strength = 4;
		this.dexterity = 2;
		this.charisma = 1;
	}

	@Override
	public void getXp(int xpValue)
	{

		this.currentXp += xpValue;
		if (currentXp > xpPerLevel)
		{
			currentXp -= xpPerLevel;
			currentLevel += 1;
			xpPerLevel += 5;
			this.strength += 4;
			this.dexterity += 2;
			this.charisma += 1;
		}
	}

	@Override
	public int receiveDamage(int damage)
	{
		Random randomObj = new Random();
		boolean halfDamage = randomObj.nextBoolean();
		int receiveDamage = super.charisma * damage / 4 + super.dexterity * damage / 4;
		if (halfDamage)
		{
			this.currentLife -= receiveDamage / 2;
			return receiveDamage / 2;
		}
		this.currentLife -= receiveDamage;
		return receiveDamage;
	}

	@Override
	public int dealDamage()
	{
		Random randomObj = new Random();
		boolean doubleDamage = randomObj.nextBoolean();
		if (doubleDamage)
		{
			return 16 * super.strength;
		}
		return 8 * super.strength;
	}

	@Override
	public boolean accept(Visitor<Entity> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "Warrior  Level: " + this.currentLevel + "  XP: " + this.currentXp;
	}

}
