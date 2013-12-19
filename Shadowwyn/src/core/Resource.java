package core;
public class Resource implements WorldMapObject
{
	private ResourceType type;
	private int amount;
	
	public Resource(ResourceType t, int a)
	{
		type = t;
		amount = a;
	}
	
	public String getName()
	{
		return type.name;
	}
	
	public boolean stand(Hero hero, Player player)
	{
		System.out.println("resource stand");
		player.addResource(type, amount);
		return true;
	}
	
	public boolean standNextTo(Hero hero, Player player)
	{
		return false;
	}
	
	public int stackLevel()
	{
		return 1;
	}
	
	public ResourceType getType()
	{
		return type;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public void dailyBonus(Player player)
	{
	}
	
	public void weeklyBonus(Player player)
	{
	}

	@Override
	public int willingnessToMoveHere(Hero hero, Player player, int distance,
			int day) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getImageFile() {
		return type.file;
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
