package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Shop implements CellElement
{
	ArrayList<Potion> potionList;

	public Shop()
	{
		potionList = new ArrayList<>();
		Random randomObj = new Random();
		int numberOfPotions = randomObj.nextInt(2) + 2;
		for (int i = 0; i < numberOfPotions; i++)
		{
			boolean potionType = randomObj.nextBoolean();
			if (potionType)
				potionList.add(new HealthPotion());
			potionList.add(new ManaPotion());
		}

	}

	@Override
	public char toCharacter()
	{
		return 'S';
	}

	public Potion getPotion(int index)
	{
		Potion potionToGet = potionList.get(index);
		potionList.remove(index);
		return potionToGet;
	}

}
