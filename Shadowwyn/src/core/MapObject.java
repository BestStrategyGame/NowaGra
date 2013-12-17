package core;
 import java.util.*;
 
 public enum MapObject implements WorldMapObject
 {
	MOUNTAINS("Góry", 205, 205, 205, "image/objects/mountains.png"),
	TREE("Drzewo", 255, 0, 236, "image/objects/tree.png");
	
	public final String name;
	public final int rgb;
	public final String file;
		
	private MapObject(String n, int r, int g, int b, String f)
	{
		name = n;
		rgb = 256*256*r + 256*g + b;
		file = f;
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean stand(Hero hero, Player player)
	{
		return false;
	}
	
	public boolean standNextTo(Hero hero, Player player)
	{
		return false;
	}
	
	public int stackLevel()
	{
		return 1;
	}
	
	public void dailyBonus(Player player)
	{
	}
	
	public void weeklyBonus(Player player)
	{
	}
	

	private static final Map<Integer, MapObject> mapId = new HashMap<Integer, MapObject>();
	
	static
	{
		for (MapObject i: MapObject.values()) {
			mapId.put(i.rgb, i);
		}
	}
	
	public static MapObject fromRGB(int r, int g, int b)
	{
		int rgb = 256*256*r + 256*g + b;
		return mapId.get(rgb);
	}

	@Override
	public int willingnessToMoveHere(Hero hero, Player player, int distance,
			int day) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getImageFile() {
		//return file;
		return null;
	}

	@Override
	public boolean isVisitable() {
		return false;
	}

	@Override
	public boolean isCollectable() {
		return false;
	}
 } 
