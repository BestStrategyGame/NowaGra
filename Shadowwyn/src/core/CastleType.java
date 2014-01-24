package core;
 import java.util.*;
 
 public enum CastleType
 {
	HUMAN_CASTLE(1, "Zamek Ludzi", 0, 0, 0, UnitType.WOJAK, UnitType.KUSZNIK, UnitType.MAG_OGNIA, UnitType.RYCERZ_MROKU, "image/heroes/human.png"),
	DEMONICAL_NECROPOLY(2, "Demoniczna Nekropolia", 119, 0, 0, UnitType.SZKIELET, UnitType.POLNOCNICA, UnitType.WAPIERZ, UnitType.ARCY_DIABEL, "image/heroes/demon.png"),
	FOREST_ROOK(3, "Leśna Twierdza", 172, 113, 0, UnitType.NIMFA, UnitType.ELFI_SKRYTOBOJCA, UnitType.ENT, UnitType.FENIKS, "image/heroes/forest.png");
	
	public final int id;
	public final String name;
	public final int rgb;
	public final UnitType tier1;
	public final UnitType tier2;
	public final UnitType tier3;
	public final UnitType tier4;
	public final String file;
	
	private CastleType(int i, String n, int r, int g, int b, UnitType t1, UnitType t2, UnitType t3, UnitType t4, String f)
	{
		id = i;
		name = n;
		rgb = 256*256*r + 256*g + b;
		t1.castle = this;
		t2.castle = this;
		t3.castle = this;
		t4.castle = this;
		tier1 = t1;
		tier2 = t2;
		tier3 = t3;
		tier4 = t4;
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