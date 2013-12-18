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
