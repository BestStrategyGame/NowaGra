package core;
public enum Color
{
	BLUE("Niebieski"),
	RED("Czerwony"),
	GREEN("Zielony");
	
	public final String name;
	
	private Color(String n)
	{
		name = n;
	}
}