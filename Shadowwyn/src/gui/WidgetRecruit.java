package gui;

import java.util.*;

import com.trolltech.qt.QSignalEmitter.Signal0;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QSizePolicy.Policy;

public class WidgetRecruit extends QFrame
{
	private core.Player player;
	private core.Castle castle;
	private core.Hero hero;
	private core.UnitType unit;
	
	private QGridLayout layout = new QGridLayout();
	private QHBoxLayout topLayout = new QHBoxLayout();
	private QLabel totalGold = new QLabel("0");
	private QLabel totalWood = new QLabel("0");
	private QLabel totalOre = new QLabel("0");
	private QSlider amount = new QSlider();
	private QPushButton buy = new QPushButton();
	private QLabel available;
	
	public Signal0 recruited = new Signal0();
	
	public WidgetRecruit(core.Player p, core.Castle c, core.Hero h, core.UnitType u)
	{
		super();
		
		player = p;
		castle = c;
		hero = h;
		unit = u;
		
		setLayout(topLayout);
		setSizePolicy(Policy.MinimumExpanding, Policy.Maximum);
		//setFrameStyle(1);
		System.out.println(c.getAvailableToRecruit(u.level));
		available = new QLabel("<i>("+c.getAvailableToRecruit(u.level)+" dostępnych)</i>");
		
		QWidget image = new WidgetImage(u.file);
		image.setSizePolicy(Policy.Maximum, Policy.Maximum);
		topLayout.addWidget(image);
		topLayout.addLayout(layout);
		layout.addWidget(new QLabel("<b>"+u.name+"</b>"), 0, 1, 1, 3);
		layout.addWidget(available, 1, 4, 1, 5);
		layout.addWidget(new QLabel("Koszt jednostki:"), 1, 1, 1, 2);
		layout.addWidget(new QLabel("Złoto"), 2, 1);
		layout.addWidget(new QLabel("Kamień"), 3, 1);
		layout.addWidget(new QLabel("Drewno"), 4, 1);
		layout.addWidget(new QLabel(""+u.cost.gold), 2, 2);
		layout.addWidget(new QLabel(""+u.cost.ore), 3, 2);
		layout.addWidget(new QLabel(""+u.cost.wood), 4, 2);
		layout.addWidget(new QLabel("Razem:"), 1, 3);
		layout.addWidget(totalGold, 2, 3);
		layout.addWidget(totalOre, 3, 3);
		layout.addWidget(totalWood, 4, 3);
		layout.addWidget(amount, 2, 4, 2, 5);
		layout.addWidget(buy, 3, 4, 3, 5);
		
		
		amount.setOrientation(Orientation.Horizontal);
		amount.valueChanged.connect(this, "amountChanged(int)");
		buy.clicked.connect(this, "buyClicked()");
		updateRange();
		amountChanged(0);
	}
	
	public void buyClicked()
	{
		if (amount.value() == 0) {
			return;
		}
		boolean found = false;
		for (core.GroupOfUnits u: hero.getUnits()) {
			if (u.type == unit) {
				u.setNumber(u.getNumber()+amount.value());
				found = true;
				pay();
				break;
			}
		}
		if (!found) {
			if (hero.getUnits().size() == 6) {
				QMessageBox.warning(this, "Nie można zrekrutować jednostek", "W garnizonie może znajdować sie maksymalnie 6 jednostek");
			} else {
				hero.getUnits().add(new core.GroupOfUnits(unit, hero, amount.value()));
				pay();
			}
		}
		updateRange();
		recruited.emit();
	}
	
	public void updateRange()
	{
		float gold = player.getResource(core.ResourceType.GOLD)/(float)unit.cost.gold;
		float wood = player.getResource(core.ResourceType.WOOD)/(float)unit.cost.wood;
		float ore = player.getResource(core.ResourceType.ORE)/(float)unit.cost.ore;
		float min = castle.getAvailableToRecruit(unit.level);
		if (gold < min) min = gold;
		if (wood < min) min = wood;
		if (ore < min) min = ore;
		int max = (int)min;
		amount.setRange(0, max);
		amount.setValue(0);
		available.setText("<i>("+max+" dostępnych)</i>");
	}
	
	private void pay()
	{
		castle.recruit(unit.level, amount.value());
		available.setText("<i>("+castle.getAvailableToRecruit(unit.level)+" dostępnych)</i>");
		player.addResource(core.ResourceType.GOLD, -unit.cost.gold*amount.value());
		player.addResource(core.ResourceType.WOOD, -unit.cost.wood*amount.value());
		player.addResource(core.ResourceType.ORE, -unit.cost.ore*amount.value());
	}
	
	public void amountChanged(int amount)
	{
		totalGold.setText(""+unit.cost.gold*amount);
		totalWood.setText(""+unit.cost.wood*amount);
		totalOre.setText(""+unit.cost.ore*amount);
		buy.setText("Rekrutuj "+amount+" jednostek");
	}
	
	public static void main(String[] args)
	{
		core.Player p1 = new core.PlayerHuman("gracz 1", core.Color.RED);
		p1.addResource(core.ResourceType.GOLD, 8000);
		p1.addResource(core.ResourceType.WOOD, 5);
		core.Castle c1 = new core.Castle(core.CastleType.HUMAN_CASTLE, null);
		c1.addAvailableToRecruit(1, 99);
		QApplication.initialize(args);
		core.Hero h =  new core.Hero("dummy", null);
		WidgetRecruit w = new WidgetRecruit(p1, c1, h, core.UnitType.WOJAK);
		w.show();
		QApplication.exec();
		//System.out.println(h.getUnits().get(0).getNumber());
	}
}
