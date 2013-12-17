package core;
import java.util.*;

public enum ResourceType
{
	GOLD("Złoto", 0, 0, 0, "image/resource/gold.png"),
	ORE("Ruda", 0, 0, 0, "image/resource/ore.png"),
	WOOD("Drewno", 0, 0, 0, "image/resource/wood.png");
	
	public final String name;
	public final int rgb;
	public final String file;
	
	private ResourceType(String n, int r, int g, int b, String f)
	{
		name = n;
		rgb = 256*256*r + 256*g + b;
		file = f;
	}
	
	private static final Map<Integer, ResourceType> mapId = new HashMap<Integer, ResourceType>();
	
	static
	{
		for (ResourceType i: ResourceType.values()) {
			mapId.put(i.rgb, i);
		}
	}
	
	public static ResourceType fromRGB(int r, int g, int b)
	{
		int rgb = 256*256*r + 256*g + b;
		return mapId.get(rgb);
	}
} 
