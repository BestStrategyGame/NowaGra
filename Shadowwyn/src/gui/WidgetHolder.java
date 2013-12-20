package gui;

import com.trolltech.qt.gui.*;

public class WidgetHolder extends QStackedWidget
{

	public WidgetHolder(QWidget parent, int size, QWidget widget)
	{
		super(parent);
		setGeometry(0, 0, size, size);
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
