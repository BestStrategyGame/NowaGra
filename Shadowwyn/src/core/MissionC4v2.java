package core;

import java.util.*;

public class MissionC4v2 extends MissionC4
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
		return "Akt IV. Zguba";
	}
	
	@Override
	public String getDescription()
	{
		return "Podczas, gdy Ty byłeś zajęty uwolnieniem generała, twój najbardziej niebezpieczny oddział zbuntował się. Oddział ten składał się z diabłów, które udało ci się przywołać bardzo dawno temu za pomocą czarnej magii. Niestety jak widać nie udało Ci się ich całkowicie podporządkować. Podczas dezercji ukradły one ze stolicy artefakt, który powodował, że żadna wroga armia nie mogła wejść na teren wokół twojej stolicy. Diabły zamierzają wykorzystać twój artefakt to swoich celów jakimi są podporządkowanie sobie mocy artefaktu, przejęcie twoich wojsk, a następnie zdobycie pozostałych krain. Musisz odebrać im ten przedmiot zanim uda im się przekierować moc artefaktu. Na dodatek o całej sprawie dowiedzieli się ludzie, którzy zobaczyli w tej sytuacji okazję do zniszczenia artefaktu i ostatecznego pokonania twoich wojsk. Dlatego w pośpiechu wysyłasz swoją armię na teren, gdzie prawdopodobnie ukryły się diabły z zamiarem jak najszybszego odebrania im artefaktu.";
	}

	@Override
	public String getObjective()
	{
		return "Twoim celem jest zdobycie artefaktu przed ludźmi, a także zanim uda się diabłom podporządkować moc tego artefaktu. W misji tej twój bohater nie może zginąć.";
	}
	
	@Override
	public Color playerColor()
	{
		return Color.RED;
	}
}
