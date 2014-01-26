package core;
import java.util.*;

public enum ResourceType
{
	GOLD("Złoto", 0.01f, 255, 255, 200, "image/resource/gold.png") {
		public int defaultAmount()
		{
			return 1500;
		}
	},
	ORE("Kamień", 1f, 200, 255, 255, "image/resource/ore.png"),
	WOOD("Drewno", 2f, 255, 200, 200, "image/resource/wood.png"),
	ARTIFACT("Artefakt", 1f, 200, 200, 200, "image/objects/artifact.png");
	
	public final String name;
	public final int rgb;
	public final String file;
	public final float exchange;
	
	private ResourceType(String n, float e, int r, int g, int b,  String f)
	{
		name = n;
		rgb = 256*256*r + 256*g + b;
		exchange = e;
		file = f;
	}
	
	public int defaultAmount()
	{
		return 2;
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
		System.out.println("RES FROM RGB " + r +" " + g + " " + b);
		int rgb = 256*256*r + 256*g + b;
		return mapId.get(rgb);
	}
} 
