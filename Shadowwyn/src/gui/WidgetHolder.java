package gui;

import com.trolltech.qt.gui.*;

public class WidgetHolder extends QStackedWidget
{

	public WidgetHolder(QWidget parent, int width, int height, QWidget widget)
	{
		super(parent);
		//move(0, 0);
		setGeometry(0, 0, width, height);
		if (widget != null) {
			addWidget(widget);
		}
	}
	
	public void setWidget(QWidget widget)
	{
		QWidget w = currentWidget();
		if (w != null) {
			removeWidget(w);
			w.dispose();
		}
		if (widget != null) {
			addWidget(widget);
		}
	}
}
