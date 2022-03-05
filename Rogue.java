package com.company;

import java.util.Random;

public class Rogue extends Character
{
	// method for creating a Rogue
	public Rogue(String name, int currentXp, int currentLevel)
	{
		super(name, currentXp, currentLevel);
		this.currentLife = 100;
		this.currentMana = 100;
		this.fireProtection = false;
		this.iceProtection = false;
		this.earthProtection = true;
		this.inventory = new Inventory(7);
		this.strength = 2;
		this.dexterity = 4;
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
			this.dexterity += 4;
			this.strength += 2;
			this.charisma += 1;
		}

	}

	@Override
	public int receiveDamage(int damage)
	{
		Random randomObj = new Random();
		boolean halfDamage = randomObj.nextBoolean();
		int receiveDamage = super.charisma * damage / 4 + super.strength * damage / 4;
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
			return 8 * super.dexterity;
		}
		return 4 * super.dexterity;
	}

	@Override
	public boolean accept(Visitor<Entity> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "Rogue  Level: " + this.currentLevel + "  XP: " + this.currentXp;
	}

}
