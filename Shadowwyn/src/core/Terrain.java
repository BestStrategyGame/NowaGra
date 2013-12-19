package core;
import java.util.*;

public enum Terrain {
	GRASS("Trawa", 6,118,0, 1.5f, "image/terrain/grass.png"),
	LAVA("Lawa", 164, 164, 164, 2f, "image/terrain/lava.png"),
	SWAMP("Bagna", 7, 255, 71, 2.5f, "image/terrain/swamp.png"),
	PATH("Ścieżka", 103, 103, 78, 1f, "image/terrain/path.png"),
	MOUNTAINS("Góry", 255, 255, 255, 5000000f, "image/objects/mountains.png"),
	TREE("Drzewo", 255, 0, 236, 5000000f, "image/objects/tree.png"),
	CASTLE("Castle", 0, 0, 0, 5000000f, "image/objects/tree.png"),
	ENTRANCE("Castle entrance", 0, 255, 255, 1f, "image/objects/tree.png"),
	OTHER("", 66, 66, 66, 1f, "");
	
	
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
		Terrain t = mapId.get(rgb);
		return t == null ? Terrain.OTHER : t;
	}
}
