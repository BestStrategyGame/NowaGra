package gui;

import com.trolltech.qt.gui.*;

public class WidgetMap extends WidgetStack
{
	public Signal2<Integer, Integer> pressed = new Signal2<Integer, Integer>();
	public Signal2<Integer, Integer> over = new Signal2<Integer, Integer>();
	
	private WidgetStackGrid grid;
	private int rows;
	private int cols;
	private int size;
	
	public WidgetMap(int c, int r, int s, boolean showGrid)
	{
		super(showGrid ? c*(s+1)-1 :c*s, showGrid ? r*(s+1)-1 : r*s, s, 2, 1, 1);
		rows = r;
		cols = c;
		size = s;
		grid = new WidgetStackGrid(c, r, s, showGrid);
		grid.setObjectName("map");
		setLayer(1, grid);
		
		grid.pressed.connect(pressed);
		grid.over.connect(over);
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
		grid.clearLevel(level);
	}
	
	public void addShadow()
	{
		for (int r=0; r<rows; ++r) {
			for (int c=0; c<cols; ++c) {
				//grid.getImageStack(r, c).setLayer(5, new gui.WidgetImage("image/markers/shadow.png"));
			}
		}
	}
	
	public void resetShadow()
	{
		for (int r=0; r<rows; ++r) {
			for (int c=0; c<cols; ++c) {
				//grid.getImageStack(r, c).widgetAtLayer(5).show();
			}
		}
	}
	
	public void removeShadow(int row, int col)
	{
		//grid.getImageStack(row, col).widgetAtLayer(5).hide();
	}
}
