package core;

import com.trolltech.qt.QSignalEmitter.Signal1;
import com.trolltech.qt.QSignalEmitter.Signal2;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMessageBox;

import gui.WindowStack;

import java.util.*;
import java.io.*;

public abstract class Mission extends QObject
{
	private static Mission LAST_INSTANCE;
	
	public static Mission getLastInstance() 
	{
		return LAST_INSTANCE;
	}
	
	private final int maxPlayers = 8;
	
	private Player[] players = new Player[maxPlayers];
	private int playersNo = 0;
	private int day = 1;
	protected WorldMap wmap;
	
	public Signal1<Player> updateWindow;
	public Signal1<Hero> addHero = new Signal1<Hero>();
	public Signal0 removeHero = new Signal0();

	private Player activePlayer;
	
	public Player getPlayer(Color c)
	{
		for (Player player: players) {
			if (player != null && player.getColor() == c) {
				return player;
			}
		}
		return null;
	}
	
	public void battleFinished(Color color, Player player1, Player player2, Hero hero1, Hero hero2, int strength1, int strength2)
	{
		Player winner, looser;
		Hero winnerh, looserh;
		int exp = 0;
		
		if (player1.getColor() == color) {
			winner = player1;
			winnerh = hero1;
			looser = player2;
			looserh = hero2;
			exp = strength2;
		} else {
			winner = player2;
			winnerh = hero2;
			looser = player1;
			looserh = hero1;
			exp = strength1;
		}
		
		
		WorldMap wm = WorldMap.getLastInstance();
		looserh.setUnits(null);
		if (looserh.getX() != -1) {
			looser.dieHero(looserh);
			if (looser == getActivePlayer()) {
				removeHero.emit();
			}
			wm.removeHero(looserh);
		}
		
		QMessageBox.warning(wm.getMapWidget(), "Koniec bitwy", "Wygrywa gracz "+color.name);
		exp *= 10;
		System.out.println("exp add " + exp);
		int i = winnerh.incExperience(exp);
		while (i-- != 0) {
			new gui.DialogLevelUp(winnerh, i).exec();
		}
	}
	
	public void emitUpdateWindowSignal(Player player)
	{
		updateWindow.emit(player);
	}
	
	protected void setActivePlayer(Player activePlayer)
	{
		this.activePlayer = activePlayer;
	}

	public Signal2<Integer, Player> startTurn = new Signal2<Integer, Player>();
	
	public Mission()
	{
		LAST_INSTANCE = this;
	}
	
	public void init(String ... names)
	{
		 updateWindow = new Signal1<Player>();
	}
	public abstract String getName();
	public abstract String getDescription();
	public abstract String getObjective();
	
	public WorldMap getWorldMap() {
		return wmap;
	}
	
	public gui.WidgetMap getMapWidget()
	{
		return wmap.getMapWidget();
	}
	
	protected void loadMap(String bg, String tr, String rs)
	{
		System.out.println("LOAD MAP: NEW WORLD MAP");
		QApplication.processEvents();
		wmap = new WorldMap(bg);
		//loadForeground(fg);
		System.out.println("LOAD MAP: LOAD TERRAIN");
		QApplication.processEvents();
		wmap.loadTerrain(tr);
		System.out.println("LOAD MAP: LOAD RESOURCES");
		QApplication.processEvents();
		wmap.loadResources(rs);
		System.out.println("LOAD MAP: INIT ROUTE");
		QApplication.processEvents();
		wmap.initRoute();
		System.out.println("LOAD MAP: CREATE MAP WIDGET");
		QApplication.processEvents();
		wmap.createMapWidget();
	}
	
	
	
	private void loadForeground(String file)
	{
		int r, g, b;
		int width, height;
		Scanner sc;
		
		try {
			sc = new Scanner(new File(file));
			sc.nextLine();
			sc.nextLine();
			width = sc.nextInt();
			height = sc.nextInt();
			sc.nextInt();
			for (int i=0; i<height; ++i) {
				for (int j=0; j<width; ++j) {
					r = sc.nextInt();
					g = sc.nextInt();
					b = sc.nextInt();
					
					ResourceType rt = ResourceType.fromRGB(r, g, b);
					if (rt != null) {
						wmap.addObject(j, i, new Resource(rt, 2));
						
						continue;
					}
					
					MapBuildingType mbt = MapBuildingType.fromRGB(r, g, b);
					if (mbt != null) {
						wmap.addObject(j, i, new MapBuilding(mbt, null));
						continue;
					}
					
					CastleType ct = CastleType.fromRGB(r, g, b);
					if (ct != null) {
						wmap.addObject(j, i, new Castle(ct, null));
						continue;
					}
					
					/*MapObject mo = MapObject.fromRGB(r, g, b);
					if (mo != null) {
						wmap.addObject(j, i, mo);
						continue;
					}*/
					
					//System.out.println("("+r+","+g+","+b+") ");
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void addPlayer(Player player)
	{
		if (playersNo < maxPlayers) {
			players[playersNo] = player;
			player.prepareToMission();
			++playersNo;
			wmap.removeShadow(player);
		}
	}
	
	public void finnishTurn(Player player)
	{
		System.out.println(playersNo);
		System.out.println(player);
		System.out.println(players[0]);
		System.out.println(players[1]);
		wmap.dailyBonus(player);
		if (day % 7 == 0) {
			wmap.weeklyBonus(player);
		}
		
		int i;
		for (i=0; players[i] != player; ++i);
		++i;
		
		if (i == playersNo) {
			i = 0;
			if (dayEnded()) {
				return;
			}
		}
		
		activePlayer = players[i];
		System.out.println(activePlayer);
		if (players[i].startTurn(day)) {
			startTurn.emit(day,  players[i]);
		}
	}
	
	private boolean dayEnded()
	{
		++day;
		return false;
	}
	
	public void start()
	{
		activePlayer = players[0];
		if (players[0].startTurn(day)) {
			startTurn.emit(day,  players[0]);
		}
	}


	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public void clickedOnMap(int x, int y)
	{
		System.out.println("mission: clickedOnMap");
		getActivePlayer().clickedOnMap(x, y);
	}
	
	public void moveClicked()
	{
		getActivePlayer().moveClicked();
	}
	
	public void turnClicked()
	{
		getActivePlayer().turnClicked();
	}
	
	public void activeHeroChanged(Hero hero)
	{
		System.out.println("~~~~~ ACTIVE HERO CHANGE "+hero.getName());

		getActivePlayer().activeHeroChanged(hero);
	}
	
	public void interactWithCastle()
	{
		
		Hero hero = getActivePlayer().getActiveHero();
		System.out.println("interact with castle "+hero+", "+hero.isInCastle());
		if (hero.isInCastle()) {
			WorldMapObject castle = wmap.getObjectAt(hero.getX(), hero.getY());
			castle.stand(hero, getActivePlayer());
		}
	}
	
	public void interactWithHero()
	{
		Hero hero = getActivePlayer().getActiveHero();
		int x = hero.getX();
		int y = hero.getY();
		boolean found = false;
		Hero hero2 = null;
		for (int i=x-1; i<=x+1; ++i) {
			for (int j=y-1; j<=y+1; ++j) {
				hero2 = wmap.getHeroAt(i, j);
				if (hero2 != null && hero != hero2 && hero.getColor() == hero2.getColor()) {
					//new gui.DialogHero(hero, hero2).exec();
					found = true;
					break;
				}
			}
			if (found) break;
		}
		gui.DialogHero dialog = new gui.DialogHero(hero, hero2);
		dialog.exec();
		
		WorldMapObject object = wmap.getObjectAt(x, y);
		if (object != null && object.getTooltip() != null) {
			wmap.getMapWidget().objectAt(y, x).setToolTip(object.getTooltip() +"\n\n"+ hero.getTooltip());
		} else {
			wmap.getMapWidget().objectAt(y, x).setToolTip(hero.getTooltip());
		}
	}
}