package gui;

import com.trolltech.qt.QSignalEmitter.Signal2;

public class WidgetMap extends WidgetStack
{
	public Signal2<Integer, Integer> pressed = new Signal2<Integer, Integer>();
	
	private WidgetStackGrid grid;
	
	public WidgetMap(int rows, int cols, int size)
	{
		super(rows*size, 2, 1, 1);
		grid = new WidgetStackGrid(rows, cols, size);
		setLayer(1, grid);
		grid.pressed.connect(pressed);
		
	}
	
	public void setBackground(String src)
	{
		setLayer(0, new WidgetImage(src));
	}
	
	public WidgetStack objectAt(int row, int col)
	{
		return grid.getImageStack(row, col);
	}
	
	public void clearLevel(int level)
	{
		grid.clearLevel(3);
	}
}
