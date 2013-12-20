package core;

public class MissionMap2 extends Mission
{

	public MissionMap2()
	{
		super();
		//loadMap("maps/map2bg.png", "maps/map2bg.pnm", "maps/map2fg.pnm");
		loadMap("maps/c1/background.jpg", "maps/c1/mask.pnm", "maps/c1/resources.pnm");
		
		Player p1 = new PlayerHuman("Gracz 1", Color.RED);
		Player p2 = new PlayerHuman("Gracz 2", Color.BLUE);
		
		p1.addResource(ResourceType.GOLD, 5000);
		p1.addResource(ResourceType.WOOD, 10);
		p1.addResource(ResourceType.ORE, 10);
		
		Hero h1 = new Hero("Bohater 1", CastleType.HUMAN_CASTLE);
		Hero h2 = new Hero("Bohater 2", CastleType.HUMAN_CASTLE);
		Hero h3 = new Hero("Bohater 3", CastleType.HUMAN_CASTLE);
		Hero h4 = new Hero("Bohater 4", CastleType.DEMONICAL_NECROPOLY);
		p1.addHero(h1);
		p1.addHero(h2);
		p1.addHero(h3);
		p1.dieHero(h3);	
		p2.addHero(h4);
		
		wmap.addObject(10, 10, h1);
		wmap.addObject(5, 27, h2);
		wmap.addObject(3, 3, h4);
		
		Castle c1 = new Castle(CastleType.HUMAN_CASTLE, "Zamek NW");
		Castle c2 = new Castle(CastleType.HUMAN_CASTLE, "Zamek SW");
		Castle c3 = new Castle(CastleType.HUMAN_CASTLE, "Zamek NE");
		Castle c4 = new Castle(CastleType.HUMAN_CASTLE, "Zamek SE");
		p1.addCastle(c1);
		
		wmap.addObject(5, 4, c1);
		wmap.addObject(5, 29, c2);
		wmap.addObject(24, 8, c3);
		wmap.addObject(30, 30, c4);
		
		addPlayer(p1);
		addPlayer(p2);
		
		setActivePlayer(p1);
		
		wmap.populateMapWidget();
	}
}
