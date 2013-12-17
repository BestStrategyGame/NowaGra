package core;
 import java.util.*;
 
 public enum UnitType
 {
	WOJAK(1, "Wojak", CastleType.ZAMEK_LUDZI, 1, 4, 5, 3, "image/dummy.png");
	
	public final int id;
	public final String name;
	
	public final CastleType castle;
	public final int level;
	public final int attack;
	public final int defense;
	public final int speed;
	public final String file;
	
	private UnitType(int i, String n, CastleType c, int l, int a, int d, int s, String f)
	{
		id = i;
		name = n;
		castle = c;
		level = l;
		attack = a;
		defense = d;
		speed = s;
		file = f;
	}
	
	private static final Map<Integer, UnitType> mapId = new HashMap<Integer, UnitType>();
	private static final Map<String, UnitType> mapName = new HashMap<String, UnitType>();
	
	static
	{
		for (UnitType i: UnitType.values()) {
			mapId.put(i.id, i);
			mapName.put(i.name, i);
		}
	}
	
	public static UnitType fromId(int id)
	{
		return mapId.get(id);
	}
	
	public static UnitType fromName(String name)
	{
		return mapName.get(name);
	}
 }