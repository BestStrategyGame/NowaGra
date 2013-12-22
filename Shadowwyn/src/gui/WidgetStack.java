package gui;

import com.trolltech.qt.gui.*;
import com.trolltech.qt.core.*;
import com.trolltech.qt.core.QEvent.Type;


public class WidgetStack extends QWidget
{
	private int row;
	private int col;
	protected WidgetHolder[] layer;
	
	public Signal2<Integer, Integer> pressed = new Signal2<Integer, Integer>();
	public Signal2<Integer, Integer> over = new Signal2<Integer, Integer>();

	public WidgetStack(int width, int height, int size, int layers, int r, int c)
	{
		super();
		
		//setCursor(PointingHandCursor);
		row = r;
		col = c;
		layer = new WidgetHolder[layers];
		setMinimumSize(width, height);
		
		for (int i=0; i<layers; ++i) {
			layer[i] = new WidgetHolder(this, width, height, null);
			layer[i].setParent(this);
		}
	}
	
	public QWidget widgetAtLayer(int no)
	{
		return layer[no].currentWidget();
	}
		
	public WidgetStack setLayer(int no, QWidget widget)
	{
		layer[no].setWidget(widget);
		return this;
	}
	
	@Override
	protected void mousePressEvent(QMouseEvent event)
	{
		pressed.emit(row, col);
	}
	
	@Override
	public boolean event(QEvent e)
	{
		if (e.type() == Type.Enter) {
			over.emit(row, col);
		}
		return super.event(e);
	}
}
