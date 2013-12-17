package core;
public class PlayerCPU extends Player
{
	PlayerCPU(String n, Color c)
	{
		super(n, c);
	}

	@Override
	public boolean startTurn(int day)
	{
		GuiInteraction gui = GuiInteraction.INSTANCE;
		gui.progressStart();
		
		gui.progressEnd();
		Mission.getLastInstance().finnishTurn(this);
		
		return false;
	}
}
