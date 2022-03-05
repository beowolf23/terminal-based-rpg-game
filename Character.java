package com.company;

import java.util.ArrayList;
import java.util.Random;

public abstract class Character extends Entity
{
	public Integer xpPerLevel = 50;
	public String name;
	public Integer xCoord;
	public Integer yCoord;
	public Integer currentXp;
	public Integer currentLevel;
	public Integer strength, dexterity, charisma;
	public Inventory inventory;

	// creating a new Character
	public Character(String name, int currentXp, int currentLevel)
	{
		Random randomObj = new Random();
		this.name = name;
		this.currentLevel = currentLevel;
		this.currentXp = currentXp;
		this.spellList = new ArrayList<>();
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

	// method used to know if you have money or space in inventory for buying a potion
	public boolean canBeBought(Potion potion)
	{
		return (potion.getPrice() <= this.inventory.currency) && ((this.inventory.currentWeight +
				potion.getWeightValue()) <= this.inventory.maxWeight);
	}

	public void buyPotion(Potion potion)
	{
		inventory.addPotion(potion);
		inventory.currency -= potion.getPrice();
	}

	public abstract void getXp(int xpValue);

}
