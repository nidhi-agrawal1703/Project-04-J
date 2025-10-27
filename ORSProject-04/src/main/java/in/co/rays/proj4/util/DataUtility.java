package in.co.rays.proj4.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * DataUtility class is used to convert data from one format to another.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class DataUtility {
	
	public static final String APP_DATE_FORMAT="dd-MM-yyyy";
	
	public static final String APP_TIME_FORMAT="dd-MM-yyyy HH:mm:ss";
	
	private static final SimpleDateFormat formatter=new SimpleDateFormat(APP_DATE_FORMAT);
	
	private static final SimpleDateFormat timeFormatter=new SimpleDateFormat(APP_TIME_FORMAT);
	
	public static String getString(String val) {
		if(DataValidator.isNotNull(val)) {
			return val.trim();
		}else {
			return val;
		}
	}
	
	public static String getStringData(Object val) {
		if(val!=null) {
			return val.toString();
		}else {
			return "";
		}
	}
	
	public static int getInt(String val) {
		if(DataValidator.isNotNull(val)){
			return Integer.parseInt(val);
		}else {
			return 0;
		}
	}
	
	public static long getLong(String val) {
		if(DataValidator.isNotNull(val)) {
			return Long.parseLong(val);
		}else {
			return 0;
		}
	}
	
	public static Date getDate(String val) {
		Date date=null;
		try {
			date = formatter.parse(val);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return date;
	}
	
	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
	public static Timestamp getTimestamp(String val) {
		Timestamp timestamp=null;
		try {
			timestamp=new Timestamp((timeFormatter.parse(val)).getTime());
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return timestamp;
	}
	
	public static Timestamp getTimestamp(long l) {
		Timestamp timestamp=null;
		try {
			timestamp = new Timestamp(l);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return timestamp;
	}
	
	public static Timestamp getCurrentTimeStamp() {
		Timestamp timestamp=null;
		try {
			timestamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return timestamp;
	}
	
	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
}
