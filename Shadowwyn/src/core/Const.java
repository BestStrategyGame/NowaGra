package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Const
{
	public static final String style;
	
	static {
		byte[] data = new byte[0];
		try {
	    	File file = new File("stylesheet.qss");
		    FileInputStream fis = new FileInputStream(file);
		    data = new byte[(int)file.length()];
			fis.read(data);
			fis.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//try {
				style = new String(data);
			//} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
		}
		
	}
	
}
