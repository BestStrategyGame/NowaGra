package core;

import gui.WindowBattle;

import java.util.*;

import com.trolltech.qt.gui.QApplication;

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
	
	private int x = -1;
	private int y = -1;
	
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
		if (hero.getColor() == getColor()) {
			return false;
		}
		
		Mission m = Mission.getLastInstance();
		Player cp = m.getPlayer(getColor());
		
		if (getUnits().size() == 0) {
			m.battleFinished(hero.getColor(), player, cp, hero, this, 0, 0);

		} else if (hero.getUnits().size() == 0){
			m.battleFinished(getColor(), player, cp, hero, this, 0, 0);
		} else {
			Battle b = new Battle(player, cp, hero, this, Terrain.GRASS, null);
			b.createMapWidget();
			b.populateMapWidget();;
			
			WindowBattle bat = new WindowBattle(b, player, cp, hero, this);
			b.updateQueue.connect(bat, "updateQueue(Queue)");
			b.log.connect(bat, "addLogLine(String)");
			b.start();
			
			gui.WindowStack ws = gui.WindowStack.getLastInstance();
			if (ws != null) {
				ws.push(bat);
			}
			QApplication.processEvents();
		}
		return false;
	}
	
	public int stackLevel()
	{
		return 2;
	}
	
	public void dailyBonus(Player player)
	{
		if (!player.getName().equals("dummy")) {
			movePoints = getMaxMovePoints();
		}
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
	
	public int getMaxMovePoints()
	{
		return basicMovePoints + getSpeed();
	}
	
	public int getMovePoints()
	{
		return movePoints;
	}
	
	public void takeMovePoints(int n)
	{
		movePoints -= n;
	}
	
	public void takeMovePoints(float n)
	{
		takeMovePoints((int)Math.ceil(n));
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
		if (u == null) {
			units = new ArrayList<GroupOfUnits>();
		} else {
			units = u;
		}
		for (GroupOfUnits unit: units) {
			unit.setOwner(this);
		}
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
	
	public int incExperience(int n)
	{
		int oldLevel = getLevel();
		experience += n;
		return getLevel()-oldLevel; 
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
	public int willingnessToMoveHere(Hero hero, Player player, float distance,
			int day) {
		float moveRatio = hero.getMovePoints()/(distance+1);
		
		if (hero == this) {
			return -100;
		}
		
		if (hero.getColor() == getColor()) {
			return -100;
			/*float stat1 = getLevel()+2;
			float stat2 = hero.getLevel()+2;
			if (stat1 > 2*stat2) {
				return (int)(100*(Math.min(2, stat1/stat2) + Math.min(2, hero.getStrenght()/getStrenght()) + moveRatio));
			} else if (stat2 > 2*stat1) {
				return (int)(100*(Math.min(2, stat2/stat1) + Math.min(2, getStrenght()/hero.getStrenght()) + moveRatio));
			} else {
				return (int)(100*moveRatio);
			}*/
		} else {
			System.out.println("AI: STRENGTH "+hero.getStrenght()+" vs "+getStrenght());
			if (hero.getStrenght() > 1.2f*getStrenght()) {
				return (int)(100+200*moveRatio);
			}
		}
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