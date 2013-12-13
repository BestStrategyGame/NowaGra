package com;

import com.trolltech.qt.gui.*;

public class Wyswietlanie extends QWidget {

    Ui_Wyswietlanie ui = new Ui_Wyswietlanie();

    public static void main(String[] args) {
        QApplication.initialize(args);

        Wyswietlanie testWyswietlanie = new Wyswietlanie();
        testWyswietlanie.show();

        QApplication.exec();
    }

    public Wyswietlanie() {
        ui.setupUi(this);
    }
    
    public void on_pushButton_clicked()
    {
    	ui.lineEdit.setText("Siemanko1");
    
    }


    public Wyswietlanie(QWidget parent) {
        super(parent);
        ui.setupUi(this);
    }
}
