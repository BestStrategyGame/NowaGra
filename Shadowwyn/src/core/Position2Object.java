package core;
import java.util.*;

public class Position2Object
{
	Map<Integer, WorldMapObject> mapP2O = new HashMap<Integer, WorldMapObject>();
	Map<WorldMapObject, Integer> mapO2P = new HashMap<WorldMapObject, Integer>();
	
	public void put(int x, int y, WorldMapObject o)
	{
		mapP2O.put(10000*x+y, o);
		mapO2P.put(o, 10000*x+y);
		//System.out.println("put: "+x+", "+y+", "+o.getName());
		//System.out.println("get: "+x+", "+y+", "+get(x, y));
	}
	
	public WorldMapObject get(int x, int y)
	{
		if (mapP2O == null) {
			return null;
		}
		return mapP2O.get(10000*x+y);
	}
	
	public WorldMapObject get(Point p)
	{
		if (mapP2O == null) {
			return null;
		}
		return mapP2O.get(10000*p.x+p.y);
	}
	
	public Point get(WorldMapObject o)
	{
		if (mapO2P == null) {
			return null;
		}
		Integer point = mapO2P.get(o);
		if (point != null) {
			return new Point(point/10000, point%10000);
		} else {
			return null;
		}
	}
	
	public void remove(int x, int y)
	{
		System.out.println("remove");
		WorldMapObject o = get(x, y);
		mapP2O.remove(10000*x+y);
		mapO2P.remove(o);
	}
	
	public void remove(WorldMapObject o)
	{
		System.out.println("remove");
		Point p = get(o);
		mapP2O.remove(p);
		mapO2P.remove(o);
	}
	
	public Collection<WorldMapObject> objects()
	{
		return mapP2O.values();
	}
	
	public Set<Integer> points()
	{
		return mapP2O.keySet();
	}
}
