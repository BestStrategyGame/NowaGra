package core;
public enum CastleBuilding
{
	TOWN_HALL("Ratusz", null);
	
	public final String name;
	public final CastleType type;
	public final CastleBuilding[] requires;
	
	private CastleBuilding(String n, CastleType t, CastleBuilding... r)
	{
		name = n;
		type = t;
		requires = r;
	}
	
	public void dailyBonus(Player player, Castle castle)
	{
		switch (this) {
		default:
		}
	}
	
	public void weeklyBonus(Player player, Castle castle)
	{
		switch (this) {
		default:
		}
	}
}