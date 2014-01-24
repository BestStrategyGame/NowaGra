package core;

import java.util.*;

public class MissionC1 extends Mission
{

	/// CEL MISJI: Pokonaj wszystkich wrogów, odbij zamek wroga, nie daj zabic glownego bohatera
	public void init(String ... names)
	{
		super.init(names);
		//loadMap("maps/map2bg.png", "maps/map2bg.pnm", "maps/map2fg.pnm");
		System.out.println("START LOAD MAP");
		loadMap("maps/c1/background.jpg", "maps/c1/mask.pnm", "maps/c1/resources.pnm");
		System.out.println("END LOAD MAP");
		
		
		
		
		Player p1 = new PlayerHuman(names[0], Color.BLUE);
		Player p2 = new PlayerCPU("Komputer", Color.RED);
		
		
		p1.addResource(ResourceType.GOLD, 5000);
		p1.addResource(ResourceType.WOOD, 10);
		p1.addResource(ResourceType.ORE, 10);
		
		p2.addResource(ResourceType.GOLD, 5000);
		p2.addResource(ResourceType.WOOD, 10);
		p2.addResource(ResourceType.ORE, 10);
		
		Hero h1 = new Hero("Bohater 1", CastleType.HUMAN_CASTLE);
		Hero h2 = new Hero("Bohater 3", CastleType.DEMONICAL_NECROPOLY);
		
		p1.addHero(h1);
		p2.addHero(h2);
		wmap.addObject(28, 30, h1);
		wmap.addObject(3, 4, h2);
		
		setMainHero(h1);    /////////////// ten bohater nie moĹĽe zginÄ…Ä‡, inaczej koniec gry (warunek misji)
		
		
		List<GroupOfUnits> u1 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u2 = new ArrayList<GroupOfUnits>();
		u1.add(new GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new GroupOfUnits(core.UnitType.KUSZNIK, null, 3));
		u2.add(new GroupOfUnits(core.UnitType.SZKIELET, null, 10 ));
		u2.add(new GroupOfUnits(core.UnitType.POLNOCNICA, null, 3));
		
		h1.setUnits(u1);
		h2.setUnits(u2);
		
		
		
		
		
		Castle c1 = new Castle(CastleType.DEMONICAL_NECROPOLY, "Demon Castle");
		Castle c2 = new Castle(CastleType.FOREST_ROOK, "Forest Castle");
		Castle c3 = new Castle(CastleType.FOREST_ROOK, "Forest Castle 2");
		Castle c4 = new Castle(CastleType.HUMAN_CASTLE, "Human Castle");
		p1.addCastle(c4);
		p2.addCastle(c1);   ///// CEL MISJI: ODBICIE TEGO ZAMKU JAKO JEDEN Z WARUNKÓW ZWYCIĘSTWA
		
		wmap.addObject(5, 4, c1);
		wmap.addObject(5, 29, c2);
		wmap.addObject(24, 8, c3);
		wmap.addObject(30, 30, c4);
		System.out.println("END ADD OBJECTS");
		addPlayer(p1);
		addPlayer(p2);
		
		
		List<GroupOfUnits> u100 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u200 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u300 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u400 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u500 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u600 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u700 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u800 = new ArrayList<GroupOfUnits>();
		List<GroupOfUnits> u900 = new ArrayList<GroupOfUnits>();
		u100.add(new GroupOfUnits(UnitType.SZKIELET, null, 12));
		u200.add(new GroupOfUnits(UnitType.WOJAK, null, 10));
		u300.add(new GroupOfUnits(UnitType.KUSZNIK, null, 11));
		u400.add(new GroupOfUnits(UnitType.WAPIERZ, null, 7));
		u500.add(new GroupOfUnits(UnitType.ELFI_SKRYTOBOJCA, null, 8));
		u600.add(new GroupOfUnits(UnitType.ARCY_DIABEL, null, 1));
		u700.add(new GroupOfUnits(UnitType.FENIKS, null, 1));
		u800.add(new GroupOfUnits(UnitType.MAG_OGNIA, null, 12));
		u900.add(new GroupOfUnits(UnitType.ENT, null, 15));
		IndependentUnits i100 = new IndependentUnits();
		IndependentUnits i200 = new IndependentUnits();
		IndependentUnits i300 = new IndependentUnits();
		IndependentUnits i400 = new IndependentUnits();
		IndependentUnits i500 = new IndependentUnits();
		IndependentUnits i600 = new IndependentUnits();
		IndependentUnits i700 = new IndependentUnits();
		IndependentUnits i800 = new IndependentUnits();
		IndependentUnits i900 = new IndependentUnits();
		i100.setUnits(u100);
		i200.setUnits(u200);
		i300.setUnits(u300);
		i400.setUnits(u400);
		i500.setUnits(u500);
		i600.setUnits(u600);
		i700.setUnits(u700);
		i800.setUnits(u800);
		i900.setUnits(u900);
		
		wmap.addObject(4, 8, i100);
		wmap.addObject(5,30, i600);
		wmap.addObject(10, 30, i400);
		wmap.addObject(17, 32, i800);
		wmap.addObject(10, 12, i300);
		wmap.addObject(5, 16, i400);
		wmap.addObject(24,9, i700);
		wmap.addObject(30,4, i500);
		wmap.addObject(34, 12, i800);
		wmap.addObject(31, 23, i300);
		wmap.addObject(33, 27, i900);
		wmap.addObject(34, 33, i200);
		wmap.addObject(3, 25, i100 );
		wmap.addObject(1, 29,i500 );
		wmap.addObject(20, 4, i100);
		wmap.addObject(19, 11, i200);
		wmap.addObject(16, 17, i300);
		wmap.addObject(23, 20, i400);
		wmap.addObject(15, 25, i900);
		
		
		setActivePlayer(p1);
		System.out.println("START POPULATE");
		wmap.populateMapWidget();
	}

	@Override
	public String getName()
	{
		return "Akt I. PowrĂłt";
	}
	
	@Override
	public String getDescription()
	{
		return "WracajÄ…c do krĂłlestwa swojego ojca po dalekiej podrĂłĹĽy zastajesz spustoszone ziemie. W drodze do zamku spotykasz uciekiniera od ktĂłrego dowiadujesz siÄ™, ĹĽe na twe ziemie napadĹ‚ wĹ‚adca demonĂłw Gothmog. ZamordowaĹ‚ on twojego ojca, a twoja ĹĽona zostaĹ‚a wziÄ™ta do niewoli. Streszcza ci on pokrĂłtce w jaki sposĂłb udaĹ‚o siÄ™ wĹ‚adcy demonĂłw pokonaÄ‡ twojego ojca, a takĹĽe obecnÄ… sytuacjÄ™ w krĂłlestwie. DziÄ™ki temu dowiadujesz siÄ™, ĹĽe Gothmog wrĂłciĹ‚ do swojego krĂłlestwa, zostawiajÄ… c tylko niewielkÄ… czÄ™Ĺ›Ä‡ swojej armii do utrzymania porzÄ…dku. Postanawiasz, wiÄ™c odbiÄ‡ swoje krĂłlestwo z rÄ…k wroga. Dlatego rozsyĹ‚asz czÄ™Ĺ›Ä‡ swoich ludzi po krainie, by roznieĹ›li oni wieĹ›Ä‡ o twoim powrocie i rozpoczÄ™ciu zbierania armii, by pokonaÄ‡ demony. Za swojÄ… nowÄ… siedzibÄ™ obierasz opuszczonÄ… fortecÄ™ z dawnych lat, gdy jeszcze panowaĹ‚ twĂłj pradziad";
	}

	@Override
	public String getObjective()
	{
		return "Twoim celem jest odbicie zamku twojego ojca, a takĹĽe pokonanie wszystkich wrogich jednostek znajdujÄ…cych siÄ™ na twoich ziemiach. TwĂłj bohater nie moĹĽe zginÄ…Ä‡.";
	}

	private Player last;
	
	@Override
	public boolean endCondition()
	{
		Color last = lastStanding();
		return last != null;
	}

	@Override
	public Color playerColor()
	{
		return Color.BLUE;
	}
	
	@Override
	public boolean won()
	{
		return playerColor() == lastStanding();
	}
	
}
