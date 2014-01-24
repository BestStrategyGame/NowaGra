package core;

import com.trolltech.qt.gui.*;

/*
tworzymy nowa mape o rozmiarze rows*cols, gdzie kafelka ma wymiar size*size:

	wm = new WidgetMap(rows, cols, size)

nakladamy tlo z pliku filename o rozmiarze (size*cols) * (size*rows):

	wm.setBackground(filename)

pobieramy stos na ktorym ukladane sa grafiki na mapie na kafelce (col, row):

	obj = wm.objectAt(row, col)

wkladamy widget na stos na warstwie o numerze layer.
gdy widget istnieje, to jest zamieniany.
gdy ustawimy widget = null, to dotychczasowy widget jest usuniety:

	obj.setLayer(layer, widget)
	obj.setLayer(layer, null)

warstwy sa 4. na mapie swiata uzywana jest taka numeracja:

	layer = 0 - teren (nie uzywany z powodu ostatniej modyfikacji)
	layer = 1 - budynki i inne obiekty nieruchome (jak wyzej)
	layer = 2 - obiekty ruchome - bohaterowie, jednostki
	layer = 3 - markery zaznaczajace trase

widget powinien byc obiektem typu WidgetImage czyli grafika.
gdy chcemy wczytac grafike z pliku filename:

	widget = new WidgetImage(filename);
	obj.setLayer(layer, widget);
 */

public class WorldMapView
{
	private Mission mission;
	private gui.WindowMap window;
	
	public WorldMapView()
	{
		System.out.println("start");
		mission = new MissionC1v1();
		mission.init("foo");
		window = new gui.WindowMap();
		gui.WidgetMap map = mission.getMapWidget();
		mission.startTurn.connect(window, "startTurn(int,Player)");
		mission.updateWindow.connect(window, "updateData(Player)");
		window.heroChanged.connect(mission, "activeHeroChanged(Hero)");
		window.moveClicked.connect(mission, "moveClicked()");
		window.turnClicked.connect(mission, "turnClicked()");
		map.pressed.connect(mission, "clickedOnMap(int,int)");
		window.setMap(map);
		
		mission.start();
		window.show();
	}
	
	//public QWidget getWidget()

	/*private void startTurn(int day, Player player)
	{
		System.out.println("st");
		window.startTurn(day, player);
	}*/
	
	public static void main(String[] args)
	{
		QApplication.initialize(args);
		new WorldMapView();
		QApplication.exec();
	}

}
