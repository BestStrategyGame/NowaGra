package core;
import java.util.*;

public enum Terrain {
	GRASS("Trawa", 0, 255, 0, 1.5f, "image/terrain/grass.png"),
	LAVA("Lawa", 164, 164, 164, 2f, "image/terrain/lava.png"),
	SWAMP("Bagna", 7, 255, 71, 2.5f, "image/terrain/swamp.png"),
	PATH("Ścieżka", 95, 95, 44, 1f, "image/terrain/path.png");
	
	
	public final String name;
	public final int rgb;
	public final float cost;
	public final String file;
	//public gui.WidgetImage image;
	
	private Terrain(String n, int r, int g, int b, float c, String f)
	{
		name = n;
		rgb = 256*256*r + 256*g + b;
		cost = c;
		file = f;
		//image = new gui.WidgetImage(f, 64);
	}
	
	private static final Map<Integer, Terrain> mapId = new HashMap<Integer, Terrain>();
	
	static
	{
		for (Terrain i: Terrain.values()) {
			mapId.put(i.rgb, i);
		}
	}
	
	public static Terrain fromRGB(int r, int g, int b)
	{
		int rgb = 256*256*r + 256*g + b;
		return mapId.get(rgb);
	}
}
