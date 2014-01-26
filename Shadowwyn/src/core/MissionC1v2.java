package core;

import java.util.*;

public class MissionC1v2 extends Mission
{

	protected Player p1;
	protected Player p2;
	protected Hero h1;
	protected Hero h2;
	
	/// CEL MISJI: Pokonaj wszystkich wrogĂłw, odbij zamek wroga, nie daj zabic glownego bohatera
	public void init(String ... names)
	{
		super.init(names);
		//loadMap("maps/map2bg.png", "maps/map2bg.pnm", "maps/map2fg.pnm");
		System.out.println("START LOAD MAP");
		loadMap("maps/c6/background.jpg", "maps/c6/mask.pnm", "maps/c6/resources.pnm");
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
		wmap.addObject(3, 11, h1);
		wmap.addObject(27, 30, h2);		
		
		List<GroupOfUnits> u1 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u2 = new ArrayList<GroupOfUnits>();
		u1.add(new GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new GroupOfUnits(core.UnitType.KUSZNIK, null, 5));
		u2.add(new GroupOfUnits(core.UnitType.NIMFA, null, 10 ));
		u2.add(new GroupOfUnits(core.UnitType.ELFI_SKRYTOBOJCA, null, 5));
		
		h1.setUnits(u1);
		h2.setUnits(u2);
		
		Castle c1 = new Castle(CastleType.FOREST_ROOK, Names.castle());
		Castle c2 = new Castle(CastleType.FOREST_ROOK, Names.castle());
		Castle c3 = new Castle(CastleType.DEMONICAL_NECROPOLY , Names.castle());
		Castle c4 = new Castle(CastleType.DEMONICAL_NECROPOLY, Names.castle());
	
		p1.addCastle(c1);
		p2.addCastle(c3);   ///// CEL MISJI: ODBICIE TEGO ZAMKU JAKO JEDEN Z WARUNKĂ“W ZWYCIÄSTWA
		
		wmap.addObject(3, 8, c1);
		wmap.addObject(1, 28, c2);
		wmap.addObject(29, 29, c3);
		wmap.addObject(31, 5, c4);
		System.out.println("END ADD OBJECTS");
		
		order();
		
		wmap.addObject(1, 29, IndependentUnits.create(UnitType.ARCY_DIABEL, 2));
		wmap.addObject(31,6, IndependentUnits.create(UnitType.ARCY_DIABEL, 2));
		wmap.addObject(18,11, IndependentUnits.create(UnitType.FENIKS, 3));
		wmap.addObject(18,25, IndependentUnits.create(UnitType.FENIKS, 3));
		wmap.addObject(3,3, IndependentUnits.create(UnitType.ENT, 25));
		wmap.addObject(1,11, IndependentUnits.create(UnitType.KUSZNIK, 9));
		wmap.addObject(7, 5, IndependentUnits.create(UnitType.WAPIERZ, 4));
		wmap.addObject(10, 2, IndependentUnits.create(UnitType.POLNOCNICA, 13 ));
		wmap.addObject(15, 3, IndependentUnits.create(UnitType.WOJAK, 45));
		wmap.addObject(1, 19, IndependentUnits.create(UnitType.SZKIELET, 50));
		wmap.addObject(4,18, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 11));
		wmap.addObject(14,18, IndependentUnits.create(UnitType.MAG_OGNIA, 9));
		wmap.addObject(3, 26, IndependentUnits.create(UnitType.NIMFA, 13));
		wmap.addObject(11, 31, IndependentUnits.create(UnitType.SZKIELET, 10));
		wmap.addObject(15, 34, IndependentUnits.create(UnitType.WAPIERZ, 10));
		wmap.addObject(24, 4, IndependentUnits.create(UnitType.MAG_OGNIA, 15));
		wmap.addObject(29, 2, IndependentUnits.create(UnitType.SZKIELET, 17));
		wmap.addObject(27, 8, IndependentUnits.create(UnitType.KUSZNIK, 10));
		wmap.addObject(34, 12, IndependentUnits.create(UnitType.WOJAK, 15));
		wmap.addObject(32, 18, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 15));
		wmap.addObject(21, 16, IndependentUnits.create(UnitType.SZKIELET, 23));
		wmap.addObject(34, 20, IndependentUnits.create(UnitType.NIMFA, 100));
		wmap.addObject(33, 24, IndependentUnits.create(UnitType.WOJAK, 30));
		wmap.addObject(33, 34, IndependentUnits.create(UnitType.ENT, 21));
		
		
		
		System.out.println("START POPULATE");
		wmap.populateMapWidget();
	}
	
	public void variant(String name)
	{
		p1 = new PlayerCPU("Komputer", Color.BLUE);
		p2 = new PlayerHuman(name, Color.RED);
		h1 = new Hero(Names.name(), CastleType.FOREST_ROOK);
		h2 = new Hero(Names.name(), CastleType.DEMONICAL_NECROPOLY);
		setMainHero(h2);
		
	}
	
	public void order() 
	{
		addPlayer(p2);
		addPlayer(p1);
		setActivePlayer(p2);
	}

	public String getName()
	{
		return "Akt I. Podstęp";
	}
	
	@Override
	public String getDescription()
	{
		return "Wykorzystując nieobecność księcia Elessara w królestwie ludzi udało Ci się zdobyć ich twierdzę i pokonać wojska dowodzone przez podstarzałego króla. W głównej bitwie między demonami a ludźmi udało Ci się zabić króla ludzi. Po jego stracie ludzie poddali się, dzięki czemu bez trudu opanowałeś całe królestwo. Niestety o twojej napaści dowiedzieli się sojusznicy ludzi – elfowie. By odwrócić twoją uwagę od ziem ludzi, zaatakowali oni twoje ziemie na granicy północno-zachodniej. Postanawiasz, więc ruszyć z główną armią nad granicę z elfami, by jak najszybciej pokonać wrogów. Na terenach niedawno zdobytych zostawiasz tylko niewielki garnizon, by utrzymywał porządek i nie pozwolił na powstanie buntu.";
	}

	@Override
	public String getObjective()
	{
		return "Celem twojej misji jest pokonanie wojsk nieprzyjaciela i obrona północno-zachodniej granicy. W misji tej nie może zginąć twój bohater.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.RED;
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
