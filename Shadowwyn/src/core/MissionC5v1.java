package core;

import java.util.*;

public class MissionC5v1 extends MissionC5
{

	@Override
	public void variant(String name)
	{
		p1 = new PlayerHuman(name, Color.BLUE);
		p2 = new PlayerCPU("Komputer", Color.GREEN);
		p3 = new PlayerCPU("Komputer 2", Color.RED);
		h1 = new Hero(Names.name(), CastleType.HUMAN_CASTLE);
		h2 = new Hero(Names.name(), CastleType.FOREST_ROOK);
		h3 = new Hero(Names.name(), CastleType.DEMONICAL_NECROPOLY);
		setAlliance(Color.GREEN, Color.RED);
		setMainHero(h1); 
	}
	
	@Override
	public void order() 
	{
		addPlayer(p1);
		addPlayer(p2);
		addPlayer(p3);
		setActivePlayer(p1);
	}
	
	@Override
	public String getName()
	{
		return "Akt V. Na dwa fronty";
	}
	
	@Override
	public String getDescription()
	{
		return "Dzięki zdobytemu artefaktowi twoje wojska w końcu mogą wkroczyć na ziemie w pobliżu siedziby Gothmoga. Niestety podczas, gdy ty szukałeś zaczarowanego przedmiotu, władcy demonów skupił się na innym celu. Udało mu się przekupić króla elfów i namówić go do wspólnej wojny przeciwko tobie. Ich wojska już idą w stronę twoich granic, by podbić twe ziemie. Dlatego w drodze powrotnej do zamku zbierasz wszystkich ludzi zdolnych do walki. Musisz jak najszybciej obstawić granicę z elfami i przygotować się  na wojnę na dwóch frontach. Jedyną szansą na zwycięstwo jest pokonanie obu wrogów.";
	}

	@Override
	public String getObjective()
	{
		return "Misja ta jest ostatnią misją w kampanii. Żeby ją ukończyć musisz pokonać armie demonów i elfów, i całkowicie unicestwić władców obu krain. W misji tej twój bohater nie może zginąć.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.BLUE;
	}
}
