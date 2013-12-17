package gui;

import com.trolltech.qt.gui.*;

public class WidgetChooserButton extends QPushButton
{
	private Object object;
	public Signal1<Object> selected = new Signal1<Object>();
	
	public WidgetChooserButton(Object o, String text, boolean check)
	{
		super();
		setText(text);
		setCheckable(check);
		object = o;
		clicked.connect(this, "pressed()");
		setMinimumHeight(32);
		//setMinimumWidth(200);
		//setMaximumWidth(200);
	}
	
	private void pressed()
	{
		selected.emit(object);
	}
	
	public Object getObject()
	{
		return object;
	}
}
