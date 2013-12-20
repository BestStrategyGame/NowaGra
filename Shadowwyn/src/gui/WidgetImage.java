package gui;

import java.util.HashMap;
import java.util.Map;

import com.trolltech.qt.gui.*;

public class WidgetImage extends QLabel
{
	public WidgetImage(String src)
	{
		super(src);
		QImage image = new QImage(src);
		setPixmap(QPixmap.fromImage(image));
		image.dispose();
	}
	
	public WidgetImage(String src, int size)
	{
		super(src);
		QImage image = map.get(src);
		if (image == null) {
			image = new QImage(src).scaled(size, size);
			map.put(src, image);
		}
		setPixmap(QPixmap.fromImage(image));
	}
	
	public WidgetImage(String src, int x, int y, int width, int height)
	{
		super(src);
		fragment(src, x, y, width, height);
	}

	public WidgetImage(String src, int row, int col)
	{
		super(src);
		fragment(src, col*64, row*64, 64, 64);
	}
	
	private void fragment(String src, int x, int y, int width, int height)
	{
		QImage image = map.get(src);
		if (image == null) {
			image = new QImage(src);
			map.put(src, image);
		}
		setPixmap(QPixmap.fromImage(image).copy(x, y, width, height));
	}
	
	public void clean()
	{
		pixmap().dispose();
		dispose();
	}
	
	private static final Map<String, QImage> map = new HashMap<String, QImage>();
}
