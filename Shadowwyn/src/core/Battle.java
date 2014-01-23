package core;

import gui.WindowStack;

import java.util.*;

import javax.print.attribute.standard.Finishings;

import com.trolltech.qt.QThread;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.core.*;
import com.trolltech.qt.core.Qt.CursorShape;

import core.WorldMap.Point;

public class Battle extends QObject
{
	static class Point implements java.lang.Comparable<Point>
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
	
	private Point[][] route; 
	private Position2Object<GroupOfUnits> p2u = new Position2Object<GroupOfUnits>();
	private QThread thread;
	private gui.WidgetMap mapWidget;
	
	private core.Player player1;
	private core.Player player2;
	private int strength1;
	private int strength2;
	private core.Hero hero1;
	private core.Hero hero2;
	private Terrain terrain;
	private Castle castle;
	private boolean gui0 = false;
	private boolean afterMove = false;
	private boolean wait = false;
	private GroupOfUnits currentUnit = null;
	
	public GroupOfUnits getCurrentUnit()
	{
		return currentUnit;
	}

	private QCursor cursor;
	
	PriorityQueue<GroupOfUnits> units = new PriorityQueue<GroupOfUnits>();
	
	public Signal1<GroupOfUnits> mouseOver = new Signal1<GroupOfUnits>();
	public Signal1<Queue<GroupOfUnits>> updateQueue = new Signal1<Queue<GroupOfUnits>>();
	public Signal1<String> log = new Signal1<String>();
	
	private static Battle LAST_INSTANCE;
	
	public static Battle getLastInstance()
	{
		return LAST_INSTANCE;
	}
	
	public Battle(core.Player p1, core.Player p2, core.Hero h1, core.Hero h2, core.Terrain t, Castle c)
	{
		super();
		
		cursor = QApplication.overrideCursor();
		if (p1 instanceof PlayerHuman || p2 instanceof PlayerHuman) {
			QApplication.restoreOverrideCursor();
			QApplication.processEvents();
		}
		
		castle = c;
		player1 = p1;
		player2 = p2;
		hero1 = h1;
		hero2 = h2;
		strength1 = h1.getStrenght();
		strength2 = h2.getStrenght();
		terrain = t;
		
		Collection<GroupOfUnits> us; 
		Iterator<GroupOfUnits> it;
		
		us = h1.getUnits();
		it = us.iterator();
		switch (us.size()) {
			case 1:
				addObjectAndPrepare(0, 4, it.next());
				break;
			case 2:
				addObjectAndPrepare(0, 3, it.next());
				addObjectAndPrepare(0, 6, it.next());
				break;
			case 3:
				addObjectAndPrepare(0, 2, it.next());
				addObjectAndPrepare(0, 4, it.next());
				addObjectAndPrepare(0, 6, it.next());
				break;
			case 4:
				addObjectAndPrepare(0, 1, it.next());
				addObjectAndPrepare(0, 3, it.next());
				addObjectAndPrepare(0, 5, it.next());
				addObjectAndPrepare(0, 7, it.next());
				break;
			case 5:
				addObjectAndPrepare(0, 1, it.next());
				addObjectAndPrepare(0, 4, it.next());
				addObjectAndPrepare(0, 8, it.next());
				addObjectAndPrepare(1, 3, it.next());
				addObjectAndPrepare(1, 6, it.next());
				break;
			case 6:
				addObjectAndPrepare(0, 1, it.next());
				addObjectAndPrepare(0, 4, it.next());
				addObjectAndPrepare(0, 5, it.next());
				addObjectAndPrepare(0, 8, it.next());
				addObjectAndPrepare(1, 3, it.next());
				addObjectAndPrepare(1, 6, it.next());
				break;
			default:
		}
		
		us = h2.getUnits();
		it = us.iterator();
		switch (us.size()) {
			case 1:
				addObjectAndPrepare(getWidth()-1, 4, it.next());
				break;
			case 2:
				addObjectAndPrepare(getWidth()-1, 3, it.next());
				addObjectAndPrepare(getWidth()-1, 6, it.next());
				break;
			case 3:
				addObjectAndPrepare(getWidth()-1, 2, it.next());
				addObjectAndPrepare(getWidth()-1, 4, it.next());
				addObjectAndPrepare(getWidth()-1, 6, it.next());
				break;
			case 4:
				addObjectAndPrepare(getWidth()-1, 1, it.next());
				addObjectAndPrepare(getWidth()-1, 3, it.next());
				addObjectAndPrepare(getWidth()-1, 5, it.next());
				addObjectAndPrepare(getWidth()-1, 7, it.next());
				break;
			case 5:
				addObjectAndPrepare(getWidth()-1, 1, it.next());
				addObjectAndPrepare(getWidth()-1, 4, it.next());
				addObjectAndPrepare(getWidth()-1, 8, it.next());
				addObjectAndPrepare(getWidth()-2, 3, it.next());
				addObjectAndPrepare(getWidth()-2, 5, it.next());
				break;
			case 6:
				addObjectAndPrepare(getWidth()-1, 1, it.next());
				addObjectAndPrepare(getWidth()-1, 4, it.next());
				addObjectAndPrepare(getWidth()-1, 5, it.next());
				addObjectAndPrepare(getWidth()-1, 8, it.next());
				addObjectAndPrepare(getWidth()-2, 3, it.next());
				addObjectAndPrepare(getWidth()-2, 5, it.next());
				break;
			default:
		}
		
		LAST_INSTANCE = this;
		
		initRoute();
	}
	
	public boolean humanPlaying()
	{
		return player1 instanceof PlayerHuman || player2 instanceof PlayerHuman;
	}
		
	private void addObjectAndPrepare(int x, int y, GroupOfUnits u)
	{
		addObject(x, y, u);
		u.prepareToBattle();
		units.add(u);
	}
	
	public void waitUnit() 
	{
		log.emit(currentUnit.type.name+" ("+currentUnit.getOwner().getColor().name+") czeka");
		currentUnit.setWait((byte)10);
		finnishTurn(null);
	}
	
	public void giveMove()
	{
		log.emit(currentUnit.type.name+" ("+currentUnit.getOwner().getColor().name+") oddał ruch");
		finnishTurn(null);
	}
	
	public void surrender()
	{
		log.emit("Gracz "+currentUnit.getOwner().getColor().name+" poddał się");
		for (GroupOfUnits u: currentUnit.getOwner().getUnits()) {
			units.remove(u);
			
		}
		currentUnit = units.peek();
		finishBattle();
	}
	
	public void finnishTurn(Player p)
	{
		mapWidget.clearLevel(1);
		start();
	}
	
	public void finishBattle()
	{
		
		if (player1 instanceof PlayerHuman || player2 instanceof PlayerHuman) {
			
			WindowStack ws = WindowStack.getLastInstance();
			ws.pop();
		}
		
		Mission m = Mission.getLastInstance();
		
		QApplication.setOverrideCursor(cursor);
		QApplication.processEvents();
		
		try {
			m.battleFinished(currentUnit.getOwner().getColor(), player1, player2, hero1, hero2, strength1, strength2, castle);
		} catch (NullPointerException e) {
			//e.printStackTrace();
		}
	}
	
	public void start()
	{
		
		gui.WindowBattle wb = gui.WindowBattle.getLastInstance();
		wb.setCanWait(true);
		if (currentUnit != null) {
			currentUnit.nextTurn();
			units.add(currentUnit);
			
		}
		updateQueue.emit(units);
		GroupOfUnits unit = units.remove();
		
		boolean endOfBattle = true;
		for (GroupOfUnits u: units) {
			System.out.println ("finish? " + u.getOwner() +" "+unit.getOwner());
			if (u.getOwner() != unit.getOwner()) {
				endOfBattle = false;
				break;
			}
		}
		
		System.out.println("end of battle"+endOfBattle);
		
		if (endOfBattle) {
			finishBattle();
			return;
		}
		
		currentUnit = unit;
		unit.setWait((byte)0);
		Player player = unit.getOwner().getColor() == player1.getColor() ? player1 : player2;
		calculateRoute(unit);
		player.battleStartTurn(unit);
		
		for (int r=0; r<getHeight(); ++r) {
			for (int c=0; c<getWidth(); ++c) {
				float d = getDistance(c, r);
				//System.out.println("distance "+r+", "+c+", "+d);
				if (d <= currentUnit.getSpeed()+0.01 && d >= 0.01) {
					//System.out.println(true);
					mapWidget.objectAt(r, c).setLayer(1, new gui.WidgetImage("image/markers/distance.png"));
				} else if (d <= 0.01) {
					mapWidget.objectAt(r, c).setLayer(1, new gui.WidgetImage("image/markers/current.png"));
				}
			}
		}
		
		
		afterMove = false;
		
	}
	
	public void addObject(int x, int y, GroupOfUnits unit)
	{
		System.out.println("add object");
		p2u.put(x, y, unit);
	}
	
	public core.Point getPositionOf(GroupOfUnits unit)
	{
		return p2u.get(unit);
	}
	
	public int getHeight()
	{
		return 10;
	}
	
	public int getWidth()
	{
		return 12;
	}
	
	public void initRoute()
	{
		route = new Point[getHeight()][];
		for (int i=0; i<getHeight(); ++i) {
			route[i] = new Point[getWidth()];
		}
	}
	
	public void mouseOver(int row, int col)
	{
		GroupOfUnits unit = p2u.get(col, row);
		if (unit != null) {
			System.out.println(unit);
			mouseOver.emit(unit);
		}
	}
	
	public void hit(GroupOfUnits target)
	{
		int no = target.getNumber();
		
		if (humanPlaying()) {
			if (currentUnit.type.shooting) {
				gui.Sound.play("sound/shot.wav");
			} else {
				gui.Sound.play("sound/attk.wav");
			}
		}
			
		QApplication.processEvents();
		
		boolean isDead = currentUnit.hit(target);
		String l = currentUnit.type.name+" ("+currentUnit.getOwner().getColor().name+") uderzył "+target.type.name+" i zabił "+(no-target.getNumber())+" jednostek";
		log.emit(l);
		QApplication.processEvents();
		core.Point pos = p2u.get(target);
		if (isDead) {
			mapWidget.objectAt(pos.y, pos.x).setLayer(3, null);
			mapWidget.objectAt(pos.y, pos.x).setLayer(4, null);
			p2u.remove(pos.x, pos.y);
			units.remove(target);
			
			if (humanPlaying()) {
				gui.Sound.play("sound/kill.wav");
			}
			QApplication.processEvents();
			
		} else {
			mapWidget.objectAt(pos.y, pos.x).setLayer(4, new gui.WidgetLabel(""+target.getNumber(), target.getOwner().getColor()));
		}
		
	}
	
	public void clicked(int c, int r)
	{
		GroupOfUnits unit = p2u.get(r, c);
		if (afterMove == false) {
			System.out.println(unit);
			if (unit == null) {
				float d = getDistance(r, c);
				System.out.println("clicked distance "+r+", "+c+", "+d);
				if (d <= currentUnit.getSpeed()+0.01 && d >= 0.01) {
					mapWidget.clearLevel(1);
					moveTo(c, r);
					afterMove = true;
					gui.WindowBattle wb = gui.WindowBattle.getLastInstance();
					wb.setCanWait(false);
					
					boolean hasNeighbour = false;
					for (int x=c-1; x<=c+1; ++x) {
						for (int y=r-1; y<=r+1; ++y) {
							if (x != c || y != r) {
								//System.out.println("   -> "+x+" "+y);
								GroupOfUnits n = p2u.get(x, y);
								if (n != null && n.getOwner() != currentUnit.getOwner()) {
									hasNeighbour = true;
									break;
								}
							}
						}
					}
					if (hasNeighbour == false) {
						finnishTurn(null);
					}
				}
			} else if (unit.getOwner() != currentUnit.getOwner()) {
				if (currentUnit.type.shooting) {
					// ATTACK unit
					core.Point pos = p2u.get(currentUnit);
					if (Math.abs(r-pos.x) <= 1 && Math.abs(c-pos.y) <= 1) {
						hit(unit);
						finnishTurn(null);
					} else {
						boolean hasNeighbour = false;
						for (int x=pos.x-1; x<=pos.x+1; ++x) {
							for (int y=pos.y-1; y<=pos.y+1; ++y) {
								if (x != pos.x || y != pos.y) {
									System.out.println("   -> "+x+" "+y);
									 if (p2u.get(x, y) != null) {
										 hasNeighbour = true;
										 break;
									 }
								}
							}
						}
						if (hasNeighbour == false) {
							hit(unit);
							finnishTurn(null);
						}
					}
				} else {
					Point pos = getRoute(r, c).getParent();
					System.out.println(pos);
					float d = pos.getDistance();
					if (d <= currentUnit.getSpeed()+0.01) {
						mapWidget.clearLevel(1);
						moveTo(pos.y, pos.x);
						System.out.println("come closer and attack");
						// ATTACK unit
						hit(unit);
						finnishTurn(null);
					}
				}
			}
		} else if (unit != null && unit.getOwner() != currentUnit.getOwner()) {
			core.Point pos1 = p2u.get(unit);
			core.Point pos2 = p2u.get(currentUnit);
			if (Math.abs(pos1.x-pos2.x) <= 1 && Math.abs(pos1.y-pos2.y) <= 1) {
				hit(unit);
				finnishTurn(null);
			}
		}
	}
	
	public void moveTo(int c, int r)
	{
		if (humanPlaying()) {
			gui.Sound.play("sound/move.wav");
		}
		log.emit(currentUnit.type.name+" ("+currentUnit.getOwner().getColor().name+") ruszył się na pole ("+(r+1)+", "+(c+1)+")");
		core.Point pos = p2u.get(currentUnit);
		p2u.remove(pos.x, pos.y);
		p2u.put(r, c, currentUnit);
		mapWidget.objectAt(pos.y, pos.x).setLayer(1, null);
		mapWidget.objectAt(pos.y, pos.x).setLayer(3, null);
		mapWidget.objectAt(pos.y, pos.x).setLayer(4, null);
		mapWidget.objectAt(c, r).setLayer(1, new gui.WidgetImage("image/markers/current.png"));
		mapWidget.objectAt(c, r).setLayer(3, new gui.WidgetImage(currentUnit.type.file));
		mapWidget.objectAt(c, r).setLayer(4, new gui.WidgetLabel(""+currentUnit.getNumber(), currentUnit.getOwner().getColor()));
		
		QApplication.processEvents();
	}
	
	public void createMapWidget()
	{
		mapWidget = new gui.WidgetMap(getWidth(), getHeight(), 64, true);
		mapWidget.over.connect(this, "mouseOver(int, int)");
		mapWidget.pressed.connect(this, "clicked(int, int)");
	}
	
	public void populateMapWidget()
	{
		for (int i=0; i<getHeight(); ++i) {
			for (int j=0; j<getWidth(); ++j) {
				mapWidget.objectAt(i, j).setLayer(0, new gui.WidgetImage(terrain.file, 64));
			}
		}
		GroupOfUnits u;
		for (core.Point pos: p2u.points()) {
			u = p2u.get(pos);
			if (u != null) {
				System.out.println(mapWidget.objectAt(pos.y, pos.x));
				mapWidget.objectAt(pos.y, pos.x).setLayer(3, new gui.WidgetImage(u.type.file));
				mapWidget.objectAt(pos.y, pos.x).setLayer(4, new gui.WidgetLabel(""+u.getNumber(), u.getOwner().getColor()));
			}
				//mapWidget.objectAt(pos.y, pos.x).setToolTip(u.getTooltip());
				//mapWidget.objectAt(pos.y, pos.x).setToolTip(o.getName());
		}
	}
	
	public gui.WidgetMap getMapWidget()
	{
		return mapWidget;
	}
	
	private float wages(GroupOfUnits unit, int fromx, int fromy, int tox, int toy)
	{
		GroupOfUnits from = p2u.get(fromx, fromy);
		GroupOfUnits to = p2u.get(tox, toy);
		if (to != null && to != unit) {
			return 500000f;
		}
		if ((fromx-tox)*(fromy-toy) == 0) {
			return 1f;
		} else {
			return 2f;
		}
	}
	
	public void calculateRoute(final GroupOfUnits unit)
	{
		System.out.println("calculate route "+p2u);
		
		thread = new QThread(new Runnable() {
			@Override
			public void run()
			{
				PriorityQueue<Point> q = new PriorityQueue<Point>();
				for (int y=0; y<getHeight(); ++y) {
					for (int x=0; x<getWidth(); ++x) {
						core.Point pos = p2u.get(unit);
						if (pos.x == x && pos.y == y) {
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
									float w = wages(unit, u.x, u.y, v.x, v.y);
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
	

}
