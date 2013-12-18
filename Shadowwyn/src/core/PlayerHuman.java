package core;

public class PlayerHuman extends Player
{
	private WorldMap.Point target = null;
	
	PlayerHuman(String n, Color c)
	{
		super(n, c);
	}
	
	@Override
	public boolean startTurn(int day) {
		System.out.println("Start turn "+getName());
		if (getHeroes().iterator().hasNext()) {
			setActiveHero(getHeroes().iterator().next());
		} else {
			setActiveHero(null);
		}
		//Mission.getLastInstance().finnishTurn(this);
		
		return true;

	}
	
	public void clickedOnMap(int x, int y)
	{
		if (getActiveHero() == null) {
			return;
		}
		float d = Mission.getLastInstance().getWorldMap().getDistance(y, x);
		
		System.out.println("clicked on map "+d);
		if (d < 1000000) {
			WorldMap wmap = Mission.getLastInstance().getWorldMap();
			WorldMap.Point point = wmap.getRoute(y, x);
			target = null;
			gui.WidgetMap mapWidget = Mission.getLastInstance().getMapWidget();//.drawPath(point, getActiveHero().getMovePoints());
			gui.WidgetStack imageStack;
			/*for (int row=0; row<wmap.getHeight(); ++row) {
				for (int col=0; col<wmap.getWidth(); ++col) {
					imageStack = mapWidget.getImageStack(row, col);
					imageStack.setLayer(3, null);
					//new gui.WidgetImage("image/o.png", 64)
				}
			}*/
			mapWidget.clearLevel(3);
			for (int i=0; i<1000 && (point.x != getActiveHero().getX() || point.y != getActiveHero().getY()); ++i) {
				//System.out.println(point.x +", "+point.y+", "+getActiveHero().getX()+", "+getActiveHero().getY());
				if (i == 0) {
					if (point.getDistance() > getActiveHero().getMovePoints()) {
						System.out.println(point.getDistance());
						if (point.getDistance() > 500000f && point.getDistance()-500000f < getActiveHero().getMovePoints()) {
							mapWidget.objectAt(point.y, point.x).setLayer(3, new gui.WidgetImage("image/markers/orange2.png", 64));
							if (target == null) {
								target = point;
							}
						} else {
							mapWidget.objectAt(point.y, point.x).setLayer(3, new gui.WidgetImage("image/markers/red2.png", 64));
						}
						
					} else {
						mapWidget.objectAt(point.y, point.x).setLayer(3, new gui.WidgetImage("image/markers/green2.png", 64));
						if (target == null) {
							target = point;
						}
					}
				}
				else if (point.getDistance() > getActiveHero().getMovePoints()) {
					mapWidget.objectAt(point.y, point.x).setLayer(3, new gui.WidgetImage("image/markers/red.png", 64));
				} else {
					mapWidget.objectAt(point.y, point.x).setLayer(3, new gui.WidgetImage("image/markers/green.png", 64));
					if (target == null) {
						target = point;
					}
				}
				point = point.getParent();
			}
		}
	}
	
	public void moveClicked()
	{
		if (target != null) {
			WorldMap wmap = Mission.getLastInstance().getWorldMap();
			float distance = target.getDistance() > 500000f ? target.getDistance()-500000f : target.getDistance();
			getActiveHero().takeMovePoints((int)java.lang.Math.ceil(distance));
			wmap.moveTo(getActiveHero(), this, target.x, target.y, target.getParent().x, target.getParent().y);
		}
	}
	
	public void turnClicked()
	{
		Mission.getLastInstance().getWorldMap().getMapWidget().clearLevel(3);
		Mission.getLastInstance().finnishTurn(this);
	}

	public void activeHeroChanged(Hero hero)
	{
		super.activeHeroChanged(hero);
		Mission.getLastInstance().getMapWidget().clearLevel(3);
	}
}
