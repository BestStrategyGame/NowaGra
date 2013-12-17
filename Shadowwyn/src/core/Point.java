package core;
public class Point
{
	public final int x;
	public final int y;
	
	Point (int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	Point (int xy)
	{
		this.x = xy/10000;
		this.y = xy%10000;
	}
}
