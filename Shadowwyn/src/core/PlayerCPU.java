package core;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QMessageBox.StandardButton;
import com.trolltech.qt.gui.QMessageBox.StandardButtons;

public class PlayerCPU extends Player
{
	PlayerCPU(String n, Color c)
	{
		super(n, c);
	}
	
	

	@Override
	public boolean startTurn(int day)
	{
		super.startTurn(day);
		
		GuiInteraction gui0 = GuiInteraction.INSTANCE;
		//gui.progressStart();
		
		boolean finnish = false;
		WorldMap wmap = WorldMap.getLastInstance();
		WorldMap.Point target;
		WorldMap.Point next;
		
		boolean changeMade;
		do {
			changeMade = false;
			
			// if castle is endagered
			for (Castle c: getCastles()) {
				System.out.println("AI: CHECK CASTLE FOR DANGER: "+c.getName());
				Hero dummy  = new Hero(null, null);
				Point pos = wmap.getPositionOfObject(c);
				dummy.setPosition(pos.x, pos.y);
				System.out.println("AI: CREATE DUMMY HERO IN CASTLE AT POSITION: "+pos.x+", "+pos.y);
				wmap.calculateRoute(dummy);
				
				boolean enforceCastle;
				do {
					enforceCastle = false;
					if (wmap.checkDangerFor(c, dummy)) {
						// castle endangered
						
						
						if (c.recruitAll(this)) {
							System.out.println("AI: !RECRUIT UNITS IN CASTLE");
							// recruit in castle
							enforceCastle = true;
							changeMade = true;
						} else if (!c.isBuyed() && c.buyBuildingLair(this)) {
							System.out.println("AI: !BUILD LAIR IN CASTLE");
							enforceCastle = true;
							changeMade = true;
						} else {
							// set as endangered so hero must go back
							System.out.println("AI: CASTLE STILL ENDANGERED");
							if (c.getEndangered() == false) {
								System.out.println("AI: !CASTLE MARKED AS ENDANGERED");
								c.setEndangered(true);
								changeMade = true;
							}
						}
					}
				} while (enforceCastle);
			}
			
			while (getHeroes().size() < 2 || getHeroes().size() < getCastles().size()) {
				boolean change = false;

				if (getResource(core.ResourceType.GOLD) < 2500) {
					break;
				}
				for (Castle castle: getCastles()) {
					if (castle.getHero() != null && castle.getHero().isInCastle(castle)) {
						continue;
					} else {
						Hero hero = new Hero("Nowy bohater", castle.getType());
						hero.setInCastle(castle);
						addResource(core.ResourceType.GOLD, -2500);
						addHero(hero);
						castle.setHero(hero);

						core.Point pos = wmap.getPositionOfObject(castle);
						wmap.addObject(pos.x, pos.y, hero);
						wmap.moveTo(hero, this, pos.x, pos.y, -1, -1);
						change = true;
					}
				}
				if (!change) break;
			}
			
			// buy stuff
			boolean finishedBuying;
			do {
				System.out.println("AI: START BUILDING");
				finishedBuying = true;
				for (Castle c: getCastles()) {
					if (c.isBuyed()) {
						continue;
					}
					System.out.println("AI: BUILD IN CASLTE "+c.getName());
					generateBalancedBudget();
					
					Cost budget = getBalancedBudget();
					if (budget.gold >= -100) {
						System.out.println("AI: LOW ON GOLD");
						if (!c.isBuyed() && c.buyBuildingOrRequired(this, CastleBuilding.CAPITOL)) {
							System.out.println("AI: !BUILD CAPITOL");
							finishedBuying = false;
							changeMade = true;
						}
					} else {
						
						if (!c.isBuyed() && c.buyBuildingLair(this)) {
							System.out.println("AI: !BUILD LAIR");
							c.moveUnitsToHero();
							finishedBuying = false;
							changeMade = true;
						} else if (!c.isBuyed() && c.buyBuildingOrRequired(this, CastleBuilding.CASTLE)) {
							System.out.println("AI: !BUILD CASTLE");
							finishedBuying = false;
							changeMade = true;
						}
					}
					
					if (c.recruitAll(this)) {
						System.out.println("AI: !RECRUIT UNITS IN CASTLE 2");
						finishedBuying = false;
					}
					if (c.getHero() != null && c.getHero().isInCastle()) {
						System.out.println("AI: !MOVE UNITS TO HERO");
						c.moveUnitsToHero();
						finishedBuying = false;
					}
					
				}
				break;
			} while (finishedBuying);
			generateBalancedBudget();
			// move heroes
			System.out.println("AI: FIND WHERE TO GO");
			target = null;
			Hero hero = null;
			for (Hero h: getHeroes()) {
				System.out.println("AI: PATH FOR HERO "+h.getName()+" "+h.getMovePoints());
				if (h.getMovePoints() > 0) {
					target = wmap.whereToGo(h, this, day);
					hero = h;
					
					
					
					if (target != null) {
						System.out.println("AI: GO TO TARGET "+target.x+", "+target.y+", dist = "+target.getDistance());

						while (target.getDistance() > hero.getMovePoints()) {
							if (target.getDistance() > 499999) {
								next = target.getParent();
								if (next.getDistance() <= hero.getMovePoints()) {
									
									break;
								}
							}
							target = target.getParent();
						}
						
						next = target.getParent();
						WorldMapObject wmo = wmap.getObjectAt(target.x, target.y);
						System.out.println("AI: DEBUG "+target+", "+next);
						float distance = target.getDistance();
						System.out.println("AI: DISTANCE MADE "+distance);
						if (wmo != null) {
							System.out.println("AI: STAND ON "+wmo.getName());
						}
						
						
						if (distance > 500000) {
							distance -= 500000;
						}
						hero.takeMovePoints(distance);
						
						if (distance > 0.1) {
							System.out.println("AI: !MOVE");
							setActiveHero(hero);
							if (next != null) {
								wmap.moveTo(hero, this, target.x, target.y, next.x, next.y);
							} else {
								wmap.moveTo(hero, this, target.x, target.y, -1, -1);
							}
							gui.WindowStack ws = gui.WindowStack.getLastInstance();
							while (ws.top() instanceof gui.WindowBattle) {
								QApplication.processEvents();
							}
							
							changeMade = true;
						}
					}
					
					
					
					
				}
			}
			
			
			
			
		} while (changeMade);
		
		
		//gui.progressEnd();
		System.out.println("AI: FINISHED TURN");
		
		System.out.println("  AI: RESOURCE GOLD "+getResource(ResourceType.GOLD));
		System.out.println("  AI: RESOURCE WOOD "+getResource(ResourceType.WOOD));
		System.out.println("  AI: RESOURCE ORE "+getResource(ResourceType.ORE));
		for (Castle c: getCastles()) {
			System.out.println("  AI: CASTLE "+c.getName());
			for (CastleBuilding b: c.getBuildings()) {
				System.out.println("  AI: BUILDING "+b.name);
			}
			if (c.getGarission() != null) for (GroupOfUnits u: c.getGarission().getUnits()) {
				System.out.println("  AI: GARISSON "+u.type.name+" ("+u.getNumber()+")");
			}
		}
		for (Hero h: getHeroes()) {
			System.out.println("  AI: HERO "+h.getName()+" "+h.getColor());
			for (GroupOfUnits u: h.getUnits()) {
				System.out.println("  AI: UNIT "+u.type.name+" ("+u.getNumber()+")");
			}
		}
		
		Mission.getLastInstance().finnishTurn(this);
		
		return false;
	}
	
	@Override
	public boolean battleStartTurn(GroupOfUnits unit)
	{
		return false;
	}
}
