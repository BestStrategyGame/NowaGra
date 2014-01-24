package core;
public enum CastleBuilding
{
	CITY_COUNCIL(0, "Rada miasta", "Generuje dochód 500 zlota/dzień", null, new Cost(1000, 5, 5)) {
		public void dailyBonus(Player player, Castle castle)
		{
			player.addResource(ResourceType.GOLD, 500);
		}
	},
	TOWN_HALL(0, "Ratusz", "Generuje dochód 1000 zlota/dzień", null, new Cost(1500, 10, 10), CITY_COUNCIL) {
		public void dailyBonus(Player player, Castle castle)
		{
			player.addResource(ResourceType.GOLD, 1000);
		}
	},
	CAPITOL(0, "Kapitol", "Generuje dochód 2000 zlota/dzień", null, new Cost(4000, 20, 20), TOWN_HALL) {
		public void dailyBonus(Player player, Castle castle)
		{
			player.addResource(ResourceType.GOLD, 2000);
		}
	},
	FORT(0, "Fort", "+4 do obrony bohatera podczas oblężenia", null, new Cost(1500, 20, 10)),
	CITADEL(0, "Cytadela", "+8 do obrony bohatera podczas oblężenia", null, new Cost(2500, 5, 15), FORT),
	CASTLE(0, "Zamek", "+12 do obrony bohatera podczas oblężenia", null, new Cost(5000, 5, 25), CITADEL),
	// Zamek ludzi
	HUMAN_CASTLE_TIER1(1, "Baraki", "Pozwala rekrutować jednostki Wojak",  CastleType.HUMAN_CASTLE, new Cost(500, 5, 5)) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(1, 20);
		}
	},
	HUMAN_CASTLE_TIER2(2, "Strażnica", "Pozwala rekrutować jednostki Kusznik",  CastleType.HUMAN_CASTLE, new Cost(1500, 0, 10), HUMAN_CASTLE_TIER1) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(2, 12);
		}
	},
	HUMAN_CASTLE_TIER3(3, "Wieża magów", "Pozwala rekrutować jednostki Mag ognia", CastleType.HUMAN_CASTLE, new Cost(3500, 15, 15), HUMAN_CASTLE_TIER2) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(3, 5);
		}
	},
	HUMAN_CASTLE_TIER4(4, "Arena", "Pozwala rekrutować jednostki Rycerz mrodku", CastleType.HUMAN_CASTLE, new Cost(4500, 20, 20), HUMAN_CASTLE_TIER3) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(4, 3);
		}
	},
	DEMONICAL_NECROPOLY_TIER1(1, "Cmentarz", "Pozwala rekrutować jednostki Szkielet",  CastleType.DEMONICAL_NECROPOLY, new Cost(500, 5, 5)) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(1, 25);
		}
	},
	DEMONICAL_NECROPOLY_TIER2(2, "Przeklęte Pola", "Pozwala rekrutować jednostki Północnica",  CastleType.DEMONICAL_NECROPOLY, new Cost(1500, 0, 10), HUMAN_CASTLE_TIER1) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(2, 15);
		}
	},
	DEMONICAL_NECROPOLY_TIER3(3, "Krypta", "Pozwala rekrutować jednostki Wąpierz", CastleType.DEMONICAL_NECROPOLY, new Cost(3500, 15, 15), HUMAN_CASTLE_TIER2) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(3, 8);
		}
	},
	DEMONICAL_NECROPOLY_TIER4(4, "Piekielna Brama", "Pozwala rekrutować jednostki Arcy Diabeł", CastleType.DEMONICAL_NECROPOLY, new Cost(4500, 20, 20), HUMAN_CASTLE_TIER3) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(4, 2);
		}
	},
	FOREST_ROOK_TIER1(1, "Magiczna polana", "Pozwala rekrutować jednostki Nimfa",  CastleType.FOREST_ROOK, new Cost(500, 5, 5)) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(1, 35);
		}
	},
	FOREST_ROOK_TIER2(2, "Ciemny Las", "Pozwala rekrutować jednostki Elfi Skrótobujca",  CastleType.FOREST_ROOK, new Cost(1500, 0, 10), HUMAN_CASTLE_TIER1) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(2, 17);
		}
	},
	FOREST_ROOK_TIER3(3, "Zaczarowany Sad", "Pozwala rekrutować jednostki Ent", CastleType.FOREST_ROOK, new Cost(3500, 15, 15), HUMAN_CASTLE_TIER2) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(3, 7);
		}
	},
	FOREST_ROOK_TIER4(4, "Ognista góra", "Pozwala rekrutować jednostki Feniks", CastleType.FOREST_ROOK, new Cost(4500, 20, 20), HUMAN_CASTLE_TIER3) {
		public void weeklyBonus(Player player, Castle castle)
		{
			if (!player.getName().equals("dummy"))
			castle.addAvailableToRecruit(4, 3);
		}
	};
	
	public final int tier;
	public final String name;
	public final String description;
	public final CastleType type;
	public final Cost cost;
	public final CastleBuilding[] requires;
	
	private CastleBuilding(int ti, String n, String d, CastleType t, Cost c, CastleBuilding... r)
	{
		tier = ti;
		name = n;
		description = d;
		type = t;
		cost = c;
		requires = r;
	}
	
	public static CastleBuilding getTier(int tier, CastleType castle)
	{
		for (CastleBuilding u: values()) {
			if (u.tier == tier && u.type == castle) {
				return u;
			}
		}
		return null;
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