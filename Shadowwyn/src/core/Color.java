package core;
public enum Color
{
	BLUE("Niebieski", "image/colors/mf_blue.png"),
	RED("Czerwony", "image/colors/mf_red.png"),
	GREEN("Zielony", "image/colors/mf_blue.png");
	
	public final String name;
	public final String mfFile;
	
	private Color(String n, String mf)
	{
		name = n;
		mfFile = mf;
	}
}