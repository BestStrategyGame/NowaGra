package gui;

import java.util.*;

import com.trolltech.qt.core.Qt.ConnectionType;
import com.trolltech.qt.gui.*;

public class WindowCampaign extends QWidget
{
	
	private QPushButton start = new QPushButton("Rozpocznij misję");
	private QPushButton back = new QPushButton("Wróć do menu głównego");
	
	private QFormLayout layout = new QFormLayout();
	private QFrame options = new QFrame();
	private QLineEdit name = new QLineEdit(System.getProperty("user.name"));
	private QComboBox mission = new QComboBox();
	private QTextEdit description = new QTextEdit();
	private QTextEdit objective = new QTextEdit();
	
	private WidgetImage background = new WidgetImage("image/menu/campaign.png");
	private WidgetImage logo = new WidgetImage("image/menu/logo.png");
	
	Thread thread;
	
	public WindowCampaign()
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
		options.setGeometry(width()-250, 200, 200, 350);
		//options.setStyleSheet("QFrame { background-color: none; color: white } QTextEdit, QLineEdit, QComboBox, QListView { border: 1px solid white; background-color: #333; color: white; padding: 3px; }");
		
		layout.setMargin(0);
		layout.addRow("Imię:", name);
		layout.addRow("Misja:", mission);
		layout.addRow(new QLabel("Opis misji: "));
		layout.addRow(description);
		layout.addRow(new QLabel("Cel misji: "));
		layout.addRow(objective);
		
		objective.setMaximumHeight(100);
		objective.setReadOnly(true);
		description.setReadOnly(true);
		
		core.Mission m;
		m =  new core.MissionC1();
		mission.addItem(m.getName(), m);
		
		mission.currentIndexChanged.connect(this, "missionChanged(int)");
		name.editingFinished.connect(this, "nameCheck()");
		back.clicked.connect(this, "backClicked()");
		start.clicked.connect(this, "startClicked()");
		
		missionChanged(0);
	}
	
	private void missionChanged(int index)
	{
		core.Mission m = (core.Mission)mission.itemData(index);
		description.setText(m.getDescription());
		objective.setText(m.getObjective());
	}
	
	private void startClicked()
	{
		WindowStack ws = WindowStack.getLastInstance();
		System.out.println("map loaded "+ws+"");
		
		start.hide();
		back.hide();
		options.hide();
				
		QLabel loading = new QLabel("Wczytywanie...");
		loading.setParent(this);
		loading.setStyleSheet("font-size: 20px; qproperty-alignment: AlignCenter");
		loading.setGeometry(width()/2-100, height()/2-100, 200, 200);
		loading.show();
		
		QApplication.processEvents();
		if (ws != null) {
			core.Mission mission = (core.Mission)this.mission.itemData(this.mission.currentIndex());
			mission.init(name.text());
			
			gui.WindowMap window = new gui.WindowMap();
			gui.WidgetMap map = mission.getMapWidget();
			
			mission.startTurn.connect(window, "startTurn(int,Player)");
			mission.updateWindow.connect(window, "updateData(Player)");
			window.heroChanged.connect(mission, "activeHeroChanged(Hero)");
			window.moveClicked.connect(mission, "moveClicked()");
			window.turnClicked.connect(mission, "turnClicked()");
			window.interactWithCastle.connect(mission, "interactWithCastle()");
			window.interactWithHero.connect(mission, "interactWithHero()");
			mission.addHero.connect(window, "addHero(Hero)");
			map.pressed.connect(mission, "clickedOnMap(int,int)");
			window.setMap(map);
			
			mission.start();
			
			ws.push(window);
		}
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
		if (name.text().isEmpty()) {
			name.setText(System.getProperty("user.name"));
		}
	}
	
	public static void main(String[] args)
	{
		QApplication.initialize(args);
		
		WindowCampaign w = new WindowCampaign();
		w.show();
		QApplication.exec();
	}
}


