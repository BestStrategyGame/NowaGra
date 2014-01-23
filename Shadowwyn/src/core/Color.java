package core;
public enum Color
{
	BLUE("Niebieski", 0, 0, 255, "image/colors/mf_blue.png", "image/colors/hf_blue.png"),
	RED("Czerwony", 255, 0, 0, "image/colors/mf_red.png", "image/colors/hf_red.png"),
	GREEN("Zielony", 0, 255, 0, "image/colors/mf_green.png", "image/colors/hf_green.png"),
	NONE("Niezależny", 128, 128, 128, "", "");
	
	public final String name;
	public final int rgb;
	public final String mfFile;
	public final String hfFile;
	
	private Color(String n, int r, int g, int b, String mf, String hf)
	{
		name = n;
		rgb = 256*256*r + 256*g + b;
		mfFile = mf;
		hfFile = hf;
	}
}