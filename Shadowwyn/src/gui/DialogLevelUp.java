package gui;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.core.Qt.WindowType;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

public class DialogLevelUp extends QDialog
{
	private QGridLayout layout = new QGridLayout();
	
	private QPushButton attack;
	private QPushButton defense;
	private QPushButton speed;
	
	private core.Hero hero;
	
	public DialogLevelUp(core.Hero h)
	{
		super();
		
		hero = h;
		
		setLayout(layout);
		setWindowFlags(WindowType.Drawer);
		setStyleSheet(core.Const.style);
		layout.setSizeConstraint(SizeConstraint.SetFixedSize);
		
		layout.addWidget(new QLabel("<b>"+h.getName()+"</b> awansował na poziom "+h.getLevel()));
		layout.addWidget(new QLabel("Wybierz którą cechę chcesz poprawić:"));
		
		attack = new QPushButton("Atak ("+h.getAttack()+")");
		defense = new QPushButton("Obrona ("+h.getDefense()+")");
		speed = new QPushButton("Szybkość ("+h.getSpeed()+")");
		
		layout.addWidget(attack);
		layout.addWidget(defense);
		layout.addWidget(speed);
		
		attack.clicked.connect(this, "attackClicked()");
		defense.clicked.connect(this, "defenseClicked()");
		speed.clicked.connect(this, "speedClicked()");
	}
	
	private void attackClicked()
	{
		hero.incAttack(1);
		close();
	}
	
	private void defenseClicked()
	{
		hero.incDefense(1);
		close();
	}
	
	private void speedClicked()
	{
		hero.incSpeed(1);
		close();
	}
	
	public static void main(String[] args)
	{
		core.Hero h = new core.Hero("Bohater 1", null);
		
		QApplication.initialize(args);
		DialogLevelUp w = new DialogLevelUp(h);
		w.exec();
		w = new DialogLevelUp(h);
		w.exec();
		QApplication.exec();
	}
}
