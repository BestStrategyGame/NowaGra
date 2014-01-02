package gui;

import com.trolltech.qt.core.Qt.Alignment;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QSizePolicy.Policy;

public class WidgetLabel extends QFrame
{
	public WidgetLabel(String text, core.Color color)
	{
		super();
		QLabel label = new QLabel();
		label.setText(text);
		QVBoxLayout layout = new QVBoxLayout();
		QHBoxLayout hlayout = new QHBoxLayout();
		layout.setMargin(0);
		hlayout.setMargin(0);
		
		layout.addStretch();
		layout.addLayout(hlayout);
		hlayout.addStretch();
		hlayout.addWidget(label);
		//hlayout.addStretch();
		
		setLayout(layout);
		//setAlignment(new Alignment(AlignmentFlag.AlignCenter));
		label.setObjectName("label");
		setSizePolicy(Policy.Maximum, Policy.Maximum);
		//setGeometry(48, 32, 16, 32);
		//setMaximumHeight(16);
		//setMaximumWidth(32);
		int rgb = color.rgb;
		int b = rgb % 256;
		rgb /= 256;
		int g = rgb % 256;
		int r = rgb / 256;
		label.setStyleSheet("background-color: rgb("+r+","+g+","+b+");");
	}
}
