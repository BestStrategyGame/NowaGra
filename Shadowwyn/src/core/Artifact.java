package core;
import java.util.*;
 
public enum Artifact implements WorldMapObject
{
	DUMMY(0, "Brak", null, 0, 0, 0, "image/dummy.png"),
	MIECZ_PRZEZNACZENIA(1, "Miecz przeznaczenia", ArtifactType.SWORD, 4, 5, 3, "image/dummy.png");
	
	public final int id;
	public final String name;
	
	public final ArtifactType type;
	public final int attack;
	public final int defense;
	public final int speed;
	public final String file;
	
	

	private Artifact(int i, String n, ArtifactType t, int a, int d, int s, String f)
	{
		id = i;
		name = n;
		type = t;
		attack = a;
		defense = d;
		speed = s;
		file = f;
	}
	
	@Override
	public String getImageFile()
	{
		return file;
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
	
	private static final Map<Integer, Artifact> mapId = new HashMap<Integer, Artifact>();
	private static final Map<String, Artifact> mapName = new HashMap<String, Artifact>();
	
	static
	{
		for (Artifact i: Artifact.values()) {
			mapId.put(i.id, i);
			mapName.put(i.name, i);
		}
	}
	
	public static Artifact fromId(int id)
	{
		return mapId.get(id);
	}
	
	public static Artifact fromName(String name)
	{
		return mapName.get(name);
	}

	@Override
	public int willingnessToMoveHere(Hero hero, Player player, int distance,
			int day) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isVisitable() {
		return true;
	}

	@Override
	public boolean isCollectable() {
		return true;
	}
	
	@Override
	public Color getColor() {
		return null;
	}
 } 
