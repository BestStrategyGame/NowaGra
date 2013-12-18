package core;
public class MapBuilding implements WorldMapObject
{
	private MapBuildingType type;
	private Color color;
	
	public MapBuilding(MapBuildingType t, Color c)
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
		if (color != hero.getColor()) {
			if (WorldMap.getLastInstance() != null) {
				WorldMap.getLastInstance().colorChanged(this, hero.getColor());
			}
		}
		color = hero.getColor();
		
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
	
	public MapBuildingType getType()
	{
		return type;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color c)
	{
		WorldMap.getLastInstance().colorChanged(this, c);
		color = c;
	}
	
	public void dailyBonus(Player player)
	{
		if (color == player.getColor()) {
			type.dailyBonus(player);
		}
	}
	
	public void weeklyBonus(Player player)
	{
		if (color == player.getColor()) {
			type.weeklyBonus(player);
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