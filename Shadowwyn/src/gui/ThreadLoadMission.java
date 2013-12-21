package gui;

import java.util.*;
import com.trolltech.qt.core.*;

public class ThreadLoadMission implements Runnable
{
	private core.Mission mission;
	private String[] names;
	
	public ThreadLoadMission(core.Mission m, String ... n)
	{
		mission = m;
		names = n;
	}
	
	@Override
	public void run()
	{
		
	}
}
