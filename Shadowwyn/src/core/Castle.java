package core;
import gui.WindowBattle;
import gui.WindowCastle;

import java.util.*;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMessageBox;

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
	private boolean endangered = false;
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
		//lock = true;
	}

	public void setHero(Hero hero)
	{
		this.hero = hero;
	}

	public boolean getEndangered()
	{
		return endangered;
	}
	
	public void setEndangered(boolean e)
	{
		endangered = true;
	}
	
	public boolean isBuyed()
	{
		return buyed;
	}
	
	public CastleType getType()
	{
		return type;
	}
	
	private Cost weeklyRequirements;
	public Cost getWeeklyRequirements()
	{
		return weeklyRequirements;
	}
	
	public void  generateWeeklyRequirements(Player player)
	{
		int gold, wood, ore;
		Castle dummy = new Castle(type, "");
		for (CastleBuilding b: buildings) {
			System.out.println("WEEKLY BONUS: "+dummy.getName());
			b.weeklyBonus(player, dummy);
		}
		gold = dummy.getAvailableToRecruit(1)*type.tier1.cost.gold
			 + dummy.getAvailableToRecruit(2)*type.tier2.cost.gold
			 + dummy.getAvailableToRecruit(3)*type.tier3.cost.gold
			 + dummy.getAvailableToRecruit(4)*type.tier4.cost.gold;
		
		wood = dummy.getAvailableToRecruit(1)*type.tier1.cost.wood
			 + dummy.getAvailableToRecruit(2)*type.tier2.cost.wood
			 + dummy.getAvailableToRecruit(3)*type.tier3.cost.wood
			 + dummy.getAvailableToRecruit(4)*type.tier4.cost.wood;
		
		ore = dummy.getAvailableToRecruit(1)*type.tier1.cost.ore
			 + dummy.getAvailableToRecruit(2)*type.tier2.cost.ore
			 + dummy.getAvailableToRecruit(3)*type.tier3.cost.ore
			 + dummy.getAvailableToRecruit(4)*type.tier4.cost.ore;
		weeklyRequirements = new Cost(gold, wood, ore);
	}
	
	public int getAvailableToRecruit(int tier)
	{
		return availableToRecruit[tier-1];
	}
	
	public void addAvailableToRecruit(int tier, int no)
	{
		availableToRecruit[tier-1] += no;
		System.out.println("ADD AVAILABLE: "+getName() + availableToRecruit[tier-1]);
		if (!getName().equals("")) {
			Thread.dumpStack();
		}
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
			if (player instanceof PlayerHuman) {
				gui.WindowStack ws = gui.WindowStack.getLastInstance();
				if (ws != null) {
					gui.WindowCastle wc = new gui.WindowCastle(player, this, hero);
					ws.push(wc);
				}
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
				Mission m = Mission.getLastInstance();
				Player cp = m.getPlayer(getColor());
				
				Terrain t = WorldMap.getLastInstance().getTerrainAt(hero.getX(), hero.getY());
				Battle b = new Battle(player, cp, hero, getGarission(), Terrain.GRASS, this);
				b.createMapWidget();
				b.populateMapWidget();
				getGarission().setInCastle(this);
				WindowBattle bat = new WindowBattle(b, player, cp, hero, getGarission());
				b.updateQueue.connect(bat, "updateQueue(Queue)");
				b.log.connect(bat, "addLogLine(String)");
				b.start();
				
				if (player instanceof PlayerHuman || cp instanceof PlayerHuman) {
					gui.WindowStack ws = gui.WindowStack.getLastInstance();
					ws.push(bat);
				}
	
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
		garisson.setColor(c);
		if (WorldMap.getLastInstance() != null) {
			WorldMap.getLastInstance().colorChanged(this, c);
		}
		color = c;
	}
	
	public void addBuilding(CastleBuilding b)
	{
		if (b.type == null || b.type == type) {
			if (!buildings.contains(b)) {
				buildings.add(b);
				b.buyBonus(Mission.getLastInstance().getActivePlayer(), this);
				System.out.println("ADD BUILDING: "+b.name);
			}
		}
	}
	
	public void buyBuilding(CastleBuilding b)
	{
		if (buyed == false) {
			addBuilding(b);
			buyed = true;
		}
	}
	
	public int inCityDefenseBonus(Hero hero)
	{
		if (buildings.contains(CastleBuilding.CASTLE)) {
			return 12;
		} else if (buildings.contains(CastleBuilding.CITADEL)) {
			return 8;
		} else if (buildings.contains(CastleBuilding.FORT)) {
			return 4;
		}
		return 0;
	}
	
	public void dailyBonus(Player player)
	{
		endangered = false;
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
	
	private int recruitPay(Player player, UnitType unit)
	{
		float gold = player.getResource(core.ResourceType.GOLD)/(float)unit.cost.gold;
		float wood = player.getResource(core.ResourceType.WOOD)/(float)unit.cost.wood;
		float ore = player.getResource(core.ResourceType.ORE)/(float)unit.cost.ore;
		float min = getAvailableToRecruit(unit.level);
		if (gold < min) min = gold;
		if (wood < min) min = wood;
		if (ore < min) min = ore;
		int max = (int)min;
		
		player.addResource(core.ResourceType.GOLD, -max*unit.cost.gold);
		player.addResource(core.ResourceType.WOOD, -max*unit.cost.wood);
		player.addResource(core.ResourceType.ORE, -max*unit.cost.ore);
		
		addAvailableToRecruit(unit.level, -max);
		return max;
	}
	
	public boolean recruitAllFromTier(Player player, int tier)
	{
		int amount = -1;
		boolean found = false;
		UnitType unit = UnitType.getTier(tier, type);
		for (core.GroupOfUnits u: garisson.getUnits()) {
			System.out.println(u.type);
			if (u.type == unit) {
				amount = recruitPay(player, unit);
				u.setNumber(u.getNumber()+amount);
				found = true;
				break;
			}
		}
		if (found == false) {
			amount = recruitPay(player, unit);
			if (amount > 0) {
				garisson.getUnits().add(new GroupOfUnits(unit, garisson, amount));
				found = true;
			}
		}
		System.out.println("!!    AI: RECRUIT ALL FROM TIER "+unit+" "+getAvailableToRecruit(tier)+" "+tier+" "+amount+" "+found);
		return found;
	}
	
	public void moveUnitsToHero()
	{
		if (!hero.isInCastle(this)) return;
		List<GroupOfUnits> toRemove = new ArrayList<GroupOfUnits>();
		for (core.GroupOfUnits ug: garisson.getUnits()) {
			if (hero.getUnits().size() >= 6) return;
			boolean found = false;
			for (core.GroupOfUnits uh: hero.getUnits()) {
				if (ug.type == uh.type) {
					uh.setNumber(uh.getNumber()+ug.getNumber());
					toRemove.add(ug);
					//garisson.getUnits().remove(ug);
					found = true;
					break;
				}
			}
			if (found == false) {
				hero.getUnits().add(ug);
				toRemove.add(ug);
				//garisson.getUnits().remove(ug);
			}
			
		}
		garisson.getUnits().removeAll(toRemove);
	}
	
	public boolean recruitAll(Player player)
	{
		System.out.println("!!    AI: RECRUIT ALL");
		return	recruitAllFromTier(player, 1) ||
				recruitAllFromTier(player, 2) ||
				recruitAllFromTier(player, 3) ||
				recruitAllFromTier(player, 4);
	}

	@Override
	public int willingnessToMoveHere(Hero hero, Player player, float distance,
			int day) {
		float moveRatio = hero.getMovePoints()/(distance+1);
		
		if (color == player.getColor()) {
			if (getEndangered()) {
				System.out.println(" 1 "+hero.getName());
				return (int)(500*moveRatio);
			} else {
				int strength = 0;
				if (getGarission() != null)
					strength += getGarission().getStrenght();
				if (strength > 1.5f*hero.getStrenght()) {
					System.out.println(hero.isInCastle());
					System.out.println(getHero());
					System.out.println(" 2 "+hero.getName());
					return (int)(500*moveRatio);
				}
			}
		} else if (Mission.getLastInstance().isAlly(hero.getColor(), getColor())) {
				return -1;
		} else {
			float strength = 1;
			if (getHero() != null)
				strength += getHero().getStrenght();
			if (getGarission() != null)
				strength += getGarission().getStrenght();
			System.out.println(" 3 "+hero.getName());
			return (int)(Math.min(200f*hero.getStrenght()/strength, 300f) + 50*moveRatio);
		}
		
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

	private boolean buyBuildingSpecific(Player player , CastleBuilding b)
	{
		if (player.getResource(ResourceType.GOLD) > b.cost.gold &&
			player.getResource(ResourceType.WOOD) > b.cost.wood &&
			player.getResource(ResourceType.ORE) > b.cost.ore &&
			!isBuyed()) {
			
			player.addResource(ResourceType.GOLD, -b.cost.gold);
			player.addResource(ResourceType.WOOD, -b.cost.wood);
			player.addResource(ResourceType.ORE, -b.cost.ore);
			
			buyBuilding(b);
			return true;
		}
		return false;
	}
	
	public boolean buyBuildingOrRequired(Player player, CastleBuilding b)
	{
		if (!buildings.contains(b)) {
			if (buildings.containsAll(Arrays.asList(b.requires))) {
				return buyBuildingSpecific(player, b);
			} else {
				List<CastleBuilding> req = Arrays.asList(b.requires);
				req.removeAll(buildings);
				
				for (CastleBuilding r: req) {
					if (buyBuildingOrRequired(player, r)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean buyBuildingLair(Player player)
	{
		for (CastleBuilding b: CastleBuilding.values()) {
			if (b.type == type) {
				if (!buildings.contains(b) && buildings.containsAll(Arrays.asList(b.requires))) {
					return buyBuildingSpecific(player, b);
				}
			}
		}
		return false;
	}
}
