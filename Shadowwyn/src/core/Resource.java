package core;

import gui.WindowStack;

import com.trolltech.qt.gui.QMessageBox;

public class Resource implements WorldMapObject
{
	private ResourceType type;
	private int amount;
	
	public Resource(ResourceType t)
	{
		type = t;
		amount = t.defaultAmount();
	}
	
	public String getName()
	{
		return type.name;
	}
	
	public boolean stand(Hero hero, Player player)
	{
		System.out.println("resource stand");
		if (type == ResourceType.ARTIFACT) {
			Mission.getLastInstance().setWon(hero.getColor());
			if (player instanceof PlayerHuman) {
				QMessageBox.about(WindowStack.getLastInstance(), "Artefakt", "Zdobyłeś poszukiwany artefakt!");
			}
		} else {
			player.addResource(type, amount);
		}
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
	public int willingnessToMoveHere(Hero hero, Player player, float distance,
			int day) {
		float moveRatio = hero.getMovePoints()/distance;
		float result = 0;
		Cost need = player.getBalancedBudget();
		
		if (type == ResourceType.GOLD) {
			result = 1.2f*(moveRatio + Math.min(3, need.gold/amount));
		} else if (type == ResourceType.WOOD) {
			result = 1.0f*(moveRatio + Math.min(3, need.wood/amount));
		} else if (type == ResourceType.ORE) {
			result = 1.0f*(moveRatio + Math.min(3, need.ore/amount));
		}
		return (int)(100*result);
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

	@Override
	public String getTooltip() {
		// TODO Auto-generated method stub
		return null;
	}
}
