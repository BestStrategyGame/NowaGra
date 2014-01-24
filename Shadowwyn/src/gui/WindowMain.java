package gui;

import java.util.*;

import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QMessageBox.StandardButton;
import com.trolltech.qt.gui.QMessageBox.StandardButtons;

public class WindowMain extends QWidget
{
	
	private QPushButton campaign1 = new QPushButton("Kampania Ludzi");
	private QPushButton campaign2 = new QPushButton("Kampania Demonów");
	private QPushButton fast = new QPushButton("Szybka walka");
	private QPushButton about = new QPushButton("O grze");
	private QPushButton exit = new QPushButton("Wyjście");
	
	private WidgetImage background = new WidgetImage("image/menu/main.png");
	private WidgetImage logo = new WidgetImage("image/menu/logo.png");
	
	private DialogAbout dialog = new DialogAbout(this);
	
	public WindowMain()
	{
		super();
		
		//setStyleSheet(core.Const.menuStyle);
		
		setMinimumSize(1000, 700);
		setMaximumSize(1000, 700);
		
		exit.setGeometry(width()-200, height()-50*1-32, 150, 40);
		about.setGeometry(width()-200, height()-50*2-32, 150, 40);
		fast.setGeometry(width()-200, height()-50*3-32, 150, 40);
		campaign2.setGeometry(width()-200, height()-50*4-32, 150, 40);
		campaign1.setGeometry(width()-200, height()-50*5-32, 150, 40);
		
		logo.move(250, 50);
		//logo.setStyleSheet("background-color: none");
		
		background.setParent(this);
		logo.setParent(this);
		exit.setParent(this);
		about.setParent(this);
		fast.setParent(this);
		campaign1.setParent(this);
		campaign2.setParent(this);
		
		
		exit.clicked.connect(this, "exitClicked()");
		about.clicked.connect(this, "aboutClicked()");
		fast.clicked.connect(this, "fastClicked()");
		campaign1.clicked.connect(this, "campaign1Clicked()");
		campaign2.clicked.connect(this, "campaign2Clicked()");
		
		
	}
	
	public void exitClicked()
	{
		WindowStack ws = WindowStack.getLastInstance();
		if (ws != null) {
			ws.close();
		}
	}
	
	public void aboutClicked()
	{
		dialog.exec();
	}
	
	public void fastClicked()
	{
		WindowStack ws = WindowStack.getLastInstance();
		System.out.println(ws);
		if (ws != null) {
			
			WindowFast fast = new WindowFast();
			ws.push(fast);
		}
	}
	
	public void campaign1Clicked()
	{
		WindowStack ws = WindowStack.getLastInstance();
		System.out.println(ws);
		if (ws != null) {
			
			WindowCampaign campaign = new WindowCampaign(1);
			ws.push(campaign);
		}
	}
	
	public void campaign2Clicked()
	{
		WindowStack ws = WindowStack.getLastInstance();
		System.out.println(ws);
		if (ws != null) {
			
			WindowCampaign campaign = new WindowCampaign(2);
			ws.push(campaign);
		}
	}
	
	public static void main(String[] args)
	{
		QApplication.initialize(args);
		
		WindowMain w = new WindowMain();
		w.show();
		QApplication.exec();
	}
}
