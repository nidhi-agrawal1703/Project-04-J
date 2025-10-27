package in.co.rays.proj4.util;

import java.util.ResourceBundle;


/**
 * PropertyReader is used to read values
 * from Resource Bundle in form of key-value 
 * 
 * @author Nidhi
 * @version 1.0
 */
public class PropertyReader {
	
	private static ResourceBundle rb=ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");
	
	public static String getValue(String key) {
		String val=null;
		try {
			val=rb.getString(key);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return val;
	}
	
	public static String getValue(String key,String param) {
		String msg=getValue(key);
		msg=msg.replace("{0}", param);
		return msg;
	}
	
	public static String getValue(String key,String[] param) {
		String msg=getValue(key);
		for(int i=0;i<param.length;i++) {
			msg=msg.replace("{"+i+"}", param[i]);
		}
			return msg;
	}

	public static void main(String[] args) {
		
		System.out.println("Single key example:");
		System.out.println(PropertyReader.getValue("error.require"));
		
		System.out.println("\nSingle parameter replacement example");
		System.out.println(PropertyReader.getValue("error.require","login Id"));
		
		System.out.println("Multiple parameter replacement example");
		String[] params= {"Roll no","Student name"};
		System.out.println(PropertyReader.getValue("error.multipleFields", params));
		
	}
	
}
