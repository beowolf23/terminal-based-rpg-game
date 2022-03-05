package com.company;

public class Ice extends Spell
{
	public Ice()
	{
		super.damage = 50;
		super.manaCost = 60;
	}

	@Override
	public boolean visit(Entity entity)
	{
		if (!entity.iceProtection)
		{
			entity.useAbility(entity, new Ice());
			return true;
		}
		else
		{
			System.out.println("The enemy has ice protection! You dealt 0 damage.");
			return false;
		}
	}

}
