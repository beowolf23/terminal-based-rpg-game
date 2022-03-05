package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity implements CellElement
{
	public int standardDamage = 15;

	// creating an enemy
	public Enemy()
	{
		Random randomObj = new Random();
		this.currentLife = 100;
		this.currentMana = 100;
		this.earthProtection = randomObj.nextBoolean();
		this.fireProtection = randomObj.nextBoolean();
		this.iceProtection = randomObj.nextBoolean();
		this.spellList = new ArrayList<Spell>();
		for (int i = 0; i < randomObj.nextInt(4 - 2) + 2; i++)
		{
			int randomNumber = randomObj.nextInt(3);
			if (randomNumber == 0)
				spellList.add(new Fire());
			if (randomNumber == 1)
				spellList.add(new Ice());
			if (randomNumber == 2)
				spellList.add(new Earth());
		}
	}

	@Override
	public char toCharacter()
	{
		return 'Ë';
	}

	// get damage
	@Override
	public int receiveDamage(int damage)
	{
		Random randomObj = new Random();
		boolean parseDamage = randomObj.nextBoolean();
		if (parseDamage)
			return 0;
		this.currentLife -= damage;
		return damage;

	}

	// how much damage the enemy deals
	@Override
	public int dealDamage()
	{
		Random randomObj = new Random();
		boolean doubleDamage;
		int chance = randomObj.nextInt(5);
		if (chance == 0)
		{
			int spellListLen = spellList.size();
			chance = randomObj.nextInt(spellListLen);
			Spell toDeal = spellList.get(chance);
			if (currentMana > toDeal.manaCost)
			{
				currentMana -= toDeal.manaCost;
				doubleDamage = randomObj.nextBoolean();
				if (doubleDamage)
				{
					System.out.println("The enemy used a spell and critically striked you!");
					System.out.println();
					return 2 * toDeal.damage;
				}
				System.out.println("The enemy used a spell!");
				System.out.println();
				return toDeal.damage;
			}
			else
			{
				System.out.println("The enemy used a spell but he forgot his mana at home!");
				System.out.println();
				return 0;
			}
		}
		else
		{
			doubleDamage = randomObj.nextBoolean();
			if (doubleDamage)
			{
				System.out.println("The enemy punched you and critically striked!");
				return 2 * standardDamage;
			}
			System.out.println("The enemy punched you!");
			return standardDamage;
		}
	}

	@Override
	public boolean accept(Visitor visitor)
	{
		return visitor.visit(this);
	}

}
