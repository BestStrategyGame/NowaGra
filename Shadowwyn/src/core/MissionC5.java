package core;
import java.util.*;

public abstract class MissionC5 extends Mission 
{
	
	
	
	protected Player p1;
	protected Player p2;
	protected Player p3;
	protected Hero h1;
	protected Hero h2;
	protected Hero h3;
	
	public abstract void variant(String name);
	public abstract void order();

	/// CEL MISJI: Pokonaj wszystkich wrogĂłw, odbij zamek wroga, nie daj zabic glownego bohatera
	public void init(String ... names)
	{
		super.init(names);
		//loadMap("maps/map2bg.png", "maps/map2bg.pnm", "maps/map2fg.pnm");
		System.out.println("START LOAD MAP");
		loadMap("maps/c5/background.jpg", "maps/c5/mask.pnm", "maps/c5/resources.pnm");
		System.out.println("END LOAD MAP");

		variant(names[0]);
		
		p1.addResource(ResourceType.GOLD, 5000);
		p1.addResource(ResourceType.WOOD, 10);
		p1.addResource(ResourceType.ORE, 10);
		
		p2.addResource(ResourceType.GOLD, 5000);
		p2.addResource(ResourceType.WOOD, 10);
		p2.addResource(ResourceType.ORE, 10);
		
		p3.addResource(ResourceType.GOLD, 5000);
		p3.addResource(ResourceType.WOOD, 10);
		p3.addResource(ResourceType.ORE, 10);
		
		
		p1.addHero(h1);
		p2.addHero(h2);
		p3.addHero(h3);
		wmap.addObject(13, 35, h1);
		wmap.addObject(16, 3, h2);
		wmap.addObject(31, 11, h3);
		
		
		List<GroupOfUnits> u1 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u2 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u3 = new ArrayList<GroupOfUnits>();
		u1.add(new GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new GroupOfUnits(core.UnitType.KUSZNIK, null, 5));
		u2.add(new GroupOfUnits(core.UnitType.NIMFA, null, 10 ));
		u2.add(new GroupOfUnits(core.UnitType.ELFI_SKRYTOBOJCA, null, 5));
		u3.add(new GroupOfUnits(core.UnitType.SZKIELET, null, 10 ));
		u3.add(new GroupOfUnits(core.UnitType.POLNOCNICA, null, 5));
		
		h1.setUnits(u1);
		h2.setUnits(u2);
		h3.setUnits(u3);
		
		Castle c1 = new Castle(CastleType.HUMAN_CASTLE, Names.castle());
		Castle c2 = new Castle(CastleType.HUMAN_CASTLE, Names.castle());
		Castle c3 = new Castle(CastleType.FOREST_ROOK, Names.castle());
		Castle c4 = new Castle(CastleType.FOREST_ROOK, Names.castle());
		Castle c5 = new Castle(CastleType.DEMONICAL_NECROPOLY , Names.castle());
		Castle c6 = new Castle(CastleType.DEMONICAL_NECROPOLY, Names.castle());
	
		p1.addCastle(c1);
		p2.addCastle(c3);
		p3.addCastle(c5);
		
		wmap.addObject(15, 34, c1);
		wmap.addObject(1, 23, c2);
		wmap.addObject(16, 1, c3);
		wmap.addObject(2, 13, c4);
		wmap.addObject(31, 9, c5);
		wmap.addObject(30, 22, c6);
		System.out.println("END ADD OBJECTS");
		
		order();
		
		wmap.addObject(8, 8, IndependentUnits.create(UnitType.ARCY_DIABEL, 3));
		wmap.addObject(23,3, IndependentUnits.create(UnitType.ARCY_DIABEL, 2));
		wmap.addObject(18,8, IndependentUnits.create(UnitType.FENIKS, 2));
		wmap.addObject(17,24, IndependentUnits.create(UnitType.RYCERZ_MROKU, 2));
		wmap.addObject(25,31, IndependentUnits.create(UnitType.FENIKS, 2));
		wmap.addObject(2,14, IndependentUnits.create(UnitType.FENIKS, 1));
		//wmap.addObject(1,24, IndependentUnits.create(UnitType.ARCY_DIABEL, 1));
		wmap.addObject(30, 23, IndependentUnits.create(UnitType.RYCERZ_MROKU, 1));
		wmap.addObject(2, 6, IndependentUnits.create(UnitType.POLNOCNICA, 30 ));
		wmap.addObject(7, 6, IndependentUnits.create(UnitType.KUSZNIK, 25));
		wmap.addObject(1, 15, IndependentUnits.create(UnitType.SZKIELET, 18));
		wmap.addObject(6,14, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 9));
		wmap.addObject(0,21, IndependentUnits.create(UnitType.WOJAK, 15));
		wmap.addObject(5, 28, IndependentUnits.create(UnitType.KUSZNIK, 10));
		wmap.addObject(1, 31, IndependentUnits.create(UnitType.NIMFA, 35));
		wmap.addObject(3, 35, IndependentUnits.create(UnitType.WAPIERZ, 7));
		wmap.addObject(9, 33, IndependentUnits.create(UnitType.MAG_OGNIA, 4));
		wmap.addObject(21, 33, IndependentUnits.create(UnitType.SZKIELET, 14));
		wmap.addObject(12, 24, IndependentUnits.create(UnitType.ENT, 10));
		wmap.addObject(12, 13, IndependentUnits.create(UnitType.RYCERZ_MROKU, 1));
		wmap.addObject(16, 17, IndependentUnits.create(UnitType.MAG_OGNIA, 20));
		wmap.addObject(17, 18, IndependentUnits.create(UnitType.ARCY_DIABEL, 3));
		wmap.addObject(13, 1, IndependentUnits.create(UnitType.SZKIELET, 20));
		wmap.addObject(21, 2, IndependentUnits.create(UnitType.KUSZNIK, 6));
		wmap.addObject(29, 7, IndependentUnits.create(UnitType.WOJAK, 14));
		wmap.addObject(34, 8, IndependentUnits.create(UnitType.NIMFA, 20));
		wmap.addObject(35, 18, IndependentUnits.create(UnitType.KUSZNIK, 30));
		wmap.addObject(33, 23, IndependentUnits.create(UnitType.WAPIERZ, 10));
		wmap.addObject(33, 27, IndependentUnits.create(UnitType.SZKIELET, 20));
		
		
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
