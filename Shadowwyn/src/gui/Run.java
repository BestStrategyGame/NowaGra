package gui;

import com.trolltech.qt.gui.QApplication;

public class Run 
{
	public static void main(String[] args)
	{
		QApplication.initialize(args);
		//QApplication.invokeLater(Runnable);
		WindowStack w =  new WindowStack();
		WindowMain main =  new WindowMain();
		w.push(main);
		w.setFixedSize(1000, 700);
		w.show();
		QApplication.exec();
	}
}
