package core;
public class Point
{
	public final int x;
	public final int y;
	
	public Point (int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point (int xy)
	{
		this.x = xy/10000;
		this.y = xy%10000;
	}
	
	@Override
	public int hashCode()
	{
		return x*10000+y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		Point other = (Point)o;
		return x==other.x && y==other.y;
	}
}
