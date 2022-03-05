package com.company;

public class CharacterFactory
{
	public static Character getNewCharacter(String profession, String characterName, int currentXp, int currentLevel)
	{
		if (profession.equals("Warrior"))
			return new Warrior(characterName, currentXp, currentLevel);
		if (profession.equals("Rogue"))
			return new Rogue(characterName, currentXp, currentLevel);
		if (profession.equals("Mage"))
			return new Mage(characterName, currentXp, currentLevel);
		return null;
	}

}
