package core;

import java.util.ArrayList;
import java.util.List;

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
	
	public static IndependentUnits create(UnitType type, int num)
	{
		List<GroupOfUnits> u = new ArrayList<GroupOfUnits>();
		u.add(new GroupOfUnits(type, null, num));
		IndependentUnits i = new IndependentUnits();
		i.setUnits(u);
		return i;
	}
	
	public IndependentUnits add(UnitType type, int num)
	{
		getUnits().add(new GroupOfUnits(type, null, num));
		return this;
	}
}