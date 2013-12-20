package core;
public enum Color
{
	BLUE("Niebieski", 0, 0, 255, "image/colors/mf_blue.png"),
	RED("Czerwony", 255, 0, 0, "image/colors/mf_red.png"),
	GREEN("Zielony", 0, 255, 0, "image/colors/mf_blue.png");
	
	public final String name;
	public final int rgb;
	public final String mfFile;
	
	private Color(String n, int r, int g, int b, String mf)
	{
		name = n;
		rgb = 256*256*r + 256*g + b;
		mfFile = mf;
	}
}