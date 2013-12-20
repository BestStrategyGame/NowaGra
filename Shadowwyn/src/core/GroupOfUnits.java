package core;
public class GroupOfUnits
{
	public final UnitType type;
	private int totalHP;
	private int number;
	private Hero owner;
	
	public GroupOfUnits(UnitType t, Hero o, int n)
	{
		type = t;
		owner = o;
		number = n;
	}
	
	public void setOwner(Hero o)
	{
		owner = o;
	}
	
	/**
		Set number of units in group
		@param n number of units
	*/
	public void setNumber(int n)
	{
		number = n;
	}
	
	/**
		Get number of units in group
		@return number of units in group
	*/
	public int getNumber()
	{
		//return java.lang.Math.max(0, totalHP/type.defense);
		return number;
	}
	
	public String getNumberDesc()
	{
		int n = getNumber();
		if (n < 5) return "mało";
		if (n < 10) return "kilka";
		if (n < 20) return "watacha";
		if (n < 50) return "dużo";
		if (n < 100) return "horda";
		if (n < 250) return "masa";
		if (n < 500) return "mrowie";
		if (n < 1000) return "rzesza";
		if (n < 50000) return "legion";
		return "wow such power much numbers"; // ;)
	}
	
	public int getAttack()
	{
		return type.attack +
			owner.getAttack()*type.level/4;
	}
	
	public int getDefense()
	{
		return type.defense +
			owner.getDefense()*type.level/4;
	}
	
	public int getSpeed()
	{
		return type.speed +
			owner.getSpeed()/2;
	}
	
	public void prepareToBattle()
	{
		totalHP = getDefense()*number;
	}
	
	/**
		Hit other group of units
		@param target group to hit
		@return is target dead?
	*/
	public boolean hit(GroupOfUnits target)
	{
		target.totalHP -= number*getAttack();
		return target.totalHP <= 0;
	}
}
