package core;

import java.util.*;

public class MissionC2v1 extends MissionC2
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
		return "Akt II. Na granicy";
	}
	
	@Override
	public String getDescription()
	{
		return "Wieści o oswobodzeniu przez ciebie królestwa rozchodzą się bardzo szybko. Coraz więcej uchodźców wraca do twojego królestwa, by pomóc Ci w obronie ziem. Niestety informacja o twoim zwycięstwie dotarła także do władcy demonów . Twoi zwiadowcy właśnie powiadomili Cię, że Gothmog wysłał swojego najlepszego generała do fortecy przy granicy północno-zachodniej. Jego zadaniem jest ponowne pokonanie ludzi i wzięcie Ciebie do niewoli. Decydujesz, więc wysłać wiadomość do swoich sojuszników elfów z prośbą o pomoc. Niestety odległość pomiędzy królestwami jest bardzo duża, dlatego nie możesz się spodziewać pomocy szybciej niż za 30 dni. Dlatego zbierasz całą swoją armię nad granicę w celu spowolnienia działań wroga i dania czasu na ucieczkę w głąb kraju Twoim poddanym.";
	}

	@Override
	public String getObjective()
	{
		return "Misję tą można skończyć na dwa sposoby. Pierwszym jest powstrzymywanie wrogich wojsk przez 30 dni, dopóki pomoc od elfów nie nadejdzie. Dodatkowo misję tą można wygrać pokonując wrogą armie i biorąc do niewoli wrogiego generała. W misji tej nie może zginąć twój bohater.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.BLUE;
	}
}
