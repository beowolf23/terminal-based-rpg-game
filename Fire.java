package com.company;

public class Fire extends Spell
{
	public Fire()
	{
		super.damage = 80;
		super.manaCost = 80;
	}

	@Override
	public boolean visit(Entity entity)
	{
		if(!entity.fireProtection)
		{
			entity.useAbility(entity, new Fire());
			return true;
		}
		else
		{
			System.out.println("The enemy has fire protection! You dealt 0 damage.");
			return false;
		}
	}
}
