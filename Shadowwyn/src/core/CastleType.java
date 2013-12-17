package core;
 import java.util.*;
 
 public enum CastleType
 {
	ZAMEK_LUDZI(1, "Zamek Ludzi", 0, 0, 0),
	DEMONICZNA_NEKROPOLIA(2, "Demoniczna Nekropolia", 119, 0, 0),
	LESNA_TWIERDZA(3, "Leśna Twierdza", 172, 113, 0);
	
	public final int id;
	public final String name;
	public final int rgb;
	public final String file = "image/dummy.png";
	
	private CastleType(int i, String n, int r, int g, int b)
	{
		id = i;
		name = n;
		rgb = 256*256*r + 256*g + b;
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