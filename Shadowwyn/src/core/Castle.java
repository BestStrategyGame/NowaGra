package core;
import gui.WindowCastle;

import java.util.*;

public class Castle implements WorldMapObject
{
	private CastleType type;
	private String name;
	private Color color;
	private Set<CastleBuilding> buildings = new HashSet<CastleBuilding>();
	private boolean buyed = false;
	private int[] availableToRecruit = new int[4]; 
	private Hero garisson = new Hero("Garnizon stacjonujący", null);
	private Hero hero;
	private boolean lock = false;
	
	public Hero getHero()
	{
		if (hero != null && hero.isInCastle(this)) {
			return hero;
		} else {
			return null;
		}
	}
	
	public void lockStanding()
	{
		lock = true;
	}

	public void setHero(Hero hero)
	{
		this.hero = hero;
	}

	public boolean isBuyed()
	{
		return buyed;
	}
	
	public CastleType getType()
	{
		return type;
	}
	
	public int getAvailableToRecruit(int tier)
	{
		return availableToRecruit[tier-1];
	}
	
	public void addAvailableToRecruit(int tier, int no)
	{
		availableToRecruit[tier-1] += no;
	}
	
	public void recruit(int tier, int no)
	{
		availableToRecruit[tier-1] -= no;
	}

	public Set<CastleBuilding> getBuildings()
	{
		return buildings;
	}

	public Castle(CastleType t, String n)
	{
		type = t;
		name = n;
		for (int i=0; i<availableToRecruit.length; ++i) {
			availableToRecruit[i] = 0;
		}
	}
	
	public void setUnits(List<GroupOfUnits> units)
	{
		garisson.setUnits(units);
	}
	
	public Hero getGarission()
	{
		return garisson;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean stand(Hero hero, Player player)
	{
		if (lock) {
			return false;
		}
		if (hero.getColor() == color) {
			hero.setInCastle(this);
			setHero(hero);
			gui.WindowStack ws = gui.WindowStack.getLastInstance();
			if (ws != null) {
				gui.WindowCastle wc = new gui.WindowCastle(player, this, hero);
				ws.push(wc);
			}
		} else {
			if (garisson.getUnits().size() == 0) {
				setColor(player.getColor());
				player.addCastle(this);
				hero.setInCastle(this);
				setHero(hero);
				
				Mission m = Mission.getLastInstance();
				if (m != null) {
					System.out.println("update window "+m.getActivePlayer());
					m.updateWindow.emit(m.getActivePlayer());
				}
				
			} else {
				// TODO fight
				
				System.out.println("fight in castle");
			}
			
		}
		return false;
	}
	
	public boolean standNextTo(Hero hero, Player player)
	{
		return false;
	}
	
	public int stackLevel()
	{
		return 1;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color c)
	{
		if (WorldMap.getLastInstance() != null) {
			WorldMap.getLastInstance().colorChanged(this, c);
		}
		color = c;
	}
	
	public void addBuilding(CastleBuilding b)
	{
		if (b.type == null || b.type == type) {
			buildings.add(b);
		}
	}
	
	public void buyBuilding(CastleBuilding b)
	{
		if (buyed == false) {
			addBuilding(b);
			buyed = true;
		}
	}
	
	
	public void dailyBonus(Player player)
	{
		if (color == player.getColor()) {
			for(CastleBuilding b: buildings) {
				b.dailyBonus(player, this);
			}
		}
		buyed = false;
	}
	
	public void weeklyBonus(Player player)
	{
		if (color == player.getColor()) {
			for(CastleBuilding b: buildings) {
				b.weeklyBonus(player, this);
			}
		}
	}

	@Override
	public int willingnessToMoveHere(Hero hero, Player player, int distance,
			int day) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getImageFile() {
		//return type.file;
		return null;
	}

	@Override
	public boolean isVisitable() {
		return true;
	}

	@Override
	public boolean isCollectable() {
		return false;
	}

	@Override
	public String getTooltip()
	{
		String tooltip = name;
		for (GroupOfUnits u: garisson.getUnits()) {
			tooltip += "\n"+ u.type.name +" ("+u.getNumberDesc()+")";
		}
		return tooltip;
	}
}
