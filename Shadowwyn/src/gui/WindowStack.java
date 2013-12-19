package gui;

import java.util.*;

import com.trolltech.qt.gui.*;

import core.MissionMap2;

public class WindowStack extends QWidget
{
	private static WindowStack LAST_INSTANCE;
	
	public static WindowStack getLastInstance()
	{
		return LAST_INSTANCE;
	}
	
	private QStackedLayout layout = new QStackedLayout();
	
	public WindowStack()
	{
		super();
		
		//setStyleSheet("background-color: #ccc; color: black;");
		setStyleSheet("* {background-color: #ddd; color: #333;} QPushButton[enabled=\"false\"] { color: #888 }");
		
		setLayout(layout);
		
		LAST_INSTANCE = this;
	}
	
	public void push(QWidget widget)
	{
		layout.addWidget(widget);
		layout.setCurrentIndex(layout.count()-1);
	}
	
	public void pop()
	{
		layout.removeWidget(layout.currentWidget());
	}
	
	public static void main(String[] args)
	{
		QApplication.initialize(args);
		
		
		// MAP STUFF
		core.Mission mission;
		gui.WindowMap window;
		
		mission = new MissionMap2();
		window = new gui.WindowMap();
		gui.WidgetMap map = mission.getMapWidget();
		mission.startTurn.connect(window, "startTurn(int,Player)");
		mission.updateWindow.connect(window, "updateData(Player)");
		window.heroChanged.connect(mission, "activeHeroChanged(Hero)");
		window.moveClicked.connect(mission, "moveClicked()");
		window.turnClicked.connect(mission, "turnClicked()");
		window.interactWithCastle.connect(mission, "interactWithCastle()");
		window.interactWithHero.connect(mission, "interactWithHero()");
		map.pressed.connect(mission, "clickedOnMap(int,int)");
		window.setMap(map);
		
		mission.start();
		
		// CASTLE STUFF
		/*List<core.GroupOfUnits> u1 = new ArrayList<core.GroupOfUnits>();
		u1.add(new core.GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new core.GroupOfUnits(core.UnitType.KUSZNIK, null, 20));
		//u1.add(new core.GroupOfUnits(core.UnitType.KUSZNIK, null, 20));
		//u1.add(new core.GroupOfUnits(core.UnitType.KUSZNIK, null, 20));
		//core.Hero h =  new core.Hero("foo");
		//h.setUnits(u1);
		
		core.Player p1 = new core.PlayerHuman("gracz 1", core.Color.RED);
		core.Castle c1 = new core.Castle(core.CastleType.HUMAN_CASTLE, null);
		p1.addResource(core.ResourceType.GOLD, 10000);
		p1.addResource(core.ResourceType.WOOD, 100);
		p1.addResource(core.ResourceType.ORE, 100);
		p1.addCastle(c1);
		c1.addBuilding(core.CastleBuilding.CITY_COUNCIL);
		c1.setUnits(u1);
		*/
		
		
		WindowStack w =  new WindowStack();
		//WindowCastle wc =  new WindowCastle(p1, c1, null);
		w.push(window);
		//w.push(wc);
		w.setMinimumSize(1024, 768);
		w.show();
		
		QApplication.exec();
	}
}