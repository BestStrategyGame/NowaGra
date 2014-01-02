package gui;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.core.Qt.WindowType;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

public class DialogLevelUp extends QFrame
{
	private QGridLayout layout = new QGridLayout();
	
	private QPushButton attack;
	private QPushButton defense;
	private QPushButton speed;
	
	private core.Hero hero;
	
	public DialogLevelUp(core.Hero h, int levels)
	{
		super();
		
		hero = h;
		
		setObjectName("dialog");
		setLayout(layout);
		setWindowFlags(WindowType.Drawer);
		setStyleSheet(core.Const.style);
		layout.setSizeConstraint(SizeConstraint.SetFixedSize);
		
		layout.addWidget(new QLabel("<b>"+h.getName()+"</b> awansował na poziom "+(h.getLevel()-levels)));
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
	
	private boolean wait = true;
	public void exec()
	{
		if (WindowStack.getLastInstance() != null) {
			setParent(WindowStack.getLastInstance());
		}
		move(300, 300);
		show();
		wait = true;
		while (wait) {
			QApplication.processEvents();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void attackClicked()
	{
		wait = false;
		hero.incAttack(1);
		close();
	}
	
	private void defenseClicked()
	{
		wait = false;
		hero.incDefense(1);
		close();
	}
	
	private void speedClicked()
	{
		wait = false;
		hero.incSpeed(1);
		close();
	}
	
	public static void main(String[] args)
	{
		core.Hero h = new core.Hero("Bohater 1", null);
		
		QApplication.initialize(args);
		DialogLevelUp w = new DialogLevelUp(h, 0);
		w.exec();
		w = new DialogLevelUp(h, 0);
		w.exec();
		QApplication.exec();
	}
}
