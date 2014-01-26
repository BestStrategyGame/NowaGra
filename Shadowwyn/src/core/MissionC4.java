package core;
import java.util.*;

public abstract class MissionC4 extends Mission 
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
		loadMap("maps/c4/background.jpg", "maps/c4/mask.pnm", "maps/c4/resources.pnm");
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
		wmap.addObject(7, 33, h1);
		wmap.addObject(30, 5, h2);		
		
		List<GroupOfUnits> u1 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u2 = new ArrayList<GroupOfUnits>();
		u1.add(new GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new GroupOfUnits(core.UnitType.KUSZNIK, null, 5));
		u2.add(new GroupOfUnits(core.UnitType.SZKIELET, null, 10 ));
		u2.add(new GroupOfUnits(core.UnitType.POLNOCNICA, null, 5));
		
		h1.setUnits(u1);
		h2.setUnits(u2);
		
		Castle c1 = new Castle(CastleType.HUMAN_CASTLE, Names.castle());
		Castle c2 = new Castle(CastleType.HUMAN_CASTLE, Names.castle());
		Castle c3 = new Castle(CastleType.DEMONICAL_NECROPOLY , Names.castle());
		Castle c4 = new Castle(CastleType.DEMONICAL_NECROPOLY, Names.castle());
	
		p1.addCastle(c1);
		p2.addCastle(c3);   ///// CEL MISJI: ODBICIE TEGO ZAMKU JAKO JEDEN Z WARUNKÓW ZWYCIĘSTWA
		
		wmap.addObject(4, 32, c1);
		wmap.addObject(2, 15, c2);
		wmap.addObject(28, 4, c3);
		wmap.addObject(9, 2, c4);
		System.out.println("END ADD OBJECTS");
		
		order();
		
		wmap.addObject(9, 3, IndependentUnits.create(UnitType.ARCY_DIABEL, 2));
		wmap.addObject(2,16, IndependentUnits.create(UnitType.ARCY_DIABEL, 2));
		wmap.addObject(1,12, IndependentUnits.create(UnitType.SZKIELET, 15));
		wmap.addObject(6,31, IndependentUnits.create(UnitType.KUSZNIK, 7));
		wmap.addObject(8,16, IndependentUnits.create(UnitType.ENT, 5));
		wmap.addObject(13,23, IndependentUnits.create(UnitType.MAG_OGNIA, 14));
		wmap.addObject(13, 25, IndependentUnits.create(UnitType.WAPIERZ, 15));
		wmap.addObject(18, 11, IndependentUnits.create(UnitType.POLNOCNICA, 20 ));
		wmap.addObject(3, 3, IndependentUnits.create(UnitType.WOJAK, 17));
		wmap.addObject(13, 3, IndependentUnits.create(UnitType.SZKIELET, 18));
		wmap.addObject(20,1, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 14));
		wmap.addObject(32,18, IndependentUnits.create(UnitType.MAG_OGNIA, 15));
		wmap.addObject(25, 3, IndependentUnits.create(UnitType.KUSZNIK, 10));
		wmap.addObject(31, 4, IndependentUnits.create(UnitType.NIMFA, 20));
		wmap.addObject(32, 34, IndependentUnits.create(UnitType.RYCERZ_MROKU, 1));
		wmap.addObject(35, 34, IndependentUnits.create(UnitType.MAG_OGNIA, 15));
		wmap.addObject(33, 35, IndependentUnits.create(UnitType.WAPIERZ, 17));
		wmap.addObject(8, 13, IndependentUnits.create(UnitType.RYCERZ_MROKU, 5));
		wmap.addObject(18, 28, IndependentUnits.create(UnitType.RYCERZ_MROKU, 5));
		wmap.addObject(32, 29, IndependentUnits.create(UnitType.ARCY_DIABEL, 3));
		wmap.addObject(35, 34, IndependentUnits.create(UnitType.SZKIELET, 800));
		wmap.addObject(35, 31, IndependentUnits.create(UnitType.KUSZNIK, 100));
		wmap.addObject(1, 34, IndependentUnits.create(UnitType.SZKIELET, 20));
		wmap.addObject(13, 35, IndependentUnits.create(UnitType.NIMFA, 80));
		
		
		
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
