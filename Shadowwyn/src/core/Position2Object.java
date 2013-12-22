package core;
import java.util.*;

public class Position2Object<T>
{
	Map<Point, T> mapP2O = new HashMap<Point, T>();
	Map<T, Point> mapO2P = new HashMap<T, Point>();
	
	public void put(int x, int y, T o)
	{
		mapP2O.put(new Point(x, y), o);
		mapO2P.put(o, new Point(x, y));
		//System.out.println("put: "+x+", "+y+", "+o.getName());
		//System.out.println("get: "+x+", "+y+", "+get(x, y));
	}
	
	public T get(int x, int y)
	{
		if (mapP2O == null) {
			return null;
		}
		return mapP2O.get(new Point(x, y));
	}
	
	public T get(Point p)
	{
		if (mapP2O == null) {
			return null;
		}
		return mapP2O.get(p);
	}
	
	public Point get(T o)
	{
		if (mapO2P == null) {
			return null;
		}
		return mapO2P.get(o);
	}
	
	public void remove(int x, int y)
	{
		System.out.println("remove");
		T o = get(x, y);
		mapP2O.remove(new Point(x, y));
		mapO2P.remove(o);
	}
	
	public void remove(T o)
	{
		System.out.println("remove");
		Point p = get(o);
		mapP2O.remove(p);
		mapO2P.remove(o);
	}
	
	public Collection<T> objects()
	{
		return mapP2O.values();
	}
	
	public Set<Point> points()
	{
		return mapP2O.keySet();
	}
}
