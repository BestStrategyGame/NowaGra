package core;
public class IndependentUnits extends Hero implements WorldMapObject 
{
	
	public IndependentUnits()
	{
		super("Jednostki niezależne", null);
		setColor(Color.NONE);
	}
	
	@Override
	public String getImageFile() {
		return getUnits().get(0).type.file;
	}
}