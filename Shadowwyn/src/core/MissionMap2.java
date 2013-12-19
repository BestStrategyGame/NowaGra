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
		
		Hero h1 = new Hero("Bohater 1");
		Hero h2 = new Hero("Bohater 2");
		Hero h3 = new Hero("Bohater 3");
		p1.addHero(h1);
		p1.addHero(h2);	
		p2.addHero(h3);
		
		wmap.addObject(10, 10, h1);
		wmap.addObject(7, 7, h2);
		wmap.addObject(3, 3, h3);
		
		Castle c1 = new Castle(CastleType.HUMAN_CASTLE, "Mój zamek");
		p1.addCastle(c1);
		
		wmap.addObject(5, 4, c1);
		
		addPlayer(p1);
		addPlayer(p2);
		
		setActivePlayer(p1);
		
		wmap.populateMapWidget();
	}
}
