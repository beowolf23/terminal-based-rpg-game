package com.company;

public class Earth extends Spell
{
	public Earth()
	{
		super.damage = 30;
		super.manaCost = 30;
	}

	@Override
	public boolean visit(Entity entity)
	{
		if (!entity.earthProtection)
		{
			entity.useAbility(entity, new Earth());
			return true;
		}
		else
		{
			System.out.println("The enemy has earth protection! You did 0 damage to him.");
			return false;
		}
	}

}
