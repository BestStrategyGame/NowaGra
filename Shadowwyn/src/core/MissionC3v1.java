package core;

import java.util.*;

public class MissionC3v1 extends MissionC3
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
		return "Akt III. Jeniec";
	}
	
	@Override
	public String getDescription()
	{
		return "Dzięki pomocy elfich wojsk udaje ci się zabezpieczyć granice, przegnać armię demonów, a także uwięzić wrogiego generała. Od niego dowiadujesz się, że Meliana, twoja żona, więziona jest w starej wieży zbudowanej jeszcze przed powstaniem twego królestwa. Znajduje się ona na południowy-wschód od twoich ziem. Są to tereny bagniste i bardzo niebezpieczne. Można tam spotkać wiele niebezpiecznych stworzeń, a dodatkowo wieża jest chroniona przez garnizon demonów. Ze względu na sytuację w królestwie - musisz dopilnować wszystkich spraw związanych z odbudową królestwa, obroną granic - nie możesz opuścić zamku, by uwolnić królową. Dlatego zlecasz to zadanie swojemu najlepszemu przyjacielowi Tulkasowi, ma on zabrać ze sobą wrogiego generała, który pokaże mu gdzie dokładnie jest więziona królowa. Wysyłasz pod jego dowództwem(??) część swoich wojsk, a jako jego siedzibę wybierasz zamek przy twojej południowej granicy.";
	}

	@Override
	public String getObjective()
	{
		return "W misji tej wcielasz się w przyjaciela króla. Zadaniem twoim jest zebranie odpowiedniej armii i uwolnienie królowej z rąk wroga. W misji tej nie może zginąć twój bohater.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.BLUE;
	}
}
