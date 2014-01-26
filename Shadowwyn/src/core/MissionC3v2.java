package core;

import java.util.*;

public class MissionC3v2 extends MissionC3
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
		return "Akt III. Jeniec";
	}
	
	@Override
	public String getDescription()
	{
		return "Minęły już dwa tygodnie od wspaniałej obrony granicy przez twojego najlepszego generała. Od tego czasu morale twoich wojsk nad granicą rosły i coraz śmielej zapuszczały się na ziemie wroga. W jednej z takich wycieczek twój generał wpadł w pułapkę zastawioną przez ludzi. Został wzięty do niewoli i póki co jest przetrzymywany w stolicy ludzi. Informacje jakie on posiada są bardzo ważne i dlatego decydujesz się na jak najszybsze odbicie go. Twój szpieg poinformował cię, że ludziom udało się już wyciągnąć od niego miejsce przetrzymywania królowej ludzi. W tym momencie twój generał jest przewożony nad granicę, gdzie ma wskazać najlepszemu przyjacielowi króla ludzi miejsce, w którym więziona jest królowa. Decydujesz się na przeniesienie części swoich wojsk do wieży przy granicy, by mieć możliwość łatwego wypadu w celu odbicia generała.";
	}

	@Override
	public String getObjective()
	{
		return "Twoim zadaniem jest odbicie generała z rąk ludzi zanim zdradzi on więcej tajnych informacji. W misji tej twój bohater nie może zginąć.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.RED;
	}
}
