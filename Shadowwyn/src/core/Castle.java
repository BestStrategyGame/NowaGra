package core;
import java.util.*;

public class Castle implements WorldMapObject
{
	private CastleType type;
	private Color color;
	private Set<CastleBuilding> buildings = new HashSet<CastleBuilding>();
	
	public Castle(CastleType t, Color c)
	{
		type = t;
		color = c;
	}
	
	public String getName()
	{
		return type.name;
	}
	
	public boolean stand(Hero hero, Player player)
	{
		if (hero.getColor() == color) {
			hero.setInCastle();
		}
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
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color c)
	{
		if (WorldMap.getLastInstance() != null) {
			WorldMap.getLastInstance().colorChanged(this, c);
		}
		color = c;
	}
	
	public void addBuilding(CastleBuilding b)
	{
		if (b.type == null || b.type == type) {
			buildings.add(b);
		}
	}
	
	public void dailyBonus(Player player)
	{
		if (color == player.getColor()) {
			for(CastleBuilding b: buildings) {
				b.dailyBonus(player, this);
			}
		}
	}
	
	public void weeklyBonus(Player player)
	{
		if (color == player.getColor()) {
			for(CastleBuilding b: buildings) {
				b.weeklyBonus(player, this);
			}
		}
	}

	@Override
	public int willingnessToMoveHere(Hero hero, Player player, int distance,
			int day) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getImageFile() {
		//return type.file;
		return null;
	}

	@Override
	public boolean isVisitable() {
		return true;
	}

	@Override
	public boolean isCollectable() {
		return false;
	}
}
