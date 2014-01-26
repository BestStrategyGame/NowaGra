package core;

import java.util.*;

public class MissionC2v2 extends MissionC2
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
		return "Akt II. Na granicy";
	}
	
	@Override
	public String getDescription()
	{
		return "Udało Ci się powstrzymać wojska elfów, a także zaatakować ziemie przygraniczne dzięki czemu elfy musiały cofnąć się w głąb swoich ziem. Niestety manewr elfów się powiódł i udało im się odwrócić twoją uwagę od ziem ludzi. Podczas, gdy Ty byłeś zajęty obroną granicy, do królestwa ludzi przybył ich książę Elessar. Jego powrót podniósł morale ludzi dzięki czemu udało mu się zebrać odpowiednio dużą armię i pokonać twój garnizon pozostawiony na tamtych ziemiach, a także ruszyć z nią w stronę twojej granicy. Tymczasem otrzymałeś raport, że król elfów wysyłał posiłki nad granicę północno-zachodnią. Oznacza to że nie możesz ruszyć na ludzi, by powstrzymać ich przed atakiem. Decydujesz, więc by wysłaswojego najlepszego generała z połową swojej armii do fortecy przy granicy południowo-zachodniej. Ma on za zadanie umocnić granicę i przetrzymać ataki ze strony ludzi dopóki nie przybędą posiłki ze stolicy.";
	}

	@Override
	public String getObjective()
	{
		return "W misji tej wcielasz się w generała armii demonów. Masz za zadanie umocnić granicę i nie pozwolić ludziom na zdobycie terenów przygranicznych zanim przybędą posiłki. By to osiągnąć musisz przetrwać ataki ludzi przez 30 dni.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.RED;
	}
}
