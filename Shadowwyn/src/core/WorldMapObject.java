package core;
public interface WorldMapObject
{
	boolean isVisitable();
	boolean isCollectable();
	boolean stand(Hero hero, Player player);
	boolean standNextTo(Hero hero, Player player);
	int stackLevel();
	int willingnessToMoveHere(Hero hero, Player player, int distance, int day);
	void dailyBonus(Player player);
	void weeklyBonus(Player player);
	String getImageFile();
	String getName();
	Color getColor();
}