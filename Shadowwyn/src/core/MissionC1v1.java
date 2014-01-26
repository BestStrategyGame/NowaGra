package core;

import java.util.*;

public class MissionC1v1 extends Mission
{
	protected Player p1;
	protected Player p2;
	protected Hero h1;
	protected Hero h2;

	/// CEL MISJI: Pokonaj wszystkich wrogów, odbij zamek wroga, nie daj zabic glownego bohatera
	public void init(String ... names)
	{
		super.init(names);
		//loadMap("maps/map2bg.png", "maps/map2bg.pnm", "maps/map2fg.pnm");
		System.out.println("START LOAD MAP");
		loadMap("maps/c1/background.jpg", "maps/c1/mask.pnm", "maps/c1/resources.pnm");
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
		wmap.addObject(28, 30, h1);
		wmap.addObject(3, 4, h2);		
		
		List<GroupOfUnits> u1 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u2 = new ArrayList<GroupOfUnits>();
		u1.add(new GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new GroupOfUnits(core.UnitType.KUSZNIK, null, 3));
		u2.add(new GroupOfUnits(core.UnitType.SZKIELET, null, 10 ));
		u2.add(new GroupOfUnits(core.UnitType.POLNOCNICA, null, 3));
		
		h1.setUnits(u1);
		h2.setUnits(u2);
		
		Castle c1 = new Castle(CastleType.DEMONICAL_NECROPOLY, Names.castle());
		Castle c2 = new Castle(CastleType.FOREST_ROOK, Names.castle());
		Castle c3 = new Castle(CastleType.FOREST_ROOK, Names.castle());
		Castle c4 = new Castle(CastleType.HUMAN_CASTLE, Names.castle());
		p1.addCastle(c4);
		p2.addCastle(c1);   ///// CEL MISJI: ODBICIE TEGO ZAMKU JAKO JEDEN Z WARUNKÓW ZWYCIĘSTWA
		
		wmap.addObject(5, 4, c1);
		wmap.addObject(5, 29, c2);
		wmap.addObject(24, 8, c3);
		wmap.addObject(30, 30, c4);
		System.out.println("END ADD OBJECTS");
		
		order();
		
		wmap.addObject(4, 8, IndependentUnits.create(UnitType.SZKIELET, 12));
		wmap.addObject(5,30, IndependentUnits.create(UnitType.ARCY_DIABEL, 1));
		wmap.addObject(10, 30, IndependentUnits.create(UnitType.WAPIERZ, 7));
		wmap.addObject(17, 32, IndependentUnits.create(UnitType.MAG_OGNIA, 12));
		wmap.addObject(10, 12, IndependentUnits.create(UnitType.KUSZNIK, 11));
		wmap.addObject(5, 16, IndependentUnits.create(UnitType.WAPIERZ, 7));
		wmap.addObject(24,9, IndependentUnits.create(UnitType.FENIKS, 1));
		wmap.addObject(30,4, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 8));
		wmap.addObject(34, 12, IndependentUnits.create(UnitType.MAG_OGNIA, 12));
		wmap.addObject(31, 23, IndependentUnits.create(UnitType.KUSZNIK, 11));
		wmap.addObject(33, 27, IndependentUnits.create(UnitType.ENT, 7));
		wmap.addObject(34, 33, IndependentUnits.create(UnitType.WOJAK, 10));
		wmap.addObject(3, 25, IndependentUnits.create(UnitType.SZKIELET, 12));
		wmap.addObject(1, 29, IndependentUnits.create(UnitType.ELFI_SKRYTOBOJCA, 8));
		wmap.addObject(20, 4, IndependentUnits.create(UnitType.SZKIELET, 12));
		wmap.addObject(19, 11, IndependentUnits.create(UnitType.WOJAK, 10));
		wmap.addObject(16, 17, IndependentUnits.create(UnitType.KUSZNIK, 11));
		wmap.addObject(23, 20, IndependentUnits.create(UnitType.WAPIERZ, 7));
		wmap.addObject(15, 25, IndependentUnits.create(UnitType.ENT, 7));
		
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
	
	
	public void variant(String name)
	{
		p1 = new PlayerHuman(name, Color.BLUE);
		p2 = new PlayerCPU("Komputer", Color.RED);
		h1 = new Hero(Names.name(), CastleType.HUMAN_CASTLE);
		h2 = new Hero(Names.name(), CastleType.DEMONICAL_NECROPOLY);
		setMainHero(h1); 
	}
	
	public void order() 
	{
		addPlayer(p1);
		addPlayer(p2);
		setActivePlayer(p1);
	}
	
	@Override
	public String getName()
	{
		return "Akt I. Powrót";
	}
	
	@Override
	public String getDescription()
	{
		return "Wracając do królestwa swojego ojca po dalekiej podróży zastajesz spustoszone ziemie. W drodze do zamku spotykasz uciekiniera od którego dowiadujesz się, że na twe ziemie napadł władca demonów Gothmog. Zamordował on twojego ojca, a twoja żona została wzięta do niewoli. Streszcza ci on pokrótce w jaki sposób udało się władcy demonów pokonać twojego ojca, a także obecną sytuację w królestwie. Dzięki temu dowiadujesz się, że Gothmog wrócił do swojego królestwa, zostawiają c tylko niewielką część swojej armii do utrzymania porządku. Postanawiasz, więc odbić swoje królestwo z rąk wroga. Dlatego rozsyłasz część swoich ludzi po krainie, by roznieśli oni wieść o twoim powrocie i rozpoczęciu zbierania armii, by pokonać demony. Za swoją nową siedzibę obierasz opuszczoną fortecę z dawnych lat, gdy jeszcze panował twój pradziad";
	}

	@Override
	public String getObjective()
	{
		return "Twoim celem jest odbicie zamku twojego ojca, a także pokonanie wszystkich wrogich jednostek znajdujących się na twoich ziemiach. Twój bohater nie może zginąć.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.BLUE;
	}
}
