package gui;

import java.util.*;

import com.trolltech.qt.core.Qt.CursorShape;
import com.trolltech.qt.gui.*;

import core.Battle;
import core.CastleType;
import core.Color;
import core.Const;
import core.Hero;
import core.Player;
import core.PlayerHuman;
import core.Position2Object;
import core.Terrain;

public class WindowBattle extends QWidget
{
	private static WindowBattle LAST_INSTANCE;
	
	public static WindowBattle getLastInstance()
	{
		return LAST_INSTANCE;
	}
	
	private QGridLayout layout = new QGridLayout();
	private QFormLayout layoutStats = new QFormLayout();
	private QHBoxLayout layoutButtons = new QHBoxLayout();
	private WidgetStackGrid queue = new WidgetStackGrid(3, 4, 64, false);
	private QTextEdit log = new QTextEdit("Początek bitwy");
	
	private core.Player player1;
	private core.Player player2;
	private core.Hero hero1;
	private core.Hero hero2;
	private core.Battle battle;
	
	private QPushButton wait = new QPushButton("Czekaj");
	private QPushButton give = new QPushButton("Oddaj ruch");
	private QPushButton surrender = new QPushButton("Poddaj się");
	private Position2Object<core.GroupOfUnits> p2u;
	
	private QLabel uname = new QLabel();
	private QLabel uattack = new QLabel();
	private QLabel udefense = new QLabel();
	private QLabel uspeed = new QLabel();
	private QLabel uhp = new QLabel();
	private QLabel ushooting = new QLabel();
	private QLabel umagic = new QLabel();
	private QLabel uspecial = new QLabel();
	
	
	public WindowBattle(core.Battle b, core.Player p1, core.Player p2, core.Hero h1, core.Hero h2)
	{
		super();
		LAST_INSTANCE = this;
		
		battle = b;
		player1 = p1;
		player2 = p2;
		hero1 = h1;
		hero2 = h2;
		
		setLayout(layout);
		setStyleSheet(Const.style);
		layout.addWidget(b.getMapWidget(), 0, 0, 4, 1);
		layout.addLayout(layoutStats, 0, 1);
		layoutStats.addRow(new QLabel("<b>Statystki jednostki</b>"));
		layoutStats.addRow("  Nazwa:", uname);
		layoutStats.addRow("  Atak:", uattack);
		layoutStats.addRow("  Obrona:", udefense);
		layoutStats.addRow("  Szybkość:", uspeed);
		layoutStats.addRow("  Punkty życia:", uhp);
		layoutStats.addRow("  Strzela:", ushooting);
		layoutStats.addRow("  Czaruje:", umagic);
		layoutStats.addRow(uspecial);
		layoutStats.setMargin(5);
		
		//layout.addWidget(wait, 1, 1);
		//layout.addWidget(give, 2, 1);
		QLabel label = new QLabel("<b>Kolejka:</b>");
		label.setMargin(5);
		layout.addWidget(label, 1, 1);
		layout.addWidget(queue, 2, 1);
		b.mouseOver.connect(this, "updateStats(core.GroupOfUnits)");
		wait.clicked.connect(this, "waitClicked()");
		give.clicked.connect(this, "giveClicked()");
		surrender.clicked.connect(this, "surrenderClicked()");
		queue.over.connect(this, "queueOver(int,int)");
		
		log.setReadOnly(true);
		layout.addWidget(log, 3, 1);
		
		layout.addLayout(layoutButtons, 4, 0, 1, 2);
		
		layoutButtons.setSpacing(0);
		layoutButtons.addWidget(give);
		layoutButtons.addWidget(wait);
		layoutButtons.addWidget(surrender);
	}
	
	public void setCanWait(boolean enable)
	{
		wait.setEnabled(enable);
	}
	
	public void addLogLine(String line)
	{
		log.setText(line + "\n" + log.toPlainText());
	}
	
	public void queueOver(int r, int c)
	{
		if (p2u != null) {
			core.GroupOfUnits unit = p2u.get(r, c);
			updateStats(unit);
			
			
		}
	}
	
	public void	updateQueue(Queue<core.GroupOfUnits> units)
	{
		int i = 0;
		WidgetMap mapWidget = battle.getMapWidget();
		queue.clearLevel(3);
		queue.clearLevel(4);
		p2u = new Position2Object<core.GroupOfUnits>();
		for (core.GroupOfUnits u: units) {
			queue.getImageStack(i/3, i%3).setLayer(3, new gui.WidgetImage(u.type.file));
			queue.getImageStack(i/3, i%3).setLayer(4, new gui.WidgetLabel(""+u.getNumber(), u.getOwner().getColor()));
			p2u.put(i/3, i%3, u);
			++i;
		}
		updateStats(units.peek());
	}
	
	public void waitClicked()
	{
		battle.waitUnit();
	}
	
	public void giveClicked()
	{
		battle.giveMove();
	}
	
	public void surrenderClicked()
	{
		battle.surrender();
	}
	
	public void updateStats(core.GroupOfUnits unit)
	{
		if (unit != null) {
			uname.setText(unit.type.name);
			uattack.setText(""+unit.getAttack());
			udefense.setText(""+unit.getDefense());
			uspeed.setText(""+unit.getSpeed());
			uhp.setText(""+unit.getHP());
			ushooting.setText(unit.type.shooting ? "Tak" : "Nie");
			umagic.setText(unit.type.magic ? "Tak" : "Nie");
			uspecial.setText("Opis" + unit.type.name);
			
		}
	}
	
	public static void main(String[] args)
	{
		List<core.GroupOfUnits> u1 = new ArrayList<core.GroupOfUnits>();
		List<core.GroupOfUnits> u2 = new ArrayList<core.GroupOfUnits>();
		u1.add(new core.GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new core.GroupOfUnits(core.UnitType.KUSZNIK, null, 10));
		u2.add(new core.GroupOfUnits(core.UnitType.MAG_OGNIA, null, 4));
		u2.add(new core.GroupOfUnits(core.UnitType.KUSZNIK, null, 4));
		
		Hero h1 = new Hero("Bohater 1", CastleType.HUMAN_CASTLE);
		Hero h2 = new Hero("Bohater 2", CastleType.HUMAN_CASTLE);
		h1.setColor(Color.RED);
		h2.setColor(Color.BLUE);
		h1.setUnits(u1);
		h2.setUnits(u2);
		
		Player p1 = new PlayerHuman("Gracz 1", Color.RED);
		Player p2 = new PlayerHuman("Gracz 2", Color.BLUE);
		
		Battle b = new Battle(p1, p2, h1, h2, Terrain.GRASS, null);
		
		QApplication.initialize(args);
		b.createMapWidget();
		b.populateMapWidget();
		//scroll.setWidget(b.getMapWidget());
		
		WindowBattle bat = new WindowBattle(b, p1, p2, h1, h2);
		b.updateQueue.connect(bat, "updateQueue(Queue)");
		b.log.connect(bat, "addLogLine(String)");
		bat.show();
		bat.setFixedSize(1000, 700);
		b.start();
		
		QApplication.exec();
	}
}


