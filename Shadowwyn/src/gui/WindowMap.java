package gui;

import java.util.*;

import com.trolltech.qt.core.Qt.CursorShape;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QLayout.SizeConstraint;
import com.trolltech.qt.gui.QSizePolicy.Policy;

import core.Castle;
import core.Hero;
import core.Mission;
import core.WorldMap;

public class WindowMap extends QFrame
{
	private static WindowMap LAST_INSTANCE;
	public static WindowMap getLastInstance()
	{
		return LAST_INSTANCE;
	}
	
	private core.Player player;
	private core.Hero hero;
	
	private QWidget map;
	private MiniMap minimap = new MiniMap(this);
	private QGridLayout layout = new QGridLayout();
	private QVBoxLayout layoutRight = new QVBoxLayout();
	private QFormLayout layoutResources = new QFormLayout();

	private QLabel statusGold = new QLabel("0");
	private QLabel statusOre = new QLabel("0");
	private QLabel statusWood = new QLabel("0");
	private QLabel statusDay = new QLabel("1");
	private QLabel statusPlayer = new QLabel("");
	private QGridLayout layoutButtons = new QGridLayout();
	private QPushButton buttonMove = new QPushButton("Idź\nbohaterem");
	private QPushButton buttonTurn = new QPushButton("Zakończ\nturę");
	private QPushButton buttonCastle = new QPushButton("Interakcja\nz zamkiem");
	private QPushButton buttonHero = new QPushButton("Wybranty\nbohater");
	private QPushButton buttonQuit = new QPushButton("Zakończ misję");
	
	private QScrollArea heroesScroll = new QScrollArea();
	private QScrollArea castlesScroll = new QScrollArea();
	private QWidget right = new QWidget();
	private QScrollArea scroll;
	
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
	public Signal1<core.Castle> castleSelected = new Signal1<core.Castle>();
	public Signal0 moveClicked = new Signal0();
	public Signal0 turnClicked = new Signal0();
	public Signal0 interactWithCastle = new Signal0();
	public Signal0 interactWithHero = new Signal0();
	
	
	
	public WindowMap()
	{
		super();
		LAST_INSTANCE = this;
		
		setLayout(layout);
		//layout.addWidget(new WidgetHolder(this, 100, new WidgetChooser(true)));
		
		right.setSizePolicy(Policy.Fixed, Policy.Preferred);
		right.setLayout(layoutRight);
		//layout.addWidget(minimap, 0, 1);
		layout.addWidget(right, 0, 1, 2, 1);
		layoutRight.setMargin(0);
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
		layoutResources.addRow("  Dzień:", statusDay);
		layoutResources.addRow("  Gracz:", statusPlayer);
		layoutResources.addRow("  Złoto:", statusGold);
		layoutResources.addRow("  Kamień:", statusOre);
		layoutResources.addRow("  Drewno:", statusWood);
		
		layoutRight.addLayout(layoutButtons);
		layoutButtons.setWidgetSpacing(0);
		layoutButtons.addWidget(buttonMove, 0, 0);
		layoutButtons.addWidget(buttonTurn, 0, 1);
		layoutButtons.addWidget(buttonCastle, 1, 0);
		layoutButtons.addWidget(buttonHero, 1, 1);
		layoutButtons.addWidget(buttonQuit, 3, 0, 1, 2);
		
		
		buttonQuit.clicked.connect(this, "quitClicked()");
		buttonMove.clicked.connect(moveClicked);
		buttonMove.clicked.connect(this, "moveClicked()");
		buttonTurn.clicked.connect(turnClicked);
		buttonCastle.clicked.connect(interactWithCastle);
		buttonHero.clicked.connect(interactWithHero);
		minimap.pressed.connect(this, "minimapClicked(int, int)");
		
	}
	
	private void lockEnableButtons()
	{
		buttonMove.setEnabled(lockCount == 0);
		buttonTurn.setEnabled(lockCount == 0);
		buttonCastle.setEnabled(lockCount == 0);
		buttonHero.setEnabled(lockCount == 0);
		buttonQuit.setEnabled(lockCount == 0);
	}
	
	int lockCount = 0;
	public void lock()
	{
		if (lockCount++ == 0) {
			//QMessageBox.about(this, "LOCK", "");
			QCursor cursor = new QCursor();
			cursor.setShape(CursorShape.WaitCursor);
			QApplication.setOverrideCursor(cursor);
			statusPlayer.setText("-");
			statusGold.setText("-");
			statusOre.setText("-");
			statusWood.setText("-");
			lockEnableButtons();
			cleanCastles();
			cleanHeroes();
			QApplication.processEvents();
			
		}
	}
	
	public void unlock()
	{
		if (--lockCount == 0) {
			//QMessageBox.about(this, "UNLOCK", "");
			lockEnableButtons();
			QApplication.restoreOverrideCursor();
			QApplication.processEvents();
		}
	}
	
	public void minimapClicked(int x, int y)
	{
		scroll.ensureVisible(y*64, x*64, 64*4, 64*4);
	}
	
	public void quitClicked()
	{
		WindowStack.getLastInstance().pop();
		WindowStack.getLastInstance().pop();
		Mission.getLastInstance().dispose();
	}
	
	public void moveClicked()
	{
		if (hero != null) {
			scroll.ensureVisible(this.hero.getX()*64, this.hero.getY()*64, 64*4, 64*4);
		}
		minimap.repaint();
	}
	
	public void addHero(core.Hero hero)
	{
		if (lockCount != 0) return;
		
		WidgetChooserButton button;
		
		if (heroesSelected != -1) {
			try {
				button = (WidgetChooserButton)(heroesInner.itemAt(heroesSelected).widget());
				button.setChecked(false);
			} catch (NullPointerException e) {}
		}
		
		
		button = new WidgetChooserButton(hero, hero.getName(), true);
		button.setChecked(true);
		button.selected.connect(this, "selectHero(Object)");
		heroesSelected = heroesInner.count();
		heroesInner.insertWidget(-1, button);
		
		
		heroes.add(hero);
		minimap.repaint();
	}
	
	public void addCastle(core.Castle castle)
	{
		if (lockCount != 0) return;
		
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
		this.hero = (Hero)hero;
		scroll.ensureVisible(this.hero.getX()*64, this.hero.getY()*64, 64*4, 64*4);
		heroChanged.emit(this.hero);
	}
	
	public void selectCastle(Object object)
	{
		//castleSelected.emit((Castle)castle);
		
		WindowStack ws = WindowStack.getLastInstance();
		if (ws != null) {
			Castle castle = (Castle)object;
			ws.push(new WindowCastle(player, castle, castle.getHero()));
			
			core.WorldMap wm = core.WorldMap.getLastInstance();
			if (wm != null) {
				core.Point pos = wm.getPositionOfObject(castle);
				scroll.ensureVisible(pos.x*64, pos.y*64, 64*4, 64*4);
			}
		}
		
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
	
	public void setHero(Hero h)
	{
		hero = h;
	}
	
	public void removeHero()
	{
		if (lockCount != 0) return;
		
		heroesInner.takeAt(heroesSelected).widget().dispose();
		heroes.remove(heroesSelected);
		heroesSelected = 0;
		try {
			((WidgetChooserButton)(heroesInner.itemAt(heroesSelected).widget())).setChecked(true);
			selectHero(heroes.get(heroesSelected));
		} catch (java.lang.NullPointerException e) {}

		
		QApplication.processEvents();
		minimap.repaint();
	}
	
	public void startTurn(int day, core.Player player)
	{
		this.player = player;
		
		System.out.println("startTurn");
		statusGold.setText(""+player.getResource(core.ResourceType.GOLD));
		statusOre.setText(""+player.getResource(core.ResourceType.ORE));
		statusWood.setText(""+player.getResource(core.ResourceType.WOOD));
		statusDay.setText(""+((day-1)%7+1)+" / "+((day-1)/7+1)+" tydzień");
		statusPlayer.setText(player.getName());
		QApplication.processEvents();
		
		cleanHeroes();
		for (Hero hero: player.getHeroes()) {
			System.out.println("add hero?" + hero.getName());
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
		
		//buttonTurn.Sheet("background-color: #ff0000");
		minimap.repaint();
		QApplication.processEvents();
	}
	
	public void updateData(core.Player player)
	{
		if (lockCount != 0) return;
		//System.out.println("update");
		
		statusGold.setText(""+player.getResource(core.ResourceType.GOLD));
		statusOre.setText(""+player.getResource(core.ResourceType.ORE));
		statusWood.setText(""+player.getResource(core.ResourceType.WOOD));
		
		
		cleanCastles();
		for (Castle castle: player.getCastles()) {
			addCastle(castle);
		}
		
		minimap.repaint();
		QApplication.processEvents();
	}
	
	public void setMap(QWidget m)
	{
		if (map != null) {
			map.dispose();
		}
		map = m;
		
		scroll = new QScrollArea();
		scroll.setSizePolicy(Policy.MinimumExpanding, Policy.Preferred);
		scroll.setWidget(m);
		layout.addWidget(scroll, 0, 0, 2, 1);
		//scroll.setStyleSheet("background-color: blue;");
	}
}
