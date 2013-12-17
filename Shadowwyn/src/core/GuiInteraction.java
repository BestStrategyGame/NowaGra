package core;
public enum GuiInteraction
{
	INSTANCE;
	
	private int block = 0;
	
	public void progressStart()
	{
		if (block == 0) {
			
		}
		++block;
	}
	
	public void progressEnd()
	{
		--block;
		if (block == 0) {
			
		}
	}
	
	public void alert(String text)
	{
	}
}