package core;
import java.util.*;

public class WorldMap
{
	class Point implements java.lang.Comparable<Point>
	{
		public final int x;
		public final int y;
		private float distance = 1000000;
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
	
	
	static WorldMap LAST_INSTANCE;
	
	static WorldMap getLastInstance()
	{
		return LAST_INSTANCE;
	}
	
	private Position2Object p2o = new Position2Object();
	private Terrain[][] tmap;
	private Point[][] route; 
	//private gui.WidgetStackGrid mapWidget;
	private gui.WidgetMap mapWidget;
	String background;

	public WorldMap(Terrain[][] t, String b)
	{
		tmap = t;
		background = b;
		createMapWidget();
		
		route = new Point[getHeight()][];
		for (int i=0; i<getHeight(); ++i) {
			route[i] = new Point[getWidth()];
		}
		
		LAST_INSTANCE = this;
	}
	
	private void createMapWidget()
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
		mapWidget = new gui.WidgetMap(getHeight(), getWidth(), size);
		mapWidget.setBackground(background);
	}
	
	public void populateMapWidget()
	{
		WorldMapObject o;
		for (int p: p2o.points()) {
			core.Point pos = new core.Point(p);
			o = p2o.get(pos);
			if (o.getImageFile() != null) {
				System.out.println(o.getImageFile());
				mapWidget.objectAt(pos.y, pos.x).setLayer(o.stackLevel(), new gui.WidgetImage(o.getImageFile(), 64));
				//mapWidget.objectAt(pos.y, pos.x).setToolTip(o.getName());
			}
		}
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
		System.out.println("add object "+x+" "+y);
		p2o.put(x, y, o);
	}
	
	public void addObject(int x, int y, Hero hero)
	{
		System.out.println("add hero");
		p2o.put(x, y, hero);
		hero.setPosition(x, y);
	}
	
	public void dailyBonus(Player player)
	{
		for (WorldMapObject o: p2o.objects()) {
			o.dailyBonus(player);
		}
	}
	
	public void weeklyBonus(Player player)
	{
		for (WorldMapObject o: p2o.objects()) {
			o.weeklyBonus(player);
		}
	}
	
	public void colorChanged(WorldMapObject o, Color c)
	{
	}
	
	public void moveTo(Hero hero, Player player, int x, int y, int px, int py)
	{
		WorldMapObject object = p2o.get(x, y);
		p2o.remove(hero.getX(), hero.getY());
		mapWidget.objectAt(hero.getY(), hero.getX()).setLayer(hero.stackLevel(), null);
		mapWidget.clearLevel(3);
		
		if (object != null && object.isVisitable() && !object.isCollectable()) {
			x = px;
			y = py;
		}
		mapWidget.objectAt(y, x).setLayer(hero.stackLevel(), new gui.WidgetImage(hero.getImageFile(), 64));
		addObject(x, y, hero);
		
		if (object != null) if (object.stand(hero, player)) {
			p2o.remove(object);
			mapWidget.objectAt(y, x).setLayer(object.stackLevel(), null);
		}

		object = p2o.get(x-1, y-1);
		if (object != null) if (object.standNextTo(hero, player)) p2o.remove(x-1, y-1);
		
		object = p2o.get(x-1, y);
		if (object != null) if (object.standNextTo(hero, player)) p2o.remove(x-1, y);
		
		object = p2o.get(x-1, y+1);
		if (object != null) if (object.standNextTo(hero, player)) p2o.remove(x-1, y+1);
		
		object = p2o.get(x+1, y-1);
		if (object != null) if (object.standNextTo(hero, player)) p2o.remove(x+1, y-1);
		
		object = p2o.get(x+1, y);
		if (object != null) if (object.standNextTo(hero, player)) p2o.remove(x+1, y);
		
		object = p2o.get(x+1, y+1);
		if (object != null) if (object.standNextTo(hero, player)) p2o.remove(x+1, y+1);
		
		object = p2o.get(x, y-1);
		if (object != null) if (object.standNextTo(hero, player)) p2o.remove(x, y-1);
		
		object = p2o.get(x, y+1);
		if (object != null) if (object.standNextTo(hero, player)) p2o.remove(x, y+1);
		
		calculateRoute(hero);
	}
	
	private boolean forbiddenNeighbourhood(Hero hero, int x, int y)
	{
		WorldMapObject obj;
		Hero other;
		for (int i=-1; i<=1; ++i) {
			for (int j=-1; j<=1; ++j) {
				obj = p2o.get(x+i, y+j);
				if (obj instanceof Hero) {
					other = (Hero)obj;
					if (other.getColor() != hero.getColor()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private float wages(Hero hero, int fromx, int fromy, int tox, int toy)
	{
		//System.out.println(this+ "  "+tox+" - "+toy+" - "+p2o.get(tox, toy));
		
			if (forbiddenNeighbourhood(hero, tox, toy)) {
				return 1000000;
			} else if (p2o.get(tox, toy) != null && !p2o.get(tox, toy).isVisitable()) {
				return 1000000;
			}
			
			float times = 1.0f;
			if ((fromx-tox)*(fromy-toy) != 0) {
				times = 1.42f;
			}
		//}
		return (tmap[fromy][fromx].cost + tmap[toy][tox].cost)*0.45f*times;
	}
	
	public void calculateRoute(Hero hero)
	{
		System.out.println("calculate route "+p2o);
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
	}
	
	public float getDistance(int x, int y)
	{
		return route[y][x].getDistance();
	}
	
	public Point getRoute(int x, int y)
	{
		return route[y][x];
	}
}