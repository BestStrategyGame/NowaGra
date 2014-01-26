package core;

import java.util.*;

public class MissionC5v1 extends MissionC5
{

	@Override
	public void variant(String name)
	{
		p1 = new PlayerHuman(name, Color.BLUE);
		p2 = new PlayerCPU("Komputer", Color.RED);
		p3 = new PlayerCPU("Komputer2", Color.GREEN);
		h1 = new Hero(Names.name(), CastleType.HUMAN_CASTLE);
		h2 = new Hero(Names.name(), CastleType.FOREST_ROOK);
		h3 = new Hero(Names.name(), CastleType.DEMONICAL_NECROPOLY);
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
		return "Akt V. ";
	}
	
	@Override
	public String getDescription()
	{
		return "Zaraz po powrocie z niewoli krĂłlowej opowiada ci ona treĹ›Ä‡ podsĹ‚uchanej rozmowy miÄ™dzy dwoma oficerami kiedy byĹ‚a wiÄ™ziona w wieĹĽy. Wynika z niej, ĹĽe oddziaĹ‚ skĹ‚adajÄ…cy siÄ™ ze diabĹ‚Ăłw â€“ bardzo niebezpiecznych stworzeĹ„, ktĂłre zostaĹ‚y stworzone za pomocÄ… bardzo zĹ‚ej magii â€“ zbuntowaĹ‚ siÄ™. UkradĹ‚y one wĹ‚adcy demonĂłw artefakt, ktĂłry nie pozwala wrogim wojskom wejĹ›Ä‡ na zaklÄ™ty teren w pobliĹĽu stolicy wĹ‚adcy demonĂłw. DiabĹ‚y majÄ… zamiar zmieniÄ‡ moc artefaktu w ten sposĂłb, by chroniĹ‚ on ich ziemie. DziÄ™ki temu bÄ™dÄ… one zdolne pokonaÄ‡ ich byĹ‚ego wĹ‚adcÄ™, przejÄ…Ä‡ jego armiÄ™, a nastÄ™pnie podbiÄ‡ pozostaĹ‚e krĂłlestwa. Zdobycie i zniszczenie tego artefaktu jest jedynym sposobem na pokonanie demonĂłw dlatego decydujesz siÄ™ zdobyÄ‡ go za wszelkÄ… cenÄ™";
	}

	@Override
	public String getObjective()
	{
		return "Twoim celem jest zdobycie artefaktu zanim zrobi to wĹ‚adca demonĂłw, a takĹĽe zanim uda siÄ™diabĹ‚om uĹĽyÄ‡ artefaktu dla wĹ‚asnych celĂłw. W misji tej gĹ‚Ăłwny bohater nie moĹĽe zginÄ…Ä‡. ";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.BLUE;
	}
}
