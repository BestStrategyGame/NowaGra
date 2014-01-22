package core;
import java.util.*;

public enum UnitType
{
	DUMMY(0, "Dummy", 1, 1, 1, 1, false ,false, new Cost(0, 0, 0), ""),
	WOJAK(1, "Wojak", 1, 4, 5, 3,  false, false, new Cost(50, 0, 0), "image/units/human/tier1.png") {
		public float damageRatio(UnitType target)
		{
			// if coś tam...
			return 1.2f;
		}
	},
	KUSZNIK(2, "Kusznik", 2, 4, 5, 4, true, false, new Cost(190, 0, 0), "image/units/human/tier2.png"),
	MAG_OGNIA(3, "Mag Ognia", 3, 4, 5, 5, true, true, new Cost(440, 0, 0), "image/units/human/tier3.png"),
	RYCERZ_MROKU(4, "Rycerz Mroku", 4, 4, 5, 3, false, false, new Cost(800, 0, 0), "image/units/human/tier4.png");
	
	public final int id;
	public final String name;
	
	public CastleType castle = null;
	public final int level;
	public final int attack;
	public final int defense;
	public final int speed;
	public final boolean shooting;
	public final boolean magic;
	public final Cost cost;
	public final String file;
	
	private UnitType(int i, String n, int l, int a, int d, int s, boolean sh, boolean mg, Cost co, String f)
	{
		id = i;
		name = n;
		level = l;
		attack = a;
		defense = d;
		speed = s;
		shooting = sh;
		magic = mg;
		cost = co;
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
	
	public float damageRatio(UnitType target)
	{
		return 1f;
	}
	
	public static UnitType getTier(int tier, CastleType type)
	{
		for (UnitType i: UnitType.values()) {
			System.out.println ("unit "+i.level+", "+i.castle);
			if (i.level == tier && i.castle == type) {
				return i;
			}
		}
		return null;
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