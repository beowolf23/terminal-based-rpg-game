package com.company;

import java.util.ArrayList;

public abstract class Entity implements Element<Entity>
{
	public final int maxLife = 100;
	public final int maxMana = 100;
	public int currentLife;
	public int currentMana;
	public boolean fireProtection;
	public boolean iceProtection;
	public boolean earthProtection;
	ArrayList<Spell> spellList;

	public void lifeRegen(int value)
	{
		currentLife += value;
		if (currentLife > maxLife)
			currentLife = maxLife;
	}

	public void manaRegen(int value)
	{
		currentMana += value;
		if (currentMana > maxMana)
			currentMana = maxMana;
	}

	// method for using an ability on an enemy
	public void useAbility(Entity entity, Spell spell)
	{
		entity.currentLife -= spell.damage;
		System.out.println("You dealt " + spell.damage + " damage!");
	}

	// method for checking if the character has mana for a spell
	public boolean hasManaForSpell(Spell spell)
	{
		return this.currentMana > spell.manaCost;
	}

	public abstract int receiveDamage(int damage);

	public abstract int dealDamage();

}
