package gui;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QLayout.SizeConstraint;
import com.trolltech.qt.gui.QSizePolicy.Policy;

import core.Castle;
import core.Hero;

public class WindowMap extends QWidget
{
	private QWidget map;
	private QWidget minimap = new MiniMap(this);
	private QGridLayout layout = new QGridLayout();
	private QVBoxLayout layoutRight = new QVBoxLayout();
	private QFormLayout layoutResources = new QFormLayout();

	private QLabel statusGold = new QLabel("0");
	private QLabel statusOre = new QLabel("0");
	private QLabel statusWood = new QLabel("0");
	private QGridLayout layoutButtons = new QGridLayout();
	private QPushButton buttonMove = new QPushButton("Idź\nbohaterem");
	private QPushButton buttonTurn = new QPushButton("Zakończ\nturę");
	private QScrollArea heroesScroll = new QScrollArea();
	private QScrollArea castlesScroll = new QScrollArea();
	private QWidget right = new QWidget();
	
	private List<core.Castle> castles = new ArrayList<core.Castle>();
	private QWidget castlesWidget = new QWidget();
	private QVBoxLayout castlesOuter = new QVBoxLayout();
	private QVBoxLayout castlesInner = new QVBoxLayout();
	
	private List<core.Hero> heroes = new ArrayList<core.Hero>();
	private QWidget heroesWidget = new QWidget();
	private QVBoxLayout heroesOuter = new QVBoxLayout();
	private QVBoxLayout heroesInner = new QVBoxLayout();
	private int heroesSelected = -1;
	
	public Signal1<core.Hero> heroChanged = new Signal1<core.Hero>();
	public Signal0 moveClicked = new Signal0();
	public Signal0 turnClicked = new Signal0();
	
	
	public WindowMap()
	{
		super();
		
		setLayout(layout);
		//layout.addWidget(new WidgetHolder(this, 100, new WidgetChooser(true)));
		
		right.setSizePolicy(Policy.Fixed, Policy.Preferred);
		right.setLayout(layoutRight);
		layout.addWidget(right, 0, 1);
		layoutRight.addWidget(minimap);
		layoutRight.addWidget(new QLabel("Zamki:"));
		
		
		castlesInner.setMargin(0);
		castlesInner.setSpacing(0);
		castlesOuter.addLayout(castlesInner);
		castlesOuter.addStretch();
		castlesOuter.setMargin(0);
		castlesOuter.setSpacing(0);
		castlesWidget.setLayout(castlesOuter);
		castlesScroll.setWidget(castlesWidget);
		castlesScroll.setWidgetResizable(true);
		layoutRight.addWidget(castlesScroll);
		

		layoutRight.addWidget(new QLabel("Bohaterowie:"));
		
		// heroes
		
		heroesInner.setMargin(0);
		heroesInner.setSpacing(0);
		heroesOuter.addLayout(heroesInner);
		heroesOuter.addStretch();
		heroesOuter.setMargin(0);
		heroesOuter.setSpacing(0);
		heroesWidget.setLayout(heroesOuter);
		heroesScroll.setWidget(heroesWidget);
		heroesScroll.setWidgetResizable(true);
		layoutRight.addWidget(heroesScroll);
		
		layoutRight.addLayout(layoutResources);
		layoutResources.addRow("Złoto:", statusGold);
		layoutResources.addRow("Kamień:", statusOre);
		layoutResources.addRow("Drewno:", statusWood);
		layoutRight.addLayout(layoutButtons);
		layoutButtons.addWidget(buttonMove, 0, 0);
		layoutButtons.addWidget(buttonTurn, 0, 1);		
		
		buttonMove.clicked.connect(moveClicked);
		buttonTurn.clicked.connect(turnClicked);
		
	}
	
	public void addHero(core.Hero hero)
	{
		WidgetChooserButton button;
		
		if (heroesSelected != -1) {
			button = (WidgetChooserButton)(heroesInner.itemAt(heroesSelected).widget());
			button.setChecked(false);
		}
		
		
		button = new WidgetChooserButton(hero, hero.getName(), true);
		button.setChecked(true);
		button.selected.connect(this, "selectHero(Object)");
		heroesSelected = heroesInner.count();
		heroesInner.insertWidget(-1, button);
		
		
		heroes.add(hero);
	}
	
	public void addCastle(core.Castle castle)
	{
		WidgetChooserButton button;
		button = new WidgetChooserButton(castle, castle.getName(), false);
		button.selected.connect(this, "selectCastle(Object)");
		castlesInner.insertWidget(-1, button);
		castles.add(castle);
	}
	
	public void selectHero(Object hero)
	{
		System.out.println("?! "+heroesSelected);
		WidgetChooserButton button = (WidgetChooserButton)(heroesInner.itemAt(heroesSelected).widget());
		if (hero != heroes.get(heroesSelected)) {
			heroesSelected = heroes.indexOf(hero);
			button.setChecked(false);
		} else {
			button.setChecked(true);
		}
		heroChanged.emit((Hero)hero);
	}
	
	public void selectCastle(Object castle)
	{
	}
	
	public void cleanHeroes()
	{
		heroes = new ArrayList<core.Hero>();
		while (heroesInner.count() > 0) {
			heroesInner.takeAt(0).widget().dispose();
		}
		heroesSelected = -1;
	}
	
	public void cleanCastles()
	{
		castles = new ArrayList<core.Castle>();
		while (castlesInner.count() > 0) {
			castlesInner.takeAt(0).widget().dispose();
		}
	}
	
	public void removeHero()
	{
		heroesInner.takeAt(heroesSelected).widget().dispose();
		heroes.remove(heroesSelected);
		heroesSelected = 0;
		((WidgetChooserButton)(heroesInner.itemAt(heroesSelected).widget())).setChecked(true);
		selectHero(heroes.get(heroesSelected));
	}
	
	public void startTurn(int day, core.Player player)
	{
		System.out.println("startTurn");
		statusGold.setText(""+player.getResource(core.ResourceType.GOLD));
		statusOre.setText(""+player.getResource(core.ResourceType.ORE));
		statusWood.setText(""+player.getResource(core.ResourceType.WOOD));
		
		cleanHeroes();
		for (Hero hero: player.getHeroes()) {
			System.out.println("add hero?");
			addHero(hero);
		}
		
		cleanCastles();
		for (Castle castle: player.getCastles()) {
			addCastle(castle);
		}
		
		//selectHero(player.getHeroes().iterator().next());
		if (heroesInner.count() > 0) {
			((WidgetChooserButton)(heroesInner.itemAt(0).widget())).click();
		}
		
		//buttonTurn.setStyleSheet("background-color: #ff0000");
	}
	
	public void updateData(core.Player player)
	{
		System.out.println("update");
		statusGold.setText(""+player.getResource(core.ResourceType.GOLD));
		statusOre.setText(""+player.getResource(core.ResourceType.ORE));
		statusWood.setText(""+player.getResource(core.ResourceType.WOOD));
	}
	
	public void setMap(QWidget m)
	{
		if (map != null) {
			map.dispose();
		}
		map = m;
		QScrollArea scroll = new QScrollArea();
		scroll.setSizePolicy(Policy.MinimumExpanding, Policy.Preferred);
		scroll.setWidget(m);
		layout.addWidget(scroll, 0, 0);
	}
}