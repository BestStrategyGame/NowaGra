package gui;

import java.util.*;

import com.trolltech.qt.core.Qt.Alignment;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QLayout.SizeConstraint;
import com.trolltech.qt.gui.QMessageBox.StandardButton;
import com.trolltech.qt.gui.QMessageBox.StandardButtons;
import com.trolltech.qt.gui.QSizePolicy.Policy;

import core.Hero;
import core.WorldMap;

public class WindowCastle extends QWidget
{
	private core.Player player;
	private core.Castle castle;
	private core.Hero hero;
	
	private QVBoxLayout layout = new QVBoxLayout();
	private QHBoxLayout bottomLayout = new QHBoxLayout();
	private QGridLayout buyLayout = new QGridLayout();
	private QWidget buyWidget = new QWidget();
	
	private QLabel statusGold = new QLabel("0");
	private QLabel statusOre = new QLabel("0");
	private QLabel statusWood = new QLabel("0");
	
	private QPushButton exit = new QPushButton("Wyjdź z zamku");
	private QPushButton newHero = new QPushButton("Wynajmij bohatera");
	private QPushButton oldHero = new QPushButton("Wskrześ bohatera");
	private QPushButton market = new QPushButton("Targowisko");
	//private QPushButton
	
	private WidgetBuy[] buys = new WidgetBuy[10];
	private WidgetRecruit[] recruits = new WidgetRecruit[4];
	private WidgetUnits units;
	
	public WindowCastle(core.Player p, core.Castle c, core.Hero h)
	{
		super();
		
		//setStyleSheet("background-color: #333; color: white;");
		
		player = p;
		castle = c;
		hero = h;
		Hero g = c.getGarission();
		
		setLayout(layout);
		buyWidget.setLayout(buyLayout);
		
		buys[0] = new WidgetBuy(p, c, core.CastleBuilding.CITY_COUNCIL);
		buyLayout.addWidget(buys[0], 1, 1, AlignmentFlag.AlignTop);
		buys[1] = new WidgetBuy(p, c, core.CastleBuilding.TOWN_HALL);
		buyLayout.addWidget(buys[1], 1, 2, AlignmentFlag.AlignTop);
		buys[2] = new WidgetBuy(p, c, core.CastleBuilding.CAPITOL);
		buyLayout.addWidget(buys[2], 1, 3, AlignmentFlag.AlignTop);
		
		buys[3] = new WidgetBuy(p, c, core.CastleBuilding.FORT);
		buyLayout.addWidget(buys[3], 2, 1, AlignmentFlag.AlignTop);
		buys[4] = new WidgetBuy(p, c, core.CastleBuilding.CITADEL);
		buyLayout.addWidget(buys[4], 2, 2, AlignmentFlag.AlignTop);
		buys[5] = new WidgetBuy(p, c, core.CastleBuilding.CASTLE);
		buyLayout.addWidget(buys[5], 2, 3, AlignmentFlag.AlignTop);
		
		buys[6] = new WidgetBuy(p, c, core.CastleBuilding.HUMAN_CASTLE_TIER1);
		buyLayout.addWidget(buys[6], 3, 1, AlignmentFlag.AlignTop);
		
		recruits[0] = new WidgetRecruit(p, c, g, core.UnitType.getTier(1, c.getType()));
		buyLayout.addWidget(recruits[0], 3, 2, 3, 3, AlignmentFlag.AlignTop);
		
		buys[7] = new WidgetBuy(p, c, core.CastleBuilding.HUMAN_CASTLE_TIER2);
		buyLayout.addWidget(buys[7], 4, 1, AlignmentFlag.AlignTop);
		
		recruits[1] = new WidgetRecruit(p, c, g, core.UnitType.getTier(2, c.getType()));
		buyLayout.addWidget(recruits[1], 4, 2, 4, 3, AlignmentFlag.AlignTop);
		
		buys[8] = new WidgetBuy(p, c, core.CastleBuilding.HUMAN_CASTLE_TIER3);
		buyLayout.addWidget(buys[8], 5, 1, AlignmentFlag.AlignTop);
		
		recruits[2] = new WidgetRecruit(p, c, g, core.UnitType.getTier(3, c.getType()));
		buyLayout.addWidget(recruits[2], 5, 2, 5, 3, AlignmentFlag.AlignTop);
		
		buys[9] = new WidgetBuy(p, c, core.CastleBuilding.HUMAN_CASTLE_TIER4);
		buyLayout.addWidget(buys[9], 6, 1, AlignmentFlag.AlignTop);
		
		recruits[3] = new WidgetRecruit(p, c, g, core.UnitType.getTier(4, c.getType()));
		buyLayout.addWidget(recruits[3], 6, 2, 6, 3, AlignmentFlag.AlignTop);
		
		QScrollArea scroll = new QScrollArea();
		scroll.setWidget(buyWidget);
		scroll.setWidgetResizable(true);
		scroll.setSizePolicy(Policy.MinimumExpanding, Policy.MinimumExpanding);
		
		layout.addWidget(scroll);
		layout.addLayout(bottomLayout);
		units = new WidgetUnits(g.getUnits(), h==null ? null : h.getUnits(), g, h);
		bottomLayout.addWidget(units);
		
		QFormLayout statusLayout = new QFormLayout();
		//statusLayout.addRow("Dzień:", statusDay);
		statusLayout.setMargin(10);
		//statusLayout.addRow("", new QLabel());
		statusLayout.addRow("Gracz:", new QLabel(player.getName()));
		statusLayout.addRow("Złoto:", statusGold);
		statusLayout.addRow("Kamień:", statusOre);
		statusLayout.addRow("Drewno:", statusWood);
		
		
		
		for (int i=0; i<buys.length; ++i) {
			buys[i].buyed.connect(this,"buyed()");
		}
		
		for (int i=0; i<recruits.length; ++i) {
			recruits[i].recruited.connect(this,"recruited()");
		}
		
		QVBoxLayout buttonsLayout = new QVBoxLayout();
		buttonsLayout.setMargin(10);

		buttonsLayout.addWidget(exit);
		buttonsLayout.addWidget(newHero);
		buttonsLayout.addWidget(oldHero);
		buttonsLayout.addWidget(market);
		//buttonsLayout.addWidget(new QLabel());
		//buttonsLayout.addStretch();
		
		
		//statusLayout.addRow(exit);
		
		
		bottomLayout.addLayout(buttonsLayout);
		bottomLayout.addLayout(statusLayout);
		bottomLayout.addStretch();
		
		exit.clicked.connect(this, "exit()");
		market.clicked.connect(this, "market()");
		newHero.clicked.connect(this, "recruitHero()");
		oldHero.clicked.connect(this, "resurectHero()");
		
		oldHero.setMinimumSize(150, 20);
		newHero.setMinimumSize(150, 20);
		exit.setMinimumSize(150, 20);
		market.setMinimumSize(150, 20);
		updateResources();
	}
	
	public void resurectHero()
	{
		if (player.getDeadHeroes().size() == 0) {
			QMessageBox.warning(this, "Nie można wskrzesić bohatera", "Nie posiadasz martwego bohatera którego można wskrzesić");
		} else if (castle.getHero() != null) {
			QMessageBox.warning(this, "Nie można wskrzesić bohatera", "W tym zamku znajduje się już inny bohater");
		} else {
			Map<String, core.Hero> list = new HashMap<String, core.Hero>();
			for (core.Hero hero: player.getDeadHeroes()) {
				int cost = 500+hero.getLevel()*500;
				list.put(hero.getName()+" (poziom "+hero.getLevel()+") za "+cost+" złota", hero);
			}
			
			String select = QInputDialog.getItem(this, "Wskrześ bohatera", "Wybierz bohatera którego chcesz wskrzesić", new ArrayList(list.keySet()), 0, false);
			if (select != null) {
				core.Hero hero = list.get(select);
				int cost = 500+hero.getLevel()*500;
				System.out.println(hero.getName());
				
				if (player.getResource(core.ResourceType.GOLD) < cost) {
					QMessageBox.warning(this, "Nie można wynająć bohatera", "Nie posiadasz wystarczającej ilości złota");
				} else {
					player.resurectHero(hero);
					addNewHero(hero);
					player.addResource(core.ResourceType.GOLD, -cost);
					updateResources();
				}
			}
		}
	}
	
	
	
	public void recruitHero()
	{
		if (castle.getHero() != null) {
			QMessageBox.warning(this, "Nie można wynająć bohatera", "W tym zamku znajduje się już inny bohater");
		} else if (player.getResource(core.ResourceType.GOLD) < 2500) {
			QMessageBox.warning(this, "Nie można wynająć bohatera", "Wynajęcie bohatera kosztuje 2500 złota");
		} else {
			if (QMessageBox.question(this, "Wynajęcie bohatera", "Czy chcesz wynająć bohatera za 2500 złota?",
					new StandardButtons(StandardButton.Yes, StandardButton.No)) != StandardButton.Yes) {
				return;
			}
			Hero hero = new Hero("Nowy bohater", castle.getType());
			addNewHero(hero);
			player.addResource(core.ResourceType.GOLD, -2500);
			updateResources();
			//QMessageBox.warning(this, "Wynajęto bohatera", "Wynajęto bohatera");
			
		}
	}
	
	public void addNewHero(core.Hero hero)
	{
		core.WorldMap wmap = core.WorldMap.getLastInstance();
		if (wmap != null) {
			player.addHero(hero);
			castle.setHero(hero);
			castle.lockStanding();
			units.setRight(hero);
			core.Point pos = wmap.getPositionOfObject(castle);
			wmap.addObject(pos.x, pos.y, hero);
			//wmap.getMapWidget().objectAt(pos.y, pos.x).setLayer(hero.getLevel(), new WidgetImage(hero.getImageFile(), 64));
			wmap.moveTo(hero, player, pos.x, pos.y, -1, -1);
			core.Mission m = core.Mission.getLastInstance();
			if (m != null) {
				m.addHero.emit(hero);
			}
			player.setActiveHero(hero);
		}
	}
	
	public void market()
	{
		DialogMarket dialog = new DialogMarket(player);
		dialog.exec();
		buyed();
	}
	
	public void exit()
	{
		core.WorldMap wmap = core.WorldMap.getLastInstance();
		if (wmap != null) {
			if (hero != null) {
				wmap.getMapWidget().objectAt(hero.getY(), hero.getX()).setToolTip(castle.getTooltip() +"\n\n"+ hero.getTooltip());
			} else {
				core.Point pos = wmap.getPositionOfObject(castle);
				wmap.getMapWidget().objectAt(pos.y, pos.x).setToolTip(castle.getTooltip());
			}
		}
		
		WindowStack ws = WindowStack.getLastInstance();
		if (ws != null) {
			ws.pop();
		}
	}
	
	public void recruited()
	{
		for (int i=0; i<recruits.length; ++i) {
			recruits[i].updateRange();
		}
		for (int i=0; i<buys.length; ++i) {
			buys[i].checkActive();
		}
		units.updateUnits();
		updateResources();
	}
	
	public void buyed()
	{
		for (int i=0; i<recruits.length; ++i) {
			recruits[i].updateRange();
		}
		for (int i=0; i<buys.length; ++i) {
			buys[i].checkActive();
		}
		updateResources();
	}
	
	private void updateResources()
	{
		statusGold.setText(""+player.getResource(core.ResourceType.GOLD));
		statusWood.setText(""+player.getResource(core.ResourceType.WOOD));
		statusOre.setText(""+player.getResource(core.ResourceType.ORE));
	}
	
	public static void main(String[] args)
	{
		List<core.GroupOfUnits> u1 = new ArrayList<core.GroupOfUnits>();
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
		
		
		QApplication.initialize(args);
		WindowCastle wc =  new WindowCastle(p1, c1, null);
		wc.show();
		QApplication.exec();
	}
}
