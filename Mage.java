package com.company;

import java.util.Random;

public class Mage extends Character
{
	// creating a Mage
	public Mage(String name, int currentXp, int currentLevel)
	{
		super(name, currentXp, currentLevel);
		this.currentLife = 100;
		this.currentMana = 100;
		this.fireProtection = false;
		this.iceProtection = true;
		this.earthProtection = false;
		this.inventory = new Inventory(5);
		this.strength = 1;
		this.dexterity = 2;
		this.charisma = 4;
	}

	// method for receiving XP and levelling up a Mage
	@Override
	public void getXp(int xpValue)
	{
		this.currentXp += xpValue;
		if (currentXp > xpPerLevel)
		{
			currentXp -= xpPerLevel;
			currentLevel += 1;
			xpPerLevel += 5;
			this.charisma += 4;
			this.dexterity += 2;
			this.strength += 1;
		}
	}

	@Override
	public int receiveDamage(int damage)
	{
		Random randomObj = new Random();
		boolean halfDamage = randomObj.nextBoolean();
		int receiveDamage = super.dexterity * damage / 4 + super.strength * damage / 4;
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
			return 8 * super.charisma;
		}
		return 4 * super.charisma;
	}

	@Override
	public boolean accept(Visitor<Entity> visitor)
	{
		return visitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "Mage  Level: " + this.currentLevel + "  XP: " + this.currentXp;
	}

}
