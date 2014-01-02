package gui;

import com.trolltech.qt.core.Qt.WindowType;
import com.trolltech.qt.gui.*;

public class DialogAbout extends QFrame
{
	private QGridLayout layout = new QGridLayout();	
	private QPushButton close = new QPushButton("Zamknij okno");
	private QWidget parent;
	
	public DialogAbout(QWidget p)
	{
		super();
		
		parent = p;
		hide();
		
		setObjectName("dialog");
		setLayout(layout);
		setStyleSheet(core.Const.style);
		
		QFrame line = new QFrame();
		line.setMinimumHeight(1);
		line.setObjectName("hline");
		
		layout.addWidget(new QLabel("<b>Shadowwyn</b> wersja 1.0"));
		layout.addWidget(line);
		layout.addWidget(new QLabel("Autorzy:\n  Dawid Garus, Bartłomiej Bajdo, Paweł Fatyga,\n  Łukasz Juszczak, Michał Kalita, Dominik Adamek"));
		layout.addWidget(new QLabel("Stuff..."));
		
		QFrame line2 = new QFrame();
		line2.setMinimumHeight(1);
		line2.setObjectName("hline");
		//layout.addWidget(line2);
		
		QHBoxLayout layoutButtons = new QHBoxLayout();
		layoutButtons.addStretch();
		layoutButtons.addWidget(close);
		layout.addLayout(layoutButtons, 11, 0);

		close.clicked.connect(this, "hide()");
		
	}
	
	public void exec()
	{
		setParent(parent);
		move(300, 300);
		show();
	}
	
	public static void main(String[] args)
	{
		
	}
}
