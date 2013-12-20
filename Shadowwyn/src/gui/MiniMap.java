package gui;

import java.util.Set;

import com.trolltech.qt.QSignalEmitter.Signal2;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QImage.Format;

import java.util.*;

public class MiniMap extends QFrame
{
	private core.Terrain[][] tmap;
	private core.WorldMap wmap;
	private core.Mission mission;
	
	public Signal2<Integer, Integer> pressed = new Signal2<Integer, Integer>();
	
	public MiniMap(QWidget parent)
	{
		super(parent);
		setMinimumSize(38*5, 36*5);
		wmap = core.WorldMap.getLastInstance();
		if (wmap != null) {
			tmap = wmap.getTerrain();
		}
		mission = core.Mission.getLastInstance();
	}
	
	@Override
	protected void paintEvent(QPaintEvent e)
	{
		Set<core.Point> visible = mission.getActivePlayer().getVisible();
		QPainter painter = new QPainter(this);
		QImage image = new QImage(tmap.length, tmap[0].length, Format.Format_RGB32);
		int rgb;
		
		Set<core.Point> ommit = new HashSet<core.Point>();
		
		for (int i=0; i<tmap.length; ++i) {
			for (int j=0; j<tmap[0].length; ++j) {
				if (ommit.contains(new core.Point(j, i))) {
					continue;
				}
				core.WorldMapObject obj = wmap.getObjectAt(j, i);
				if (!visible.contains(new core.Point(j, i))) {
					image.setPixel(j, i, 0);
					continue;
				} else if (obj != null && obj.getColor() != null) {
					image.setPixel(j, i, obj.getColor().rgb);
				} else {
					image.setPixel(j, i, tmap[i][j].rgb);
				}
				core.Hero hero = wmap.getHeroAt(j, i);
				if (hero != null) {
					//image.setPixel(j, i, hero.getColor().rgb);
					image.setPixel(j+1, i, hero.getColor().rgb);
					image.setPixel(j-1, i, hero.getColor().rgb);
					image.setPixel(j, i+1, hero.getColor().rgb);
					image.setPixel(j, i-1, hero.getColor().rgb);
					ommit.add(new core.Point(j, i+1));
					ommit.add(new core.Point(j+1, i));
				}
			}
		}
		
		//for (core.Point pos: wmap.getH)
		QImage scaled = image.scaled(38*5, 36*5);
		painter.drawImage(0, 0, scaled);
		image.dispose();
		scaled.dispose();
		System.out.println("PAINTER");
	}
	
	@Override
	protected void mousePressEvent(QMouseEvent event)
	{
		pressed.emit(event.y()/5, event.x()/5);
	}
}
