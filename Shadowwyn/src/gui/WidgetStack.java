package gui;

import com.trolltech.qt.gui.*;

public class WidgetStack extends QWidget
{
	private int row;
	private int col;
	protected WidgetHolder[] layer;
	
	public Signal2<Integer, Integer> pressed = new Signal2<Integer, Integer>();

	public WidgetStack(int size, int layers, int r, int c)
	{
		super();
		
		//setCursor(PointingHandCursor);
		row = r;
		col = c;
		layer = new WidgetHolder[layers];
		setMinimumSize(size, size);
		
		for (int i=0; i<layers; ++i) {
			layer[i] = new WidgetHolder(this, size, null);
			layer[i].show();
		}
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
}
