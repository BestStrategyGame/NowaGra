package gui;

import java.util.*;
import com.trolltech.qt.gui.*;

public class WidgetUnits extends QWidget
{
	private List<core.GroupOfUnits> units1;
	private List<core.GroupOfUnits> units2;
	private core.Hero hero1;
	private core.Hero hero2;
	
	private QGridLayout layout = new QGridLayout();
	private QListWidget list1 = new QListWidget();
	private QListWidget list2 = new QListWidget();
	private QPushButton left = new QPushButton("<<");
	private QPushButton right = new QPushButton(">>");
	private QPushButton split = new QPushButton("<->");
	
	public WidgetUnits(List<core.GroupOfUnits> u1, List<core.GroupOfUnits> u2, core.Hero h1, core.Hero h2)
	{
		super();
		units1 = u1;
		units2 = u2;
		hero1 = h1;
		hero2 = h2;
		
		if (h2 == null) {
			left.setEnabled(false);
			right.setEnabled(false);
			split.setEnabled(false);
			list2.setEnabled(false);
			h2 = new core.Hero("");
			u2 = new ArrayList<core.GroupOfUnits>();
		}
		
		setLayout(layout);
		
		list1.setMinimumSize(150, 110);
		list2.setMinimumSize(150, 110);
		
		layout.addWidget(new QLabel(h1.getName()), 0, 0);
		layout.addWidget(list1, 1, 0, 4, 1);
		layout.addWidget(new QLabel(h2.getName()), 0, 2);
		layout.addWidget(list2, 1, 2, 4, 2);
		layout.addWidget(left, 1, 1);
		layout.addWidget(right, 2, 1);
		layout.addWidget(split, 3, 1);
		
		for (core.GroupOfUnits u: u1) {
			if (u != null) {
				list1.addItem(u.type.name + "("+u.getNumber()+")");
			}
		}
		for (core.GroupOfUnits u: u2) {
			if (u != null) {
				list2.addItem(u.type.name + " ("+u.getNumber()+")");
			}
		}
		
		list1.setCurrentRow(0);
		list2.setCurrentRow(0);
		
		left.clicked.connect(this, "moveLeft()");
		right.clicked.connect(this, "moveRight()");
		split.clicked.connect(this, "moveSplit()");
		
		
	}
	
	public void moveLeft()
	{
		if (units1.size() == 6) {
			QMessageBox.warning(this, "Nie mo¿na przenieœæ jednostek", "Bohater mo¿e mieæ maksymalnie 6 jednostek");
		} else if (units2.size() == 0) {
			QMessageBox.warning(this, "Nie mo¿na przenieœæ jednostek", "Bohater nie ma ¿adnych jednostek");
		} else if (list2.currentRow() == -1) {
			QMessageBox.warning(this, "Nie mo¿na przenieœæ jednostek", "Nie wybrano jednostki");
		} else {
			core.GroupOfUnits unit = units2.get(list2.currentRow());
			addUnit(unit, units1, list1);
			units2.remove(list2.currentRow());
			list2.takeItem(list2.currentRow());
			
		}
	}
	
	public void moveRight()
	{
		if (units2.size() == 6) {
			QMessageBox.warning(this, "Nie mo¿na przenieœæ jednostek", "Bohater mo¿e mieæ maksymalnie 6 jednostek");
		} else if (units1.size() == 0) {
			QMessageBox.warning(this, "Nie mo¿na przenieœæ jednostek", "Bohater nie ma ¿adnych jednostek");
		} else if (list1.currentRow() == -1) {
			QMessageBox.warning(this, "Nie mo¿na przenieœæ jednostek", "Nie wybrano jednostki");
		} else {
			core.GroupOfUnits unit = units1.get(list1.currentRow());
			addUnit(unit, units2, list2);
			units1.remove(list1.currentRow());
			list1.takeItem(list1.currentRow());
			//list2.addItem(unit.type.name + " ("+unit.getNumber()+")");
		}
	}
	
	public void moveSplit()
	{
		if (units2.size() == 6) {
			QMessageBox.warning(this, "Nie mo¿na przenieœæ jednostek", "Bohater mo¿e mieæ maksymalnie 6 jednostek");
		} else if (units1.size() == 0) {
			QMessageBox.warning(this, "Nie mo¿na przenieœæ jednostek", "Bohater nie ma ¿adnych jednostek");
		} else if (list1.currentRow() == -1) {
			QMessageBox.warning(this, "Nie mo¿na przenieœæ jednostek", "Nie wybrano jednostki");
		} else {
			core.GroupOfUnits unit = units1.get(list1.currentRow());
			Integer number = QInputDialog.getInteger(this, "Podaj liczbe jednostek do przeniesenia",
						"Liczba jednostek: "+unit.type.name, 1, 1, unit.getNumber(), 1);
			if (number != null) {
				if (number == unit.getNumber()) {
					moveRight();
				} else {
					unit.setNumber(unit.getNumber()-number);
					list1.currentItem().setText(unit.type.name + " ("+unit.getNumber()+")");
					core.GroupOfUnits newUnit = new core.GroupOfUnits(unit.type, null, number);
					addUnit(newUnit, units2, list2);
					//list2.addItem(unit.type.name + " ("+number+")");
				}
			}
		}
	}
	
	public void addUnit(core.GroupOfUnits unit, List<core.GroupOfUnits> list, QListWidget widget)
	{
		boolean found = false;
		int i=0;
		for (core.GroupOfUnits u: list) {
			if (u.type == unit.type) {
				u.setNumber(u.getNumber() + unit.getNumber());
				widget.item(i).setText(u.type.name + " ("+u.getNumber()+")");
				found = true;
				break;
			}
			++i;
		}
		if (!found) {
			list.add(unit);
			widget.addItem(unit.type.name + " ("+unit.getNumber()+")");
		}
	}
	
	public static void main(String[] args)
	{
		List<core.GroupOfUnits> u1 = new ArrayList<core.GroupOfUnits>();
		List<core.GroupOfUnits> u2 = new ArrayList<core.GroupOfUnits>();
		u1.add(new core.GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new core.GroupOfUnits(core.UnitType.RYCERZ, null, 20));
		u2.add(new core.GroupOfUnits(core.UnitType.ANIOL, null, 30));
	
		QApplication.initialize(args);
		WidgetUnits wu = new WidgetUnits(u1, u2, new core.Hero("Bohater"), new core.Hero("Zamek"));
		wu.show();
		QApplication.exec();
	}
	
}
