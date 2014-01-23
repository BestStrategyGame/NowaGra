package gui;

import com.trolltech.qt.gui.QSound;

public class Sound
{
	public static void play(String file)
	{
		QSound.play(file);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
