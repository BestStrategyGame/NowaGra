package gui;

import java.util.HashMap;
import java.util.Map;

import com.trolltech.qt.gui.*;

public class WidgetImage extends QLabel
{
	public WidgetImage(String src)
	{
		super(src);
		setPixmap(new QPixmap(src));
	}
	
	public WidgetImage(String src, int size)
	{
		super(src);
		setPixmap(new QPixmap(src));
		/*
		QImage image = map.get(src);
		if (image == null) {
			image = new QImage(src).scaled(size, size);
			map.put(src, image);
		}
		setPixmap(QPixmap.fromImage(image));*/
		
	}

	
	public void clean()
	{
		pixmap().dispose();
		dispose();
	}
	
	//private static final Map<String, QImage> map = new HashMap<String, QImage>();
}
