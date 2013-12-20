package core;

import java.util.*;

public class Hero implements WorldMapObject
{
	private String name;
	private Color color = null;
	private Castle castle;
	private CastleType type;
	
	private int attack = 0;
	private int defense = 0;
	private int speed = 0;
	private int experience = 0;
	
	private int cityAttack = 0;
	private int cityDefense = 0;
	private int citySpeed = 0;
	
	private static final int basicMovePoints = 32;
	private int movePoints = basicMovePoints;
	
	private int x;
	private int y;
	
	private Set<Artifact> artifacts = new HashSet<Artifact>();
	private Map<ArtifactType, Artifact> active = new HashMap<ArtifactType, Artifact>();
	private List<GroupOfUnits> units = new ArrayList<GroupOfUnits>();
	
	public Hero(String n, CastleType t)
	{
		active.put(ArtifactType.SWORD, Artifact.DUMMY);
		active.put(ArtifactType.HELMET, Artifact.DUMMY);
		active.put(ArtifactType.ARMOR, Artifact.DUMMY);
		active.put(ArtifactType.SHIELD, Artifact.DUMMY);
		name = n;
		type = t;
		//color = c;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean stand(Hero hero, Player player)
	{
		// FIGHT FIGHT FIGHT
		return true;
	}
	
	public boolean standNextTo(Hero hero, Player player)
	{
		return false;
	}
	
	public int stackLevel()
	{
		return 2;
	}
	
	public void dailyBonus(Player player)
	{
		movePoints = basicMovePoints + getSpeed();
		if (color == player.getColor()) {
			for (Artifact a: active.values()) {
				a.dailyBonus(player);
			}
		}
	}
	
	public void weeklyBonus(Player player)
	{
		if (color == player.getColor()) {
			for (Artifact a: active.values()) {
				a.weeklyBonus(player);
			}
		}
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public String getTooltip()
	{
		String tooltip = name;
		for (GroupOfUnits u: units) {
			tooltip += "\n"+ u.type.name +" ("+u.getNumberDesc()+")";
		}
		return tooltip;
	}
	
	public int getMovePoints()
	{
		return movePoints;
	}
	
	public void takeMovePoints(int n)
	{
		movePoints -= n;
	}
	
	public void setPosition(int x, int y)
	{
		cityAttack = 0;
		cityDefense = 0;
		citySpeed = 0;
		castle = null;
		
		System.out.println("Set out castle "+x+", "+y);
	
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setInCastle(Castle c)
	{
		System.out.println("Set in castle");
		castle = c;
		cityAttack = 1;
		cityDefense = 1;
		citySpeed = 1;
	}
	
	public boolean isInCastle(Castle c)
	{
		return castle == c;
	}
	
	public boolean isInCastle()
	{
		return castle != null;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public void removeUnits(int pos)
	{
		units.set(pos, null);
	}
	
	public List<GroupOfUnits> getUnits()
	{
		return units;
	}
	
	public void setUnits(List<GroupOfUnits> u)
	{
		units = u;
	}
	
	public void moveUnits(Hero dst, int dstPos, int srcPos)
	{
		dst.units.set(dstPos, units.get(srcPos));
		dst.units.get(dstPos).setOwner(dst);
		removeUnits(srcPos);
	}
	
	public void moveArtifact(Hero dst, Artifact art)
	{
		if (artifacts.remove(art)) {
			if (active.containsValue(art)) {
				for (ArtifactType at: active.keySet()) {
					if (active.get(at) == art) {
						active.put(at, Artifact.DUMMY);
					}
				}
			}
			dst.artifacts.add(art);
		}
	}
	
	public void setActive(ArtifactType at, Artifact art)
	{
		if (artifacts.contains(art)) {
			active.put(at, art);
		}
	}
	
	public void incAttack(int n)
	{
		attack += n;
	}
	
	public void incDefense(int n)
	{
		defense += n;
	}
	
	public void incSpeed(int n)
	{
		speed += n;
	}
	
	public void incExperience(int n)
	{
		experience += n;
	}
	
	public int getExp()
	{
		return experience;
	}
	
	public int getLevel()
	{
		return experience/1000 + 1;
	}
	
	public int getBaseAttack()
	{
		return attack;
	}
	
	public int getAttack()
	{
		int result = attack + cityAttack;
		for (Artifact a: active.values()) {
			result += a.attack;
		}
		return result;
	}
	
	public int getBaseDefense()
	{
		return defense;
	}
	
	public int getDefense()
	{
		int result = defense + cityDefense;
		for (Artifact a: active.values()) {
			result += a.defense;
		}
		return result;
	}
	
	public int getBaseSpeed()
	{
		return speed;
	}
	
	public int getSpeed()
	{
		int result = speed + citySpeed;
		for (Artifact a: active.values()) {
			result += a.speed;
		}
		return result;
	}
	
	public int getStrenght()
	{
		int strenght = 0;
		for (GroupOfUnits u: units) {
			strenght += u.getNumber()*(
				4*u.getAttack() +
				2*u.getDefense() +
				u.getSpeed()
			);
		}
		return strenght;
	}

	@Override
	public int willingnessToMoveHere(Hero hero, Player player, int distance,
			int day) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getImageFile() {
		return type.file;
	}

	@Override
	public boolean isVisitable() {
		return false;
	}

	@Override
	public boolean isCollectable() {
		return false;
	}
}