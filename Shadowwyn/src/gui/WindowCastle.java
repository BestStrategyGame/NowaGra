package gui;

import java.util.*;

import com.trolltech.qt.gui.*;

import core.Hero;

public class WindowCastle extends QWidget
{
	private core.Castle castle;
	private core.Hero hero;
	
	private QGridLayout layout = new QGridLayout();
	
	public WindowCastle(core.Castle c, core.Hero h)
	{
		super();
		
		castle = c;
		hero = h;
		
		setLayout(layout);
		layout.addWidget(new WidgetUnits(h.getUnits(), null, h, null), 0, 0);
	}
	
	public static void main(String[] args)
	{
		List<core.GroupOfUnits> u1 = new ArrayList<core.GroupOfUnits>();
		u1.add(new core.GroupOfUnits(core.UnitType.WOJAK, null, 10));
		u1.add(new core.GroupOfUnits(core.UnitType.RYCERZ, null, 20));
		core.Hero h =  new core.Hero("foo");
		h.setUnits(u1);
		
		QApplication.initialize(args);
		WindowCastle wc =  new WindowCastle(null, h);
		wc.show();
		QApplication.exec();
	}
}
