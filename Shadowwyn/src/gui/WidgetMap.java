package gui;

import com.trolltech.qt.QSignalEmitter.Signal2;

public class WidgetMap extends WidgetStack
{
	public Signal2<Integer, Integer> pressed = new Signal2<Integer, Integer>();
	
	private WidgetStackGrid grid;
	private int rows;
	private int cols;
	private int size;
	
	public WidgetMap(int r, int c, int s)
	{
		super(c*s, 2, 1, 1);
		rows = r;
		cols = c;
		size = s;
		grid = new WidgetStackGrid(r, c, s);
		grid.setObjectName("map");
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
	
	public void addShadow()
	{
		for (int r=0; r<rows; ++r) {
			for (int c=0; c<cols; ++c) {
				grid.getImageStack(r, c).setLayer(4, new gui.WidgetImage("image/markers/shadow.png"));
			}
		}
	}
	
	public void resetShadow()
	{
		for (int r=0; r<rows; ++r) {
			for (int c=0; c<cols; ++c) {
				grid.getImageStack(r, c).widgetAtLayer(4).show();
			}
		}
	}
	
	public void removeShadow(int row, int col)
	{
		grid.getImageStack(row, col).widgetAtLayer(4).hide();
	}
}
