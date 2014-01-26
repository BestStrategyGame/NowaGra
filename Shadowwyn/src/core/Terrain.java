package core;
import java.util.*;

public enum Terrain {
	GRASS("Trawa", true, 6,118,0, 1.5f, "image/terrain/grass.png"),
	LAVA("Lawa", true, 164, 164, 164, 2f, "image/terrain/lava.jpg"),
	SWAMP("Bagna", true, 140, 130, 80, 2.5f, "image/terrain/swamp.jpg"),
	PATH("Ścieżka", false, 103, 103, 78, 1f, "image/terrain/path.png"),
	MOUNTAINS("Góry", false, 255, 255, 255, 5000000f, "image/objects/mountains.png"),
	TREE("Drzewo", false, 150, 200, 150, 5000000f, "image/objects/tree.png"),
	CASTLE("Castle", false, 70, 70, 70, 5000000f, "image/objects/tree.png"),
	ENTRANCE("Castle entrance", false, 65, 65, 65, 1f, "image/objects/tree.png"),
	OTHER("", false, 66, 66, 66, 1f, "");
	
	
	public final String name;
	public final boolean ground;
	public final int rgb;
	public final float cost;
	public final String file;
	//public gui.WidgetImage image;
	
	private Terrain(String n, boolean gr, int r, int g, int b, float c, String f)
	{
		name = n;
		ground = gr;
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
