package core;
import java.util.*;

public enum MapBuildingType
{
	GOLD_MINE("Kopolania z³ota", 255, 255, 0, "image/building/gold.png"),
	ORE_MINE("Kopolania kamienia", 255, 0, 0, "image/building/ore.png"),
	WOOD_MINE("Tartak", 0, 72, 255, "image/building/wood.png");
	
	public final String name;
	public final int rgb;
	public final String file;
	
	private MapBuildingType(String n, int r, int g, int b, String f)
	{
		name = n;
		rgb = 256*256*r + 256*g + b;
		file = f;
	}
	
	public void dailyBonus(Player player)
	{
		switch (this) {
		case GOLD_MINE:
			player.addResource(ResourceType.GOLD, 1000);
			break;
		case ORE_MINE:
			player.addResource(ResourceType.ORE, 1);
			break;
		case WOOD_MINE:
			player.addResource(ResourceType.WOOD, 2);
			break;
		default:
		}
	}
	
	public void weeklyBonus(Player player)
	{
		switch (this) {
		default:
		}
	}
	
	private static final Map<Integer, MapBuildingType> mapId = new HashMap<Integer, MapBuildingType>();
	
	static
	{
		for (MapBuildingType i: MapBuildingType.values()) {
			mapId.put(i.rgb, i);
		}
	}
	
	public static MapBuildingType fromRGB(int r, int g, int b)
	{
		int rgb = 256*256*r + 256*g + b;
		return mapId.get(rgb);
	}
}