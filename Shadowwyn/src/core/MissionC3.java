package core;
import java.util.*;

public abstract class MissionC3 extends Mission 
{
	
	
	
	protected Player p1;
	protected Player p2;
	protected Hero h1;
	protected Hero h2;
	
	public abstract void variant(String name);
	public abstract void order();

	/// CEL MISJI: Pokonaj wszystkich wrogów, odbij zamek wroga, nie daj zabic glownego bohatera
	public void init(String ... names)
	{
		super.init(names);
		//loadMap("maps/map2bg.png", "maps/map2bg.pnm", "maps/map2fg.pnm");
		System.out.println("START LOAD MAP");
		loadMap("maps/c3/background.jpg", "maps/c3/mask.pnm", "maps/c3/resources.pnm");
		System.out.println("END LOAD MAP");

		variant(names[0]);
		
		p1.addResource(ResourceType.GOLD, 5000);
		p1.addResource(ResourceType.WOOD, 10);
		p1.addResource(ResourceType.ORE, 10);
		
		p2.addResource(ResourceType.GOLD, 5000);
		p2.addResource(ResourceType.WOOD, 10);
		p2.addResource(ResourceType.ORE, 10);
		
		p1.addHero(h1);
		p2.addHero(h2);
		wmap.addObject(2, 5, h1);
		wmap.addObject(31, 34, h2);		
		
		List<GroupOfUnits> u1 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u2 = new ArrayList<GroupOfUnits>();
		u1.add(new GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new GroupOfUnits(core.UnitType.KUSZNIK, null, 3));
		u2.add(new GroupOfUnits(core.UnitType.SZKIELET, null, 10 ));
		u2.add(new GroupOfUnits(core.UnitType.POLNOCNICA, null, 3));
		
		h1.setUnits(u1);
		h2.setUnits(u2);
		
		Castle c1 = new Castle(CastleType.HUMAN_CASTLE, Names.castle());
		Castle c2 = new Castle(CastleType.HUMAN_CASTLE, Names.castle());
		Castle c3 = new Castle(CastleType.DEMONICAL_NECROPOLY, Names.castle());
		Castle c4 = new Castle(CastleType.DEMONICAL_NECROPOLY, Names.castle());
	
		p1.addCastle(c1);
		p2.addCastle(c3);   ///// CEL MISJI: ODBICIE TEGO ZAMKU JAKO JEDEN Z WARUNKÓW ZWYCIĘSTWA
		
		wmap.addObject(3, 1, c1);
		wmap.addObject(16, 4, c2);
		wmap.addObject(23, 27, c3);
		wmap.addObject(34, 33, c4);
		System.out.println("END ADD OBJECTS");
		
		order();
		
		wmap.addObject(23, 28, IndependentUnits.create(UnitType.ARCY_DIABEL, 12));
		wmap.addObject(16,5, IndependentUnits.create(UnitType.ARCY_DIABEL, 3));
		wmap.addObject(8,32, IndependentUnits.create(UnitType.FENIKS, 5));
		wmap.addObject(22,19, IndependentUnits.create(UnitType.FENIKS, 5));
		wmap.addObject(33,6, IndependentUnits.create(UnitType.FENIKS, 5));
		wmap.addObject(4,26, IndependentUnits.create(UnitType.FENIKS, 5));
		wmap.addObject(28,2, IndependentUnits.create(UnitType.FENIKS, 5));
		wmap.addObject(1, 3, IndependentUnits.create(UnitType.WOJAK, 15));
		wmap.addObject(6, 2, IndependentUnits.create(UnitType.SZKIELET, 12));
		wmap.addObject(4, 8, IndependentUnits.create(UnitType.KUSZNIK, 11));
		wmap.addObject(13, 6, IndependentUnits.create(UnitType.ENT, 10));
		wmap.addObject(14,1, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 14));
		wmap.addObject(21,5, IndependentUnits.create(UnitType.WOJAK, 8));
		wmap.addObject(17, 33, IndependentUnits.create(UnitType.MAG_OGNIA, 11));
		wmap.addObject(20, 29, IndependentUnits.create(UnitType.KUSZNIK, 6));
		wmap.addObject(24, 31, IndependentUnits.create(UnitType.ENT, 10));
		wmap.addObject(34, 31, IndependentUnits.create(UnitType.WOJAK, 15));
		wmap.addObject(34, 35, IndependentUnits.create(UnitType.SZKIELET, 12));
		
		
		System.out.println("START POPULATE");
		wmap.populateMapWidget();
	}


	
	@Override
	public boolean endCondition()
	{
		Color last = lastStanding();
		return last != null;
	}
	
	@Override
	public boolean won()
	{
		return playerColor() == lastStanding();
	}
	
	

}
