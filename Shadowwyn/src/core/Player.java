package core;
import java.util.*;

public abstract class Player
{
	private String name;
	private Color color;
	
	private Map<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>();
	private List<Hero> deadHeroes = new LinkedList<Hero>();
	private List<Hero> heroes = new LinkedList<Hero>();
	private List<Castle> castles = new LinkedList<Castle>();
	private Hero activeHero;
	protected Set<Point> visible = new HashSet<Point>();
	
	public Player(String n, Color c)
	{
		setName(n);
		color = c;
		
		for (ResourceType i: ResourceType.values()) {
			resources.put(i, 0);
		}
	}
	
	private void updateWindow()
	{
		if (Mission.getLastInstance() != null) {
			Mission.getLastInstance().emitUpdateWindowSignal(this);
		}
	}
	
	public Set<Point> getVisible()
	{
		return visible;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void dieHero(Hero hero)
	{
		if (heroes.remove(hero)) {
			deadHeroes.add(hero);
		}
	}
	
	public void resurectHero(Hero hero)
	{
		if (deadHeroes.remove(hero)) {
			heroes.add(hero);
		}
	}
	
	public List<Hero> getDeadHeroes()
	{
		return deadHeroes;
	}
	
	public void addHero(Hero hero)
	{
		hero.setColor(getColor());
		heroes.add(hero);
	}
	
	public void addCastle(Castle castle)
	{
		castle.setColor(getColor());
		castles.add(castle);
	}
	
	public void removeHero(Hero hero)
	{
		heroes.remove(hero);
	}
	
	public void removeCastle(Castle castle)
	{
		castles.remove(castle);
	}
	
	public Collection<Hero> getHeroes()
	{
		return heroes;
	}
	
	public Collection<Castle> getCastles()
	{
		return castles;
	}
	
	public int getResource(ResourceType type)
	{
		return resources.get(type);
	}
	
	public void addResource(ResourceType type, int amount)
	{
		resources.put(type, resources.get(type)+amount);
		updateWindow();
	}
	
	public boolean startTurn(int day)
	{
		Iterator<Castle> i = castles.iterator();
		while (i.hasNext()) {
			Castle c = i.next();
			if (c.getColor() != color) {
				i.remove();
			}
		}
		return false;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Hero getActiveHero()
	{
		return activeHero;
	}

	public void setActiveHero(Hero activeHero)
	{
		this.activeHero = activeHero;
	}
	
	public void activeHeroChanged(Hero hero)
	{
		setActiveHero(hero);
		System.out.println("active hero changed");
		if (WorldMap.getLastInstance() != null) {
			System.out.println("pre calculate route");
			WorldMap.getLastInstance().calculateRoute(hero);
		}
	}
	
	public void clickedOnMap(int x, int y)
	{
	}
	
	public void moveClicked()
	{
	}
	
	public void turnClicked()
	{
	}
	
	public void prepareToMission()
	{
	}

	public void removeShadowRadius(int x, int y, int r)
	{
	}
}