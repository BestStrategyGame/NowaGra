package core;

import java.util.*;

public class MissionC1v1 extends MissionC1
{

	@Override
	public void variant(String name)
	{
		p1 = new PlayerHuman(name, Color.BLUE);
		p2 = new PlayerCPU("Komputer", Color.RED);
		h1 = new Hero(Names.name(), CastleType.HUMAN_CASTLE);
		h2 = new Hero(Names.name(), CastleType.DEMONICAL_NECROPOLY);
		setMainHero(h1); 
	}
	
	@Override
	public void order() 
	{
		addPlayer(p1);
		addPlayer(p2);
		setActivePlayer(p1);
	}
	
	@Override
	public String getName()
	{
		return "Akt I. Powrót";
	}
	
	@Override
	public String getDescription()
	{
		return "Wracając do królestwa swojego ojca po dalekiej podróży zastajesz spustoszone ziemie. W drodze do zamku spotykasz uciekiniera od którego dowiadujesz się, że na twe ziemie napadł władca demonów Gothmog. Zamordował on twojego ojca, a twoja żona została wzięta do niewoli. Streszcza ci on pokrótce w jaki sposób udało się władcy demonów pokonać twojego ojca, a także obecną sytuację w królestwie. Dzięki temu dowiadujesz się, że Gothmog wrócił do swojego królestwa, zostawiają c tylko niewielką część swojej armii do utrzymania porządku. Postanawiasz, więc odbić swoje królestwo z rąk wroga. Dlatego rozsyłasz część swoich ludzi po krainie, by roznieśli oni wieść o twoim powrocie i rozpoczęciu zbierania armii, by pokonać demony. Za swoją nową siedzibę obierasz opuszczoną fortecę z dawnych lat, gdy jeszcze panował twój pradziad";
	}

	@Override
	public String getObjective()
	{
		return "Twoim celem jest odbicie zamku twojego ojca, a także pokonanie wszystkich wrogich jednostek znajdujących się na twoich ziemiach. Twój bohater nie może zginąć.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.BLUE;
	}
}
