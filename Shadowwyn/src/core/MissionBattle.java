package core;

import gui.WindowFast;
import gui.WindowStack;

import com.trolltech.qt.gui.QMessageBox;

public class MissionBattle extends Mission {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getObjective() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean endCondition() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean won() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void battleFinished(Color color, Player player1, Player player2, Hero hero1, Hero hero2, int strength1, int strength2)
	{
		WindowFast.getLastInstance().finished(color);
	}

}
