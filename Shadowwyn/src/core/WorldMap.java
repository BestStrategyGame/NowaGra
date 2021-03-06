package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.QThread;

public class WorldMap
{
	static class Point implements java.lang.Comparable<Point>
	{
		public final int x;
		public final int y;
		private float distance = 10000000;
		private Point parent = null;
		
		Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		Point(int x, int y, int d)
		{
			this.x = x;
			this.y = y;
			distance = d;
		}
		
		void setParent(Point p)
		{
			parent = p;
		}
		
		void setDistance(float d)
		{
			distance = d;
		}
		
		float getDistance()
		{
			return distance;
		}
		
		Point getParent()
		{
			return parent;
		}
		
		public int compareTo(Point other)
		{
			return (distance > other.distance+0.01) ? 1 : (distance < other.distance-0.01) ? -1 : 0;
		}
	}
	
	
	private static WorldMap LAST_INSTANCE;
	
	public static WorldMap getLastInstance()
	{
		return LAST_INSTANCE;
	}
	
	private Position2Object<WorldMapObject> p2o = new Position2Object<WorldMapObject>();
	private Position2Object<Hero> p2h = new Position2Object<Hero>();
	private Terrain[][] tmap;
	private Point[][] route; 
	//private gui.WidgetStackGrid mapWidget;
	private gui.WidgetMap mapWidget;
	private String background;
	private boolean ready = false;
	QThread thread;

	public WorldMap(String b)
	{
		background = b;
		LAST_INSTANCE = this;
	}
	
	public Terrain[][] getTerrain()
	{
		return tmap;
	}
	
	public Terrain getTerrainAt(int x, int y)
	{
		System.out.println("TERRAIN AT "+x+" "+y+" "+tmap[x][y]);
	
		if (tmap[x][y].ground) {
			return tmap[x][y];
		}
		for (int i=x-1; i<=x+1; ++i) {
			
			for (int j=y-1; j<=y+1; ++j) {
				//System.out.println("TERRAIN AT "+i+" "+j+" "+tmap[i][j]);
				try {
					if (tmap[i][j].ground) {
						return tmap[i][j];
					}
				} catch (Exception e) {}
			}
		}
		
		return Terrain.GRASS;
	}
	
	public void initRoute()
	{
		route = new Point[getHeight()][];
		for (int i=0; i<getHeight(); ++i) {
			route[i] = new Point[getWidth()];
		}
	}
	
	public void loadTerrain(String file)
	{
		int r, g, b;
		int width, height;
		Scanner sc;
		
		try {
			sc = new Scanner(new File(file));
			sc.nextLine();
			sc.nextLine();
			width = sc.nextInt();
			height = sc.nextInt();
			sc.nextInt();
			tmap = new Terrain[height][];
			for (int i=0; i<height; ++i) {
				tmap[i] = new Terrain[width];
				for (int j=0; j<width; ++j) {
					r = sc.nextInt();
					g = sc.nextInt();
					b = sc.nextInt();
					tmap[i][j] = Terrain.fromRGB(r, g, b);
					
					
					ResourceType rt = ResourceType.fromRGB(r, g, b);
					if (rt != null) {
						addObject(j, i, new Resource(rt));
						continue;
					}
					
					MapBuildingType mbt = MapBuildingType.fromRGB(r, g, b);
					if (mbt != null) {
						//System.out.println("("+r+","+g+","+b+") ("+i+","+j+","+mbt.name+") ");
						addObject(j, i, new MapBuilding(mbt, null));
						continue;
					}
					
					CastleType ct = CastleType.fromRGB(r, g, b);
					if (ct != null) {
						addObject(j, i, new Castle(ct, null));
						continue;
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadResources(String file)
	{
		int r, g, b;
		int width, height;
		Scanner sc;
		
		try {
			sc = new Scanner(new File(file));
			sc.nextLine();
			sc.nextLine();
			width = sc.nextInt();
			height = sc.nextInt();
			sc.nextInt();
			for (int i=0; i<height; ++i) {
				for (int j=0; j<width; ++j) {
					r = sc.nextInt();
					g = sc.nextInt();
					b = sc.nextInt();
					
					
					ResourceType rt = ResourceType.fromRGB(r, g, b);
					if (rt != null) {
						System.out.println(rt.name);
						addObject(j, i, new Resource(rt));
						continue;
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void createMapWidget()
	{
		int size = 64;
		
		//mapWidget = new gui.WidgetStackGrid(getWidth(), getHeight(), size);
		
		/*for (int row=0; row<getHeight(); ++row) {
			for (int col=0; col<getWidth(); ++col) {
				//System.out.println(tmap[row][col]);
				//mapWidget.getImageStack(row, col).setLayer(0, new gui.WidgetImage(tmap[row][col].file, size));
				//mapWidget.getImageStack(row, col).setToolTip(tmap[row][col].name);
				//mapWidget.getImageStack(row, col).setLayer(0, new gui.WidgetImage("maps/map2bg.png", row, col));
			}
		}*/
		System.out.println(getHeight());
		System.out.println(getWidth());
		
		System.out.println("CREATE MAP WIDGET: NEW WIDGET MAP");
		QApplication.processEvents();
		mapWidget = new gui.WidgetMap(getHeight(), getWidth(), size, false);
		
		System.out.println("CREATE MAP WIDGET: SET BACKGROUND");
		QApplication.processEvents();
		mapWidget.setBackground(background);
		
		
		System.out.println("CREATE MAP WIDGET: ADD SHADOW");
		QApplication.processEvents();
		mapWidget.addShadow();
	}
	
	public void populateMapWidget()
	{
		WorldMapObject o;
		for (core.Point pos: p2o.points()) {
			QApplication.processEvents();
			o = p2o.get(pos);
			System.out.println(o);
			if (o != null) {
				if (o.getImageFile() != null) {
					System.out.println(o.getImageFile());
					mapWidget.objectAt(pos.y, pos.x).setLayer(o.stackLevel(), new gui.WidgetImage(o.getImageFile(), 64));
					
					System.out.println(o.getTooltip());
				}
				mapWidget.objectAt(pos.y, pos.x).setToolTip(o.getTooltip());
				System.out.println("populate "+pos.x+", "+pos.y+" - "+o+"  ~~~"+o.getColor());
				if (o.getColor() != null) {
					colorChanged(o, o.getColor());
				}
			}
		}
		for (core.Point pos: p2h.points()) {
			QApplication.processEvents();
			o = p2h.get(pos);
			if (o.getImageFile() != null) {
				mapWidget.objectAt(pos.y, pos.x).setLayer(o.stackLevel(), new gui.WidgetImage(o.getImageFile(), 64));
				mapWidget.objectAt(pos.y, pos.x).setLayer(4, new gui.WidgetImage(o.getColor().hfFile));
				mapWidget.objectAt(pos.y, pos.x).setToolTip(o.getTooltip());
				//mapWidget.objectAt(pos.y, pos.x).setToolTip(o.getName());
			}
		}
		ready = true;
		//mapWidget.objectAt(9, 9).setLayer(2, new gui.WidgetLabel("1000", Color.BLUE));
	}
	
	public gui.WidgetMap getMapWidget()
	{
		return mapWidget;
	}
	
	public int getHeight()
	{
		return tmap.length;
	}
	
	public int getWidth()
	{
		return tmap[0].length;
	}
	
	public void addObject(int x, int y, WorldMapObject o)
	{
		System.out.println("add object "+x+" "+y+" "+o);
		p2o.put(x, y, o);
	}
	
	public void addObject(int x, int y, Hero hero)
	{
		System.out.println("add hero");
		p2h.put(x, y, hero);
		hero.setPosition(x, y);
	}
	
	public void addObject(int x, int y, IndependentUnits hero)
	{
		System.out.println("add hero");
		p2h.put(x, y, hero);
		hero.setPosition(x, y);
	}
	
	public void removeHero(Hero hero)
	{
		System.out.println("remove hero "+hero+" "+hero.getX()+" "+hero.getY());
		mapWidget.objectAt(hero.getY(), hero.getX()).setLayer(hero.stackLevel(), null);
		mapWidget.objectAt(hero.getY(), hero.getX()).setLayer(4, null);
		WorldMapObject o = p2o.get(hero.getX(), hero.getY());
		if (o != null) {
			mapWidget.objectAt(hero.getY(), hero.getX()).setToolTip(o.getTooltip());
		} else {
			mapWidget.objectAt(hero.getY(), hero.getX()).setToolTip("");
		}
		p2h.remove(hero.getX(), hero.getY());
		
		Player activePlayer = Mission.getLastInstance().getActivePlayer();
		if (hero.getColor() != activePlayer.getColor()) {
			calculateRoute(activePlayer.getActiveHero());
		}
	}
	
	public core.Point getPositionOfObject(WorldMapObject obj)
	{
		return p2o.get(obj);
	}
	
	public WorldMapObject getObjectAt(int x, int y)
	{
		return p2o.get(x, y);
	}
	
	public Hero getHeroAt(int x, int y)
	{
		return (Hero)p2h.get(x, y);
	}
	
	public void dailyBonus(Player player)
	{
		//for (List<WorldMapObject> s: p2o.objects()) {
			for (WorldMapObject o: p2o.objects()) {
				o.dailyBonus(player);
			}
			for (WorldMapObject o: p2h.objects()) {
				o.dailyBonus(player);
			}
		//}
	}
	
	public void weeklyBonus(Player player)
	{
		//for (List<WorldMapObject> s: p2o.objects()) {
			for (WorldMapObject o: p2o.objects()) {
				o.weeklyBonus(player);
			}
			for (WorldMapObject o: p2h.objects()) {
				o.weeklyBonus(player);
			}
		//}
	}
	
	public void removeShadow(Player player)
	{
		for (Castle c: player.getCastles()) {
			core.Point p = p2o.get(c);
			player.removeShadowRadius(p.x, p.y, 3);
		}
	}
	
	public void colorChanged(WorldMapObject o, Color c)
	{
		core.Point pos = p2o.get(o);
		System.out.println("color changed: "+o+", "+c.name+", "+pos);
		
		//if (ready) {
			try {
				mapWidget.objectAt(pos.y, pos.x).setLayer(0, new gui.WidgetImage(c.mfFile));
			} catch (java.lang.NullPointerException e) {}
		//}
	}
	
	public void moveTo(Hero hero, Player player, int x, int y, int px, int py)
	{
		WorldMapObject standing = p2o.get(hero.getX(), hero.getY());
		Hero object2;
		WorldMapObject object = p2o.get(x, y);
		System.out.println("move to: "+x+", "+y+", "+object);
		
		p2h.remove(hero);
		mapWidget.objectAt(hero.getY(), hero.getX()).setLayer(hero.stackLevel(), null);
		mapWidget.objectAt(hero.getY(), hero.getX()).setLayer(4, null);
		if (standing != null) {
			mapWidget.objectAt(hero.getY(), hero.getX()).setToolTip(standing.getTooltip());
		}
		mapWidget.clearLevel(3);
		
		/*if (object != null && object.isVisitable() && !object.isCollectable()) {
			x = px;
			y = py;
		}*/
		mapWidget.objectAt(y, x).setLayer(4, new gui.WidgetImage(hero.getColor().hfFile));
		mapWidget.objectAt(y, x).setLayer(hero.stackLevel(), new gui.WidgetImage(hero.getImageFile(), 64));
		if (object != null && object.getTooltip() != null) {
			mapWidget.objectAt(y, x).setToolTip(object.getTooltip() +"\n\n"+ hero.getTooltip());
		} else {
			mapWidget.objectAt(y, x).setToolTip(hero.getTooltip());
		}
		addObject(x, y, hero);
		
		
		
		calculateRoute(hero);
		if (player instanceof PlayerHuman) {
			gui.Sound.play("sound/move.wav");
		}
		QApplication.processEvents();
		
		if (px == -1) {
			if (object instanceof Castle) {
				hero.setInCastle((Castle)object);
			}
		}
		
		if (object != null) if (object.stand(hero, player)) {
			p2o.remove(object);
			mapWidget.objectAt(y, x).setLayer(object.stackLevel(), null);
		}
		

		object2 = p2h.get(x-1, y-1);
		if (object2 != null) if (object2.standNextTo(hero, player)) {
			p2h.remove(object2);
			return;
		}
		
		object2 = p2h.get(x-1, y);
		if (object2 != null) if (object2.standNextTo(hero, player)) {
			p2h.remove(object2);
			return;
		}
		
		object2 = p2h.get(x-1, y+1);
		if (object2 != null) if (object2.standNextTo(hero, player)) {
			p2h.remove(object2);
			return;
		}
		
		object2 = p2h.get(x+1, y-1);
		if (object2 != null) if (object2.standNextTo(hero, player)) {
			p2h.remove(object2);
			return;
		}
		
		object2 = p2h.get(x+1, y);
		if (object2 != null) if (object2.standNextTo(hero, player)) {
			p2h.remove(object2);
			return;
		}
		
		object2 = p2h.get(x+1, y+1);
		if (object2 != null) if (object2.standNextTo(hero, player)) {
			p2h.remove(object2);
			return;
		}
		
		object2 = p2h.get(x, y-1);
		if (object2 != null) if (object2.standNextTo(hero, player)) {
			p2h.remove(object2);
			return;
		}
		
		object2 = p2h.get(x, y+1);
		if (object2 != null) if (object2.standNextTo(hero, player)) {
			p2h.remove(object2);
			return;
		}
		
		
		
		
	}
	
	private boolean forbiddenNeighbourhood(Hero hero, int x, int y)
	{
		Hero other;
		for (int i=-1; i<=1; ++i) {
			for (int j=-1; j<=1; ++j) {
				other = (Hero)p2h.get(x+i, y+j);
				if (other != null && other.getColor() != hero.getColor()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private float wages(Hero hero, int fromx, int fromy, int tox, int toy)
	{
		//System.out.println(this+ "  "+tox+" - "+toy+" - "+p2o.get(tox, toy));
		
			WorldMapObject obj = p2o.get(tox, toy);
			WorldMapObject obj2 = p2o.get(fromx, fromy);
			WorldMapObject h2 = p2h.get(tox, toy);
			float plus = 0;
			if (h2 != null) {
				plus = 1000000f;
			}
			if (forbiddenNeighbourhood(hero, tox, toy)) {
				if (obj instanceof Castle) {
					plus = 1000000f;
				} else {
					plus = 500000f;
				}
				
			} /*else*/
			
			float times = 1.0f;
			if (obj != null) {
				if (obj.isVisitable() && !obj.isCollectable() && (toy-fromy) != -1) {
					plus = 1000000f;
				}
			} 
			if (obj2 != null) {
				if (obj2.isVisitable() && !obj2.isCollectable() && (toy-fromy) != 1) {
					//System.out.println("can't leave from top:  "+tox+" - "+toy+"");
					plus = 1000000f;
				}
			}
			
			
			if ((fromx-tox)*(fromy-toy) != 0) {
				times = 1.42f;
			}
		//}
		return (tmap[fromy][fromx].cost + tmap[toy][tox].cost)*0.45f*times + plus;
	}
	
	public void calculateRoute(final Hero hero)
	{
		if (hero == null) {
			for (int y=0; y<getHeight(); ++y) {
				for (int x=0; x<getWidth(); ++x) {
					route[y][x] = new Point(x, y);
				}
			}
		}
		System.out.println("calculate route "+hero.getName());
		
		
		//for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
		//    System.out.println(ste);
		//}

		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		thread = new QThread(new Runnable() {
			@Override
			public void run()
			{
				PriorityQueue<Point> q = new PriorityQueue<Point>();
				for (int y=0; y<getHeight(); ++y) {
					for (int x=0; x<getWidth(); ++x) {
						if (hero.getX() == x && hero.getY() == y) {
							route[y][x] = new Point(x, y, 0);
						} else {
							route[y][x] = new Point(x, y);
						}
						q.add(route[y][x]);
					}
				}
				Point u, v;
				while (!q.isEmpty()) {
					u = q.remove();
					for (int i=-1; i<=1; ++i) {
						for (int j=-1; j<=1; ++j) {
							try {
								v = route[u.y+i][u.x+j];
								if (v.x >=0 && v.x < getWidth() && v.y >= 0 && v.y < getHeight()) {
									float w = wages(hero, u.x, u.y, v.x, v.y);
									if (v.getDistance() > u.getDistance() + w) {
										v.setDistance(u.getDistance() + w);
										v.setParent(u);
										q.add(v);
									}
								}
							} catch (Exception e) {}
						}
					}
				}
				System.out.println("end calculate route");
			}
		});
		thread.start();
	}
	
	public float getDistance(int x, int y)
	{
		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return route[y][x].getDistance();
	}
	
	public Point getRoute(int x, int y)
	{
		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return route[y][x];
	}
	
	public Point whereToGo(Hero hero, Player player, int day)
	{
		int priority = -1000000;
		int newPriority;
		float distance;
		calculateRoute(hero);
		core.Point p;
		core.Point resultPoint = null;
		
		for (WorldMapObject obj: p2o.objects()) {
			p = p2o.get(obj);
			distance = getDistance(p.x, p.y);
			newPriority = obj.willingnessToMoveHere(hero, player, distance, day);
			if (newPriority > 0)
				System.out.println("AI: WTM OBJECT "+obj.getName()+" ~ "+newPriority);
			if (newPriority >  priority) {
				priority = newPriority;
				resultPoint = p;
			}
		}
		for (Hero obj: p2h.objects()) {
			if (obj == hero) {
				continue;
			}
			p = p2h.get(obj);
			System.out.println("!!!" + hero.getName() + " " + obj.getName());
			System.out.flush();
			System.out.println(getRoute(p.x, p.y));
			if (getRoute(p.x, p.y).getParent() == null) {
				continue;
			}
			System.out.println(getRoute(p.x, p.y).getParent());
			distance = getRoute(p.x, p.y).getParent().getDistance()-500000;
			newPriority = obj.willingnessToMoveHere(hero, player, distance, day);
			System.out.println("AI: WTM HERO "+obj.getName()+" ~ "+newPriority+", dist="+distance+", "+p.x+", "+p.y);
			if (newPriority >  priority && newPriority > 0) {
				priority = newPriority;
				resultPoint = p;
			}
		}
		//try {
			Point route = getRoute(resultPoint.x, resultPoint.y);
			try {
				if (!p2o.get(route.x, route.y).isVisitable()) {
					route = route.getParent();
				}
			} catch (NullPointerException e) {
				//return null;
			}
			return route;
		//} catch (NullPointerException e) {
			//return null;
		//}
	}

	public boolean checkDangerFor(Castle castle, Hero dummy)
	{
		int strength = 0;
		if (castle.getGarission() != null) {
			strength += castle.getGarission().getStrenght();
		}
		if (castle.getHero() != null) {
			strength += castle.getHero().getStrenght();
		}
		
		for (Hero h: p2h.objects()) {
			if (h.getStrenght() > strength && h.getMaxMovePoints() < getDistance(dummy.getX(), dummy.getY())) {
				return true;
			}
		}
		return false;
	}
}