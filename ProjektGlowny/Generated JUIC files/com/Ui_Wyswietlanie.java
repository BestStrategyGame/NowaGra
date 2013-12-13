/********************************************************************************
** Form generated from reading ui file 'Wyswietlanie.jui'
**
** Created: Pt 13. gru 15:13:55 2013
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Wyswietlanie implements com.trolltech.qt.QUiForm<QWidget>
{
    public QPushButton pushButton;
    public QPushButton pushButton_2;
    public QLineEdit lineEdit;
    public QLineEdit lineEdit_2;

    public Ui_Wyswietlanie() { super(); }

    public void setupUi(QWidget Wyswietlanie)
    {
        Wyswietlanie.setObjectName("Wyswietlanie");
        Wyswietlanie.resize(new QSize(400, 300).expandedTo(Wyswietlanie.minimumSizeHint()));
        pushButton = new QPushButton(Wyswietlanie);
        pushButton.setObjectName("pushButton");
        pushButton.setGeometry(new QRect(50, 60, 75, 23));
        pushButton_2 = new QPushButton(Wyswietlanie);
        pushButton_2.setObjectName("pushButton_2");
        pushButton_2.setGeometry(new QRect(200, 60, 75, 23));
        lineEdit = new QLineEdit(Wyswietlanie);
        lineEdit.setObjectName("lineEdit");
        lineEdit.setGeometry(new QRect(50, 140, 113, 20));
        lineEdit_2 = new QLineEdit(Wyswietlanie);
        lineEdit_2.setObjectName("lineEdit_2");
        lineEdit_2.setGeometry(new QRect(190, 140, 113, 20));
        retranslateUi(Wyswietlanie);

        Wyswietlanie.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget Wyswietlanie)
    {
        Wyswietlanie.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Wyswietlanie", "Form", null));
        pushButton.setText(com.trolltech.qt.core.QCoreApplication.translate("Wyswietlanie", "PushButton", null));
        pushButton_2.setText(com.trolltech.qt.core.QCoreApplication.translate("Wyswietlanie", "PushButton", null));
    } // retranslateUi

}

