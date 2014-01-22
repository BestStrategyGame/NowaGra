package core;

public class GroupOfUnits implements java.lang.Comparable<GroupOfUnits>
{
	public final UnitType type;
	private int totalHP;
	private int number;
	private Hero owner;
	private int turn = 0;
	private byte wait;
	
	public GroupOfUnits(UnitType t, Hero o, int n)
	{
		type = t;
		owner = o;
		number = n;
	}
	
	public String getQueue()
	{
		return "turn: "+turn+", wait: "+wait;
	}
	
	public void setOwner(Hero o)
	{
		owner = o;
	}
	
	public void setWait(byte w)
	{
		wait = w;
	}
	
	public int getHP()
	{
		int hp = totalHP%getDefense();
		return hp == 0 ? getDefense() : hp;
	}
	
	public Hero getOwner()
	{
		return owner;
	}
	
	/**
		Set number of units in group
		@param n number of units
	*/
	public void setNumber(int n)
	{
		number = n;
		totalHP = getDefense()*number;
	}
	
	/**
		Get number of units in group
		@return number of units in group
	*/
	public int getNumber()
	{
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
		turn = 0;
		wait = 0;
	}
	
	/**
		Hit other group of units
		@param target group to hit
		@return is target dead?
	*/
	public boolean hit(GroupOfUnits target)
	{
		target.totalHP -= number*getAttack()*type.damageRatio(target.type);
		target.number = java.lang.Math.max(0, (int)java.lang.Math.ceil((float)target.totalHP/target.getDefense()));
		return target.totalHP <= 0;
	}


	@Override
	public int compareTo(GroupOfUnits o)
	{
		System.out.println("compare "+this.type.name+", "+o.type.name);
		int result = turn - o.turn;
		if (result != 0) {
			System.out.println(result);
			return result;
		}
		result = wait - o.wait;
		if (result != 0) {
			System.out.println("wait"+result);
			return result;
		}
		result = o.getSpeed() - getSpeed();
		System.out.println(result);
		return result;
	}
	
	public void nextTurn()
	{
		if (wait == 0) {
			++turn;
		}
	}
}
