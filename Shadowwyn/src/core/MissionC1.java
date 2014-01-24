package core;

import java.util.*;

public class MissionC1 extends Mission
{

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
		Hero h2 = new Hero("Bohater 2", CastleType.HUMAN_CASTLE);
		Hero h3 = new Hero("Bohater 3", CastleType.HUMAN_CASTLE);
		Hero h4 = new Hero("Bohater 4", CastleType.DEMONICAL_NECROPOLY);
		p1.addHero(h1);
		p1.addHero(h2);
		p1.addHero(h3);
		p1.dieHero(h3);	
		p2.addHero(h4);
		
		setMainHero(h1);
		
		List<GroupOfUnits> u1 = new ArrayList<GroupOfUnits>();
		u1.add(new GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new GroupOfUnits(core.UnitType.KUSZNIK, null, 3));
		h4.setUnits(u1);
		
		wmap.addObject(10, 10, h1);
		wmap.addObject(5, 27, h2);
		wmap.addObject(3, 3, h4);
		
		Castle c1 = new Castle(CastleType.HUMAN_CASTLE, "Zamek NW");
		Castle c2 = new Castle(CastleType.FOREST_ROOK, "Zamek SW");
		Castle c3 = new Castle(CastleType.HUMAN_CASTLE, "Zamek NE");
		Castle c4 = new Castle(CastleType.HUMAN_CASTLE, "Zamek SE");
		p1.addCastle(c1);
		
		wmap.addObject(5, 4, c1);
		wmap.addObject(5, 29, c2);
		wmap.addObject(24, 8, c3);
		wmap.addObject(30, 30, c4);
		System.out.println("END ADD OBJECTS");
		addPlayer(p1);
		addPlayer(p2);
		
		
		List<GroupOfUnits> u100 = new ArrayList<GroupOfUnits>();
		u100.add(new GroupOfUnits(UnitType.WOJAK, null, 10));
		IndependentUnits i100 = new IndependentUnits();
		i100.setUnits(u100);
		
		wmap.addObject(4, 8, i100);
		
		
		setActivePlayer(p1);
		System.out.println("START POPULATE");
		wmap.populateMapWidget();
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
