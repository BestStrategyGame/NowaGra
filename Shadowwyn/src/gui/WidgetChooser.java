package gui;

import java.util.*;

import com.trolltech.qt.gui.*;

public class WidgetChooser extends QWidget
{
	private QVBoxLayout layout = new QVBoxLayout();
	private QVBoxLayout layoutOuter = new QVBoxLayout();
	
	private List<Object> objects = new ArrayList<Object>();
	private int selectedNo = -1;
	private boolean checkable;
	
	public WidgetChooser(boolean c)
	{
		super();
		checkable = c;
		setLayout(layoutOuter);
		layoutOuter.addLayout(layout);
		layoutOuter.addStretch();
		layoutOuter.setMargin(0);
		layoutOuter.setSpacing(0);
		layout.setMargin(0);
		layout.setSpacing(0);
		//layout.addStretch(-1);
		//layout.insertSpacerItem(-1,)
		//setWidget(widget);
		layout.addWidget(new QLabel("foo"));
	}
	
	public void addItem(Object obj, String name)
	{
		WidgetChooserButton button;
		if (checkable && selectedNo != -1) {
			button = (WidgetChooserButton)(layout.itemAt(selectedNo).widget());
			button.setChecked(false);
		}
		
		button = new WidgetChooserButton(obj, name, true);
		if (checkable) {
			button.setCheckable(true);
			button.setChecked(true);
		}
		button.selected.connect(this, "selected(Object)");
		selectedNo = layout.count();
		layout.insertWidget(-1, button);
		
		objects.add(obj);
		//button.show();
		System.out.println("addItem"+layout.count());
	}
	
	public void removeSelected()
	{
		layout.takeAt(selectedNo).widget().dispose();
		objects.remove(selectedNo);
		if (checkable) {
			selectedNo = 0;
			((WidgetChooserButton)(layout.itemAt(selectedNo).widget())).setChecked(true);
			selected(objects.get(selectedNo));
		}
		
	}
	
	public void clean()
	{
		objects = new ArrayList<Object>();
		while (0<layout.count()) {
			System.out.println(1111);
			layout.takeAt(0).widget().dispose();
		}
		selectedNo = -1;
	}
	
	private void selected(Object obj)
	{
		System.out.println("?");
		System.out.println(obj);
		System.out.println(selectedNo);
		if (checkable) {
			WidgetChooserButton button = (WidgetChooserButton)(layout.itemAt(selectedNo).widget());
			if (obj != objects.get(selectedNo)) {
				selectedNo = objects.indexOf(obj);
				button.setChecked(false);
			} else {
				button.setChecked(true);
			}
		}
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		QApplication.initialize(args);

		WidgetChooser w = new WidgetChooser(true);
		//w.addItem(2, "dwa");
		//w.clean();
		//w.addItem(3, "trzy");
		//w.addItem(44, "cztery");
		//w.removeSelected();
		
		//w.addItem(55, "piec");
		//w.addItem(66, "szesc");
		//w.removeSelected();
		//w.show();
		
		//world.show();
		QScrollArea scroll = new QScrollArea();
		scroll.setWidgetResizable(true);
		scroll.setWidget(w);
		scroll.show();
		w.addItem(77, "siedem");
		//m.start();
        QApplication.exec();
	}
}
