package core;
public enum CastleBuilding
{
	CITY_COUNCIL("Rada miasta", "Generuje dochód 500 zlota/dzień", null, new Cost(1000, 5, 5)),
	TOWN_HALL("Ratusz", "Generuje dochód 1000 zlota/dzień", null, new Cost(1500, 10, 10), CITY_COUNCIL),
	CAPITOL("Kapitol", "Generuje dochód 2000 zlota/dzień", null, new Cost(4000, 20, 20), TOWN_HALL),
	FORT("Fort", "+5 do obrony bohatera podczas oblężenia", null, new Cost(1500, 20, 10)),
	CITADEL("Cytadela", "+10 do obrony bohatera podczas oblężenia", null, new Cost(2500, 5, 15), FORT),
	CASTLE("Zamek", "+20 do obrony bohatera podczas oblężenia", null, new Cost(5000, 5, 25), CITADEL),
	// Zamek ludzi
	HUMAN_CASTLE_TIER1("Wylęgarnia - Wojak", "Pozwala rekrutować jednostki Wojak", null, new Cost(500, 5, 5)) {
		public void weeklyBonus(Player player, Castle castle)
		{
			castle.addAvailableToRecruit(1, 20);
		}
	},
	HUMAN_CASTLE_TIER2("Wylęgarnia - Kusznik", "Pozwala rekrutować jednostki Kusznik", null, new Cost(1500, 0, 10), HUMAN_CASTLE_TIER1) {
		public void weeklyBonus(Player player, Castle castle)
		{
			castle.addAvailableToRecruit(2, 10);
		}
	},
	HUMAN_CASTLE_TIER3("Wylęgarnia - Mag ognia", "Pozwala rekrutować jednostki Mag ognia", null, new Cost(3500, 15, 15), HUMAN_CASTLE_TIER2) {
		public void weeklyBonus(Player player, Castle castle)
		{
			castle.addAvailableToRecruit(3, 5);
		}
	},
	HUMAN_CASTLE_TIER4("Wylęgarnia - Rycerz mrodku", "Pozwala rekrutować jednostki Rycerz mrodku", null, new Cost(4500, 20, 20), HUMAN_CASTLE_TIER3) {
		public void weeklyBonus(Player player, Castle castle)
		{
			castle.addAvailableToRecruit(4, 2);
		}
	};
	
	public final String name;
	public final String description;
	public final CastleType type;
	public final Cost cost;
	public final CastleBuilding[] requires;
	
	private CastleBuilding(String n, String d, CastleType t, Cost c, CastleBuilding... r)
	{
		name = n;
		description = d;
		type = t;
		cost = c;
		requires = r;
	}
	
	public void buyBonus(Player player, Castle castle)
	{
		weeklyBonus(player, castle);
	}
	
	public void dailyBonus(Player player, Castle castle)
	{
	}
	
	public void weeklyBonus(Player player, Castle castle)
	{
	}
}