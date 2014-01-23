package gui;

import java.util.*;

import com.trolltech.qt.core.Qt.ConnectionType;
import com.trolltech.qt.gui.*;

import core.Battle;
import core.CastleType;
import core.Color;
import core.GroupOfUnits;
import core.PlayerHuman;
import core.Position2Object;
import core.Terrain;

public class WindowFast extends QWidget
{
	private static WindowFast LAST_INSTACE;
	public static WindowFast getLastInstance() {
		return LAST_INSTACE;
	}
	
	private QPushButton start = new QPushButton("Rozpocznij bitwę");
	private QPushButton back = new QPushButton("Wróć do menu głównego");
	
	private QFormLayout layout = new QFormLayout();
	private QFrame options = new QFrame();
	private QLineEdit nameb = new QLineEdit(System.getProperty("user.name"));
	private QLineEdit namer = new QLineEdit();
	private QComboBox unit = new QComboBox();
	private QCheckBox ai = new QCheckBox("TAK");
	private QSpinBox num = new QSpinBox();
	private QPushButton addb = new QPushButton("Dodaj do\nniebieskiego");
	private QPushButton addr = new QPushButton("Dodaj do\nczerwonego");
	private WidgetStackGrid listb = new WidgetStackGrid(3, 2, 64, false);
	private WidgetStackGrid listr = new WidgetStackGrid(3, 2, 64, false);
	private core.Position2Object<GroupOfUnits> p2ur = new core.Position2Object<GroupOfUnits>();
	private core.Position2Object<GroupOfUnits> p2ub = new core.Position2Object<GroupOfUnits>();
	private core.Hero herob = new core.Hero("", CastleType.HUMAN_CASTLE);
	private core.Hero heror = new core.Hero("", CastleType.HUMAN_CASTLE);
	
	private core.Hero herobClone;
	private core.Hero herorClone;
	
	private WidgetImage background = new WidgetImage("image/menu/fast.png");
	private WidgetImage logo = new WidgetImage("image/menu/logo.png");
	
	public WindowFast()
	{
		super();
		
		//setStyleSheet(core.Const.menuStyle);
		
		setMinimumSize(1000, 700);
		setMaximumSize(1000, 700);
		
		back.setGeometry(width()-200, height()-50*1-32, 150, 40);
		start.setGeometry(width()-200, height()-50*2-32, 150, 40);
		
		
		background.setParent(this);
		logo.setParent(this);
		back.setParent(this);
		start.setParent(this);
		
		logo.move(250, 50);
		//logo.setStyleSheet("background-color: none");
		
		options.setLayout(layout);
		options.setParent(this);
		options.setGeometry(width()-250, 40, 200, 510);
		//options.setStyleSheet("QFrame { background-color: none; color: white } QTextEdit, QLineEdit, QComboBox, QListView { border: 1px solid white; background-color: #333; color: white; padding: 3px; }");
		
		layout.setMargin(0);
		layout.addRow("Gracz niebieski:", nameb);
		
		heror.setColor(core.Color.RED);
		herob.setColor(core.Color.BLUE);
		
		
		layout.addRow(new QLabel("Jednostki (kliknij aby usunąć): "));
		layout.addRow(listb);
		layout.addRow("Gracz czerwony:", namer);
		layout.addRow("Gra komputer:", ai);
		layout.addRow(new QLabel("Jednostki (kliknij aby usunąć): "));
		layout.addRow(listr);
		nameCheck();
		
		layout.addRow(new QLabel("<b>Dodaj jednostkę do garnizonu</b>"));
		layout.addRow("Typ:", unit);
		layout.addRow("Ilość:", num);
		num.setMinimum(1);
		
		for(core.UnitType u: core.UnitType.values()) {
			if (u != core.UnitType.DUMMY) {
				unit.addItem(u.name, u);
			}
		}
		
		QLayout addButtons = new QHBoxLayout();
		addButtons.addWidget(addb);
		addButtons.addWidget(addr);
		layout.addRow(addButtons);
		
		
		nameb.editingFinished.connect(this, "nameCheck()");
		namer.editingFinished.connect(this, "nameCheck()");
		back.clicked.connect(this, "backClicked()");
		start.clicked.connect(this, "startClicked()");
		addb.clicked.connect(this, "addBlue()");
		addr.clicked.connect(this, "addRed()");
		listb.pressed.connect(this, "clickedBlue(int,int)");
		listr.pressed.connect(this, "clickedRed(int,int)");
		
		LAST_INSTACE = this;
	}
	
	private void add(core.Hero hero, WidgetStackGrid list, core.Position2Object<GroupOfUnits> p2u)
	{
		if (hero.getUnits().size() >= 6 ) {
			QMessageBox.warning(this, "Nie można dodać", "W garnizonie jest już 6 jednostek");
		} else {
			core.UnitType type = (core.UnitType)this.unit.itemData(this.unit.currentIndex());
			
			int i = 0;
			for (core.GroupOfUnits u: hero.getUnits()) {
				if (u.type == type) {
					u.setNumber(u.getNumber() + num.value());
					list.getImageStack(i/3, i%3).setLayer(4, new gui.WidgetLabel(""+u.getNumber(), hero.getColor()));
					return;
				}
				++i;
			}
			i = hero.getUnits().size();
			core.GroupOfUnits u = new core.GroupOfUnits(type, hero, num.value());
			
			hero.getUnits().add(u);
			list.getImageStack(i/3, i%3).setLayer(3, new gui.WidgetImage(type.file));
			list.getImageStack(i/3, i%3).setLayer(4, new gui.WidgetLabel(""+num.value(), hero.getColor()));
			p2u.put(i/3, i%3, u);
		}
	}
	
	private void addBlue()
	{
		add(herob, listb, p2ub);
	}
	
	private void addRed()
	{
		add(heror, listr, p2ur);
	}
	
	private void clicked(core.Hero hero, WidgetStackGrid list, core.Position2Object<GroupOfUnits> p2u, int r, int c)
	{
		if (r > -1) {
			hero.getUnits().remove(p2u.get(r, c));
		}
		
		list.clearLevel(3);
		list.clearLevel(4);
		p2u.clear();
		int i = 0;
		for (core.GroupOfUnits u: hero.getUnits()) {
			list.getImageStack(i/3, i%3).setLayer(3, new gui.WidgetImage(u.type.file));
			list.getImageStack(i/3, i%3).setLayer(4, new gui.WidgetLabel(""+u.getNumber(), u.getOwner().getColor()));
			p2u.put(i/3, i%3, u);
			++i;
		}
	}
	
	private void clickedRed(int r, int c)
	{
		clicked(heror, listr, p2ur, r, c);
	}
	
	private void clickedBlue(int r, int c)
	{
		clicked(herob, listb, p2ub, r, c);
	}
	
	private void startClicked()
	{
		if (herob.getUnits().size() < 1 || heror.getUnits().size() < 1) {
			QMessageBox.warning(gui.WindowStack.getLastInstance(), "Brak jednostek",  "Musisz dodać jakąś jednostkę do garnizonu");
			return;
		}
		
		herobClone = new core.Hero("", CastleType.HUMAN_CASTLE);
		herorClone = new core.Hero("", CastleType.HUMAN_CASTLE);
		herobClone.setColor(core.Color.BLUE);
		herorClone.setColor(core.Color.RED);
		for (core.GroupOfUnits u: herob.getUnits()) {
			herobClone.getUnits().add(new core.GroupOfUnits(u.type, herobClone, u.getNumber()));
		}
		for (core.GroupOfUnits u: heror.getUnits()) {
			herorClone.getUnits().add(new core.GroupOfUnits(u.type, herorClone, u.getNumber()));
		}
		
		core.Mission m = new core.MissionBattle();
		core.Player pb = new core.PlayerHuman(nameb.text(), core.Color.BLUE);
		pb.addHero(herob);
		
		core.Player pr;
		if (ai.isChecked()) {
			pr = new core.PlayerCPU(namer.text(), core.Color.RED);
		} else {
			pr = new core.PlayerHuman(namer.text(), core.Color.RED);
		}
		pr.addHero(heror);
		
		Battle b = new Battle(pb, pr, herob, heror, Terrain.GRASS, null);
		b.createMapWidget();
		b.populateMapWidget();;
		
		WindowBattle bat = new WindowBattle(b, pb, pr, herob, heror);
		b.updateQueue.connect(bat, "updateQueue(Queue)");
		b.log.connect(bat, "addLogLine(String)");
		b.start();
		
		gui.WindowStack ws = gui.WindowStack.getLastInstance();
		ws.push(bat);
		QApplication.processEvents();
	}
	
	private void backClicked()
	{
		WindowStack ws = WindowStack.getLastInstance();
		if (ws != null) {
			ws.pop();
		}
	}
	
	private void nameCheck()
	{
		if (namer.text().isEmpty()) {
			namer.setText("Czerwony");
		}
		if (nameb.text().isEmpty()) {
			nameb.setText("Niebieski");
		}
	}

	public void finished(core.Color color)
	{
		
		heror = herorClone;
		herob = herobClone;
		clicked(heror, listr, p2ur, -1, -1);
		clicked(herob, listb, p2ub, -1, -1);
		QMessageBox.warning(gui.WindowStack.getLastInstance(), "Koniec bitwy",  "Wygrywa gracz "+color.name);
	}
}


