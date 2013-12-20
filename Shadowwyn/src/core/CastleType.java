package core;
 import java.util.*;
 
 public enum CastleType
 {
	HUMAN_CASTLE(1, "Zamek Ludzi", 0, 0, 0, "image/heroes/human.png"),
	DEMONICAL_NECROPOLY(2, "Demoniczna Nekropolia", 119, 0, 0, "image/heroes/demon.png"),
	FOREST_ROOK(3, "Leśna Twierdza", 172, 113, 0, "image/heroes/forest.png");
	
	public final int id;
	public final String name;
	public final int rgb;
	public final String file;
	
	private CastleType(int i, String n, int r, int g, int b, String f)
	{
		id = i;
		name = n;
		rgb = 256*256*r + 256*g + b;
		file = f;
	}
	
	private static final Map<Integer, CastleType> mapId = new HashMap<Integer, CastleType>();
	private static final Map<String, CastleType> mapName = new HashMap<String, CastleType>();
	private static final Map<Integer, CastleType> mapRgb = new HashMap<Integer, CastleType>();
	
	static
	{
		for (CastleType i: CastleType.values()) {
			mapId.put(i.id, i);
			mapName.put(i.name, i);
			mapRgb.put(i.rgb, i);
		}
	}
	
	public static CastleType fromId(int id)
	{
		return mapId.get(id);
	}
	
	public static CastleType fromName(String name)
	{
		return mapName.get(name);
	}
	
	public static CastleType fromRGB(int r, int g, int b)
	{
		int rgb = 256*256*r + 256*g + b;
		return mapRgb.get(rgb);
	}
 }