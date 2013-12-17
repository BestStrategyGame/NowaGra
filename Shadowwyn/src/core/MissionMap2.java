package core;

import com.trolltech.qt.gui.QApplication;

public class MissionMap2 extends Mission
{

	public MissionMap2()
	{
		super();
		//loadMap("maps/map2bg.png", "maps/map2bg.pnm", "maps/map2fg.pnm");
		loadMap("maps/c1/background.jpg", "maps/c1/mask.pnm", "maps/map2fg.pnm");
		System.out.println(wmap);
		Player p1 = new PlayerHuman("gracz 1", Color.RED);
		Player p2 = new PlayerHuman("gracz 2", Color.BLUE);
		
		Hero h1 = new Hero("Bohater 1");
		Hero h2 = new Hero("Bohater 2");
		Hero h3 = new Hero("Bohater 3");
		p1.addHero(h1);
		p1.addHero(h2);	
		p1.addHero(h3);
		wmap.addObject(10, 10, h1);
		wmap.addObject(7, 7, h2);
		
		Castle c1 = new Castle(CastleType.ZAMEK_LUDZI, null);
		Castle c2 = new Castle(CastleType.ZAMEK_LUDZI, null);
		p1.addCastle(c1);
		p1.addCastle(c2);
		
		addPlayer(p1);
		addPlayer(p2);
		
		setActivePlayer(p1);
		
		wmap.populateMapWidget();
	}
}
