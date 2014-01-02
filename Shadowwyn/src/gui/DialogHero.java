package gui;

import java.util.*;

import com.trolltech.qt.core.Qt.WindowType;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QFrame.Shadow;
import com.trolltech.qt.gui.QFrame.Shape;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

public class DialogHero extends QFrame
{
	private core.Hero hero1;
	private core.Hero hero2;
	
	private QLabel statsAttack = new QLabel();
	private QLabel statsDefense = new QLabel();
	private QLabel statsSpeed = new QLabel();
	private QLabel statsLevel = new QLabel();
	private QLabel statsExp = new QLabel();
	private QPushButton close = new QPushButton("Zamknij okno");
	
	private WidgetUnits units;
	
	private QGridLayout layout = new QGridLayout();
	
	public DialogHero(core.Hero h1, core.Hero h2)
	{
		super();
		
		hide();
		setObjectName("dialog");
		
		QFrame line = new QFrame();
		line.setMinimumHeight(1);
		line.setObjectName("hline");
		
		
		
		hero1 = h1;
		if (h2 == null) {
			h2 = new core.Hero("Zwolnij jednostki", null);
			//h2.setUnits(new ArrayList<core.GroupOfUnits>());
		}
		hero2 = h2;
		
		setLayout(layout);
		//setWindowFlags(WindowType.Drawer);
		setStyleSheet(core.Const.style);
		layout.setSizeConstraint(SizeConstraint.SetFixedSize);
		
		QFormLayout statsLayout = new QFormLayout();
		statsLayout.setMargin(10);
		statsLayout.addRow(new QLabel("Statystyki bazowe"));
		statsLayout.addRow("Poziom:", statsLevel);
		statsLayout.addRow("Doświadczenie:", statsExp);
		statsLayout.addRow("Atak:", statsAttack);
		statsLayout.addRow("Obrona:", statsDefense);
		statsLayout.addRow("Szybkość:", statsSpeed);
		
		layout.addWidget(new QLabel("<b>"+h1.getName()+"</b>"), 0, 1);
		layout.addWidget(line, 1, 1, 1, 3);
		layout.addLayout(statsLayout, 2, 1);
		
		units = new WidgetUnits(h1.getUnits(), h2.getUnits(), h1, h2);
		layout.addWidget(new QLabel("<b>Wymiania/usunięcie jednostek</b>"), 3, 1);
		layout.addWidget(units, 4, 1, 3, 3);
		
		QFrame line2 = new QFrame();
		line.setMinimumHeight(1);
		line.setObjectName("hline");
		layout.addWidget(line2, 9, 1, 1, 3);
		
		QHBoxLayout buttonsLayout = new QHBoxLayout();
		buttonsLayout.addWidget(close);
		layout.addLayout(buttonsLayout, 10, 3);
		updateStats();
		
		close.clicked.connect(this, "closeClicked()");
	}
	
	public void exec()
	{
		if (WindowStack.getLastInstance() != null) {
			setParent(WindowStack.getLastInstance());
		}
		move(200, 200);
		show();
	}
	
	private void closeClicked()
	{
		close();
	}
	
	private void updateStats()
	{
		statsLevel.setText(""+hero1.getLevel());
		statsExp.setText(""+hero1.getExp());
		statsAttack.setText(""+hero1.getBaseAttack());
		statsDefense.setText(""+hero1.getBaseDefense());
		statsSpeed.setText(""+hero1.getBaseSpeed());
	}
	
	public static void main(String[] args)
	{
		List<core.GroupOfUnits> u1 = new ArrayList<core.GroupOfUnits>();
		u1.add(new core.GroupOfUnits(core.UnitType.WOJAK, null, 10));
		core.Hero h1 = new core.Hero("Bohater 1", null);
		List<core.GroupOfUnits> u2 = new ArrayList<core.GroupOfUnits>();
		u2.add(new core.GroupOfUnits(core.UnitType.KUSZNIK, null, 20));
		core.Hero h2 = new core.Hero("Bohater 2", null);
		h1.setUnits(u1);
		h2.setUnits(u2);
		
		QApplication.initialize(args);
		DialogHero w = new DialogHero(h1, h2);
		w.exec();
		QApplication.exec();
	}
}
