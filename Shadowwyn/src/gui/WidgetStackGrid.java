package gui;

import com.trolltech.qt.gui.*;

public class WidgetStackGrid extends QWidget
{
	private QGridLayout layout = new QGridLayout();
	private WidgetStack[][] stack;
	private int width;
	private int height;
	
	public Signal2<Integer, Integer> pressed = new Signal2<Integer, Integer>();
	public Signal2<Integer, Integer> over = new Signal2<Integer, Integer>();
	
	public WidgetStackGrid(int w, int h, int size, boolean showGrid)
	{
		super();
		width = w;
		height = h;
		
		//setMaximumSize(width*size, height*size);
		//setMinimumSize(width*size, height*size);
		layout.setSpacing(showGrid ? 1 : 0);
		layout.setMargin(0);
		setLayout(layout);
		
		stack = new WidgetStack[height][];
		for (int i=0; i<height; ++i) {
			stack[i] = new WidgetStack[width];
			for (int j=0; j<width; ++j) {
				stack[i][j] = new WidgetStack(size, size, size, 5, i, j);
				stack[i][j].pressed.connect(pressed);
				stack[i][j].over.connect(over);
				layout.addWidget(stack[i][j], i, j);
			}
		}
	}
	
	public WidgetStack getImageStack(int row, int col)
	{
		return stack[row][col];
	}
	
	public void clearLevel(int no)
	{
		for (int i=0; i<height; ++i) {
			for (int j=0; j<width; ++j) {
				stack[i][j].setLayer(no, null);
			}
		}
	}
	
	public void pressed(int row, int col)
	{
		pressed.emit(row, col);
		//System.out.println(row + ", "+col);
	}
}
