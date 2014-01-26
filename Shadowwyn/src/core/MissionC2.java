package core;
import java.util.*;

public abstract class MissionC2 extends Mission 
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
		loadMap("maps/c2/background.jpg", "maps/c2/mask.pnm", "maps/c2/resources.pnm");
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
		wmap.addObject(6, 33, h1);
		wmap.addObject(32, 6, h2);		
		
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
		Castle c3 = new Castle(CastleType.HUMAN_CASTLE, Names.castle());
		Castle c4 = new Castle(CastleType.DEMONICAL_NECROPOLY , Names.castle());
		Castle c5 = new Castle(CastleType.DEMONICAL_NECROPOLY, Names.castle());
		Castle c6 = new Castle(CastleType.DEMONICAL_NECROPOLY, Names.castle());
		p1.addCastle(c2);
		p2.addCastle(c5);   ///// CEL MISJI: ODBICIE TEGO ZAMKU JAKO JEDEN Z WARUNKÓW ZWYCIĘSTWA
		
		wmap.addObject(5, 12, c1);
		wmap.addObject(6, 28, c2);
		wmap.addObject(24, 28, c3);
		wmap.addObject(10, 3, c4);
		wmap.addObject(32, 4, c5);
		wmap.addObject(30,18, c6);
		System.out.println("END ADD OBJECTS");
		
		order();
		
		wmap.addObject(7, 3, IndependentUnits.create(UnitType.WAPIERZ, 7));
		wmap.addObject(3, 2, IndependentUnits.create(UnitType.SZKIELET, 12));
		wmap.addObject(10,4, IndependentUnits.create(UnitType.ARCY_DIABEL, 1));
		wmap.addObject(2, 14, IndependentUnits.create(UnitType.WAPIERZ, 7));
		wmap.addObject(13, 3, IndependentUnits.create(UnitType.MAG_OGNIA, 12));
		wmap.addObject(25, 2, IndependentUnits.create(UnitType.KUSZNIK, 11));
		wmap.addObject(28, 4, IndependentUnits.create(UnitType.SZKIELET, 7));
		wmap.addObject(30,19, IndependentUnits.create(UnitType.FENIKS, 1));
		wmap.addObject(34,2, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 8));
		wmap.addObject(32, 17, IndependentUnits.create(UnitType.MAG_OGNIA, 11));
		wmap.addObject(33, 20, IndependentUnits.create(UnitType.KUSZNIK, 6));
		wmap.addObject(16, 18, IndependentUnits.create(UnitType.ENT, 10));
		wmap.addObject(3, 29, IndependentUnits.create(UnitType.WOJAK, 15));
		wmap.addObject(10, 28, IndependentUnits.create(UnitType.SZKIELET, 12));
		wmap.addObject(2, 34, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 8));
		wmap.addObject(21, 29, IndependentUnits.create(UnitType.SZKIELET, 12));
		wmap.addObject(26, 31, IndependentUnits.create(UnitType.WOJAK, 11));
		wmap.addObject(31, 35, IndependentUnits.create(UnitType.KUSZNIK, 11));
		wmap.addObject(34, 33, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 7));
		wmap.addObject(35, 35, IndependentUnits.create(UnitType.ENT, 7));
		wmap.addObject(35, 28, IndependentUnits.create(UnitType.WAPIERZ, 7));
		wmap.addObject(24,29, IndependentUnits.create(UnitType.ARCY_DIABEL, 1));
		wmap.addObject(33, 29, IndependentUnits.create(UnitType.MAG_OGNIA, 12));
		wmap.addObject(32, 30, IndependentUnits.create(UnitType.WOJAK, 20));
		wmap.addObject(35, 35, IndependentUnits.create(UnitType.ENT, 9));
		
		System.out.println("START POPULATE");
		wmap.populateMapWidget();
	}


	
	@Override
	public boolean endCondition()
	{
		Color last = lastStanding();
		return last != null || getDay() > 30;
	}
	
	@Override
	public boolean won()
	{
		return playerColor() == lastStanding() || getDay() > 30;
	}
	
	

}
