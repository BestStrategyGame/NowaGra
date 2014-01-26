package core;

import java.util.*;

public class MissionC4v1 extends MissionC4
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
		return "Akt IV. Zguba";
	}
	
	@Override
	public String getDescription()
	{
		return "Zaraz po powrocie z niewoli królowej opowiada ci ona treść podsłuchanej rozmowy między dwoma oficerami kiedy była więziona w wieży. Wynika z niej, że oddział składający się ze diabłów – bardzo niebezpiecznych stworzeń, które zostały stworzone za pomocą bardzo złej magii – zbuntował się. Ukradły one władcy demonów artefakt, który nie pozwala wrogim wojskom wejść na zaklęty teren w pobliżu stolicy władcy demonów. Diabły mają zamiar zmienić moc artefaktu w ten sposób, by chronił on ich ziemie. Dzięki temu będą one zdolne pokonać ich byłego władcę, przejąć jego armię, a następnie podbić pozostałe królestwa. Zdobycie i zniszczenie tego artefaktu jest jedynym sposobem na pokonanie demonów dlatego decydujesz się zdobyć go za wszelką cenę";
	}

	@Override
	public String getObjective()
	{
		return "Twoim celem jest zdobycie artefaktu zanim zrobi to władca demonów, a także zanim uda siędiabłom użyć artefaktu dla własnych celów. W misji tej główny bohater nie może zginąć. ";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.BLUE;
	}
}
