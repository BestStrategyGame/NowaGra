package gui;

import java.util.*;

import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QSizePolicy.Policy;

public class WidgetBuy extends QFrame
{
	private core.Castle castle;
	private core.Player player;
	private core.CastleBuilding building;
	
	private QPushButton buy;
	private QGridLayout layout = new QGridLayout();
	private QLabel require = new QLabel();
	
	public Signal0 buyed = new Signal0();
	
	public WidgetBuy(core.Player p, core.Castle c, core.CastleBuilding b)
	{
		super();
		
		castle = c;
		player = p;
		building = b;
		
		setLayout(layout);
		//setFrameStyle(1);
		//setMinimumHeight(200);
		//setMaximumWidth(200);
		setSizePolicy(Policy.MinimumExpanding, Policy.Maximum);
		
		layout.addWidget(new QLabel("<b>"+b.name+"</b>"), 0, 0);
		layout.addWidget(new QLabel(b.description), 1, 0);
		layout.addWidget(require, 3, 0);
		
		if (c.getBuildings().contains(b)) {
			buy = new QPushButton("Kupiony");
			setInactive();
			
		} else {
		
			String buystr = "Kup (";
			if (b.cost.gold > 0) {
				buystr += ""+b.cost.gold+" złota, ";
			}
			if (b.cost.ore > 0) {
				buystr += ""+b.cost.ore+" kamienia, ";
			}
			if (b.cost.wood > 0) {
				buystr += ""+b.cost.wood+" drewna, ";
			}
			buystr = buystr.substring(0, buystr.length()-2) + ")";
			buy = new QPushButton(buystr);
			checkActive();
			
			buy.clicked.connect(this, "buyClicked()");
		}
		layout.addWidget(buy, 2, 0);
	}
	
	public void checkActive()
	{
		if (building.requires.length > 0 && !castle.getBuildings().containsAll(Arrays.asList(building.requires))) {
			buy.setEnabled(false);
			
			String requirestr = "<i>Wymaga: ";
			Set<core.CastleBuilding> diff = new HashSet<core.CastleBuilding>(Arrays.asList(building.requires));
			diff.removeAll(castle.getBuildings());
			System.out.println(diff);
			int i = 0;
			for (core.CastleBuilding b: diff) {
				if (i != 0) requirestr += ", ";
				requirestr += b.name;
				++i;
			}
			requirestr += "</i>";
			require.setText(requirestr);
			
			return;
		} else {
			require.setText("");
		}
		
		if (castle.isBuyed()) {
			buy.setEnabled(false);
			return;
		}
		if (player.getResource(core.ResourceType.GOLD) < building.cost.gold) {
			buy.setEnabled(false);
			return;
		}
		if (player.getResource(core.ResourceType.WOOD) < building.cost.wood) {
			buy.setEnabled(false);
			return;
		}
		if (player.getResource(core.ResourceType.ORE) < building.cost.ore) {
			buy.setEnabled(false);
			return;
		}
		System.out.println(castle.getBuildings());
		System.out.println(Arrays.asList(building.requires));
		if (castle.getBuildings().contains(building)) {
			buy.setEnabled(false);
			return;
		}		
	}
	
	public void buyClicked()
	{
		System.out.println("buy clicked");
		castle.buyBuilding(building);
		player.addResource(core.ResourceType.GOLD, -building.cost.gold);
		player.addResource(core.ResourceType.WOOD, -building.cost.wood);
		player.addResource(core.ResourceType.ORE, -building.cost.ore);
		buy.setText("Kupiony");
		//building.buyBonus(player, castle);
		buyed.emit();
		
	}
	
	public void setInactive()
	{
		buy.setEnabled(false);
	}
	
	public static void main(String[] args)
	{
		core.Player p1 = new core.PlayerHuman("gracz 1", core.Color.RED);
		core.Castle c1 = new core.Castle(core.CastleType.HUMAN_CASTLE, null);
		p1.addResource(core.ResourceType.GOLD, 2000);
		p1.addCastle(c1);
		c1.addBuilding(core.CastleBuilding.CITY_COUNCIL);
		
		QApplication.initialize(args);
		WidgetBuy w = new WidgetBuy(p1, c1, core.CastleBuilding.TOWN_HALL);
		w.show();
		QApplication.exec();
	}
}
