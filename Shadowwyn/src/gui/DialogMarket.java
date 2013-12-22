package gui;

import java.util.*;

import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.core.Qt.WindowType;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QFrame.Shadow;
import com.trolltech.qt.gui.QFrame.Shape;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

public class DialogMarket extends QDialog
{
	private core.Player player;
	private QGridLayout layout = new QGridLayout();
	
	private QSlider sliderGold = new QSlider();
	private QLabel labelGold = new QLabel();
	
	private QSlider sliderWood = new QSlider();
	private QLabel labelWood = new QLabel();
	
	private QSlider sliderOre = new QSlider();
	private QLabel labelOre = new QLabel();
	
	private QComboBox toWhat = new QComboBox();
	private QLabel howMany = new QLabel("0");
	
	private QPushButton change = new QPushButton("Wymień");
	private QPushButton cancel = new QPushButton("Anuluj");
	
	private int amount = 0;
	
	public DialogMarket(core.Player p)
	{
		super();
		
		player = p;
		
		setLayout(layout);
		setWindowFlags(WindowType.Drawer);
		setStyleSheet(core.Const.style);
		layout.setSizeConstraint(SizeConstraint.SetFixedSize);
		
		layout.addWidget(new QLabel("<b>Surowce</b>"), 0, 0, 1, 3);
		layout.addWidget(new QLabel("Złoto: "), 1, 0);
		layout.addWidget(sliderGold, 1, 1);
		layout.addWidget(labelGold, 1, 2);
		layout.addWidget(new QLabel("Kamień: "), 2, 0);
		layout.addWidget(sliderOre, 2, 1);
		layout.addWidget(labelOre, 2, 2);
		layout.addWidget(new QLabel("Drewno: "), 3, 0);
		layout.addWidget(sliderWood, 3, 1);
		layout.addWidget(labelWood, 3, 2);
		
		layout.addWidget(new QLabel("<b>Wymień na</b>"), 4, 0, 1, 3);
		layout.addWidget(toWhat, 5, 0, 1, 2);
		layout.addWidget(howMany, 5, 2, 1, 1);
		
		QHBoxLayout buttonsLayout = new QHBoxLayout();
		QFrame line = new QFrame();
		line.setMinimumHeight(1);
		line.setObjectName("hline");
		layout.addWidget(line, 6, 0, 1, 3);
		layout.addLayout(buttonsLayout, 7, 0, 1, 3);
		
		buttonsLayout.addStretch();
		buttonsLayout.addWidget(cancel);
		buttonsLayout.addWidget(change);
		
		
		sliderGold.setOrientation(Orientation.Horizontal);
		sliderWood.setOrientation(Orientation.Horizontal);
		sliderOre.setOrientation(Orientation.Horizontal);
		
		updateLabels(0);
		sliderGold.valueChanged.connect(this, "updateLabels(int)");
		sliderWood.valueChanged.connect(this, "updateLabels(int)");
		sliderOre.valueChanged.connect(this, "updateLabels(int)");
		
		sliderGold.setRange(0, player.getResource(core.ResourceType.GOLD));
		sliderWood.setRange(0, player.getResource(core.ResourceType.WOOD));
		sliderOre.setRange(0, player.getResource(core.ResourceType.ORE));
		
		labelGold.setMinimumWidth(70);
		labelWood.setMinimumWidth(70);
		labelOre.setMinimumWidth(70);
		
		toWhat.addItem(core.ResourceType.GOLD.name, core.ResourceType.GOLD);
		toWhat.addItem(core.ResourceType.ORE.name, core.ResourceType.ORE);
		toWhat.addItem(core.ResourceType.WOOD.name, core.ResourceType.WOOD);
		toWhat.currentIndexChanged.connect(this, "updateLabels(int)");
		
		cancel.clicked.connect(this, "close()");
		change.clicked.connect(this, "change()");
	}
	
	private void change()
	{
		player.addResource(core.ResourceType.GOLD, -sliderGold.value());
		player.addResource(core.ResourceType.WOOD, -sliderWood.value());
		player.addResource(core.ResourceType.ORE, -sliderOre.value());
		player.addResource((core.ResourceType)(toWhat.itemData(toWhat.currentIndex())), amount);
		close();
	}
	
	private int convert(core.ResourceType to, core.ResourceType from, int amount)
	{
		return (int)(amount*from.exchange/to.exchange*0.5f);
	}
	
	private void updateLabels(int dummy)
	{
		labelGold.setText(sliderGold.value()+"/"+player.getResource(core.ResourceType.GOLD));
		labelWood.setText(sliderWood.value()+"/"+player.getResource(core.ResourceType.WOOD));
		labelOre.setText(sliderOre.value()+"/"+player.getResource(core.ResourceType.ORE));
		try {
		amount  = (
				convert((core.ResourceType)(toWhat.itemData(toWhat.currentIndex())), core.ResourceType.GOLD, sliderGold.value()) +
				convert((core.ResourceType)(toWhat.itemData(toWhat.currentIndex())), core.ResourceType.WOOD, sliderWood.value()) +
				convert((core.ResourceType)(toWhat.itemData(toWhat.currentIndex())), core.ResourceType.ORE, sliderOre.value())
			);
		howMany.setText(""+amount);
		} catch (java.lang.NullPointerException e) {}
	}
	
	public static void main(String[] args)
	{
		core.Player p1 = new core.PlayerHuman("gracz 1", core.Color.RED);
		core.Castle c1 = new core.Castle(core.CastleType.HUMAN_CASTLE, null);
		p1.addResource(core.ResourceType.GOLD, 10000);
		p1.addResource(core.ResourceType.WOOD, 100);
		p1.addResource(core.ResourceType.ORE, 100);
		
		QApplication.initialize(args);
		DialogMarket w = new DialogMarket(p1);
		w.exec();
		QApplication.exec();
	}
}
