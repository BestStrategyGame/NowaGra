package core;

import java.util.*;

public class MissionC1v2 extends MissionC1
{
	@Override
	public void variant(String name)
	{
		p1 = new PlayerCPU("Komputer", Color.BLUE);
		p2 = new PlayerHuman(name, Color.RED);
		h1 = new Hero(Names.name(), CastleType.HUMAN_CASTLE);
		h2 = new Hero(Names.name(), CastleType.DEMONICAL_NECROPOLY);
		setMainHero(h2);
		
	}
	
	@Override
	public void order() 
	{
		addPlayer(p2);
		addPlayer(p1);
		setActivePlayer(p2);
	}

	@Override
	public String getName()
	{
		return "Akt I. Podstęp";
	}
	
	@Override
	public String getDescription()
	{
		return "Wykorzystując nieobecność księcia Elessara w królestwie ludzi udało Ci się zdobyć ich twierdzę i pokonać wojska dowodzone przez podstarzałego króla. W głównej bitwie między demonami a ludźmi udało Ci się zabić króla ludzi. Po jego stracie ludzie poddali się, dzięki czemu bez trudu opanowałeś całe królestwo. Niestety o twojej napaści dowiedzieli się sojusznicy ludzi – elfowie. By odwrócić twoją uwagę od ziem ludzi, zaatakowali oni twoje ziemie na granicy północno-zachodniej. Postanawiasz, więc ruszyć z główną armią nad granicę z elfami, by jak najszybciej pokonać wrogów. Na terenach niedawno zdobytych zostawiasz tylko niewielki garnizon, by utrzymywał porządek i nie pozwolił na powstanie buntu.";
	}

	@Override
	public String getObjective()
	{
		return "Celem twojej misji jest pokonanie wojsk nieprzyjaciela i obrona północno-zachodniej granicy. W misji tej nie może zginąć twój bohater.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.RED;
	}
}
