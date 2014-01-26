package core;

import java.util.*;

public class MissionC5v2 extends MissionC5
{
	@Override
	public void variant(String name)
	{
		p1 = new PlayerCPU("Komputer", Color.BLUE);
		p2 = new PlayerCPU("Komputer 2", Color.GREEN);
		p3 = new PlayerHuman(name, Color.RED);
		h1 = new Hero(Names.name(), CastleType.HUMAN_CASTLE);
		h2 = new Hero(Names.name(), CastleType.FOREST_ROOK);
		h3 = new Hero(Names.name(), CastleType.DEMONICAL_NECROPOLY);
		setAlliance(Color.GREEN, Color.BLUE);
		setMainHero(h3);
		
	}
	
	@Override
	public void order() 
	{
		addPlayer(p3);
		addPlayer(p2);
		addPlayer(p1);
		setActivePlayer(p3);
	}

	@Override
	public String getName()
	{
		return "Akt V. Na dwa fronty";
	}
	
	@Override
	public String getDescription()
	{
		return "Po całej tej przygodzie z diabłami i odebraniu im artefaktu myślałeś, że już nic nie zagraża twoim ziemiom. Niestety myliłeś się. Moc artefaktu jest tak duża, że część jej ciągle spoczywa w miejscu, gdzie przechowywały go diabły. Dzięki temu czarodzieje elfów podjęły próbę odtworzenia całkowitej mocy artefaktu i znalezienia sposobu na jej zniszczenie. Po tygodniach prób i błędów udało im się. Mają oni teraz możliwość wejścia na twoje ziemie i pokonania cię. Decydujesz się, więc na jak najszybsze pokonanie obu wrogów zanim uda im się zebrać odpowiednio dużej armie i połączyć się. W tym celu zwołujesz do stolicy wszystkie jednostki rozsiane po kraju  z zamiarem jak najszybszego wymarszu.";
	}

	@Override
	public String getObjective()
	{
		return "Misja ta jest ostatnią misją w kampanii. Twoim celem jest pokonanie obu wrogów. W misji tej twój bohater nie może zginąć.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.RED;
	}
}
