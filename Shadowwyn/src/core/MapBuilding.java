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
	public int willingnessToMoveHere(Hero hero, Player player, float distance,
			int day) {
		
		if (color == player.getColor()) {
			return 0;
		} else if (Mission.getLastInstance().isAlly(hero.getColor(), getColor())) {
			return -1;
		}
		
		float moveRatio = hero.getMovePoints()/(distance+1);
		float result = 0;
		Cost need = player.getBalancedBudget();
		
		if (type == MapBuildingType.GOLD_MINE) {
			result = 1.8f*(moveRatio + Math.min(2, need.gold/200));
		} else if (type == MapBuildingType.WOOD_MINE) {
			result = 1.2f*(moveRatio + Math.min(2, need.wood/10));
		} else if (type == MapBuildingType.ORE_MINE) {
			result = 1.2f*(moveRatio + Math.min(2, need.ore/10));
		}
		return (int)(100*result);
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

	@Override
	public String getTooltip() {
		// TODO Auto-generated method stub
		return null;
	}
}