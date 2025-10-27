package in.co.rays.proj4.util;

import java.util.Calendar;
import java.util.Date;

/**
 * DataValidator class is contains functions 
 * that perform input validations
 *  on the data entered by user in textbox.
 *  
 * @author Nidhi
 * @version 1.0
 */
public class DataValidator {

	public static boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	public static boolean isInteger(String val) {

		if (isNotNull(val)) {
			try {
				Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				// TODO: handle exception
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isLong(String val) {

		if (isNotNull(val)) {
			try {
				Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				// TODO: handle exception
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isDate(String val) {

		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);
		}
		return d != null;
	}

	public static boolean isEmail(String val) {
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isName(String val) {
		String namereg = "^[^-\\s][\\p{L} .'-]+$";
		if (isNotNull(val)) {
			try {
				return val.matches(namereg);
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isRollNo(String val) {
		String rollreg = "[a-zA-Z]{2}[0-9]{3}";
		if (isNotNull(val)) {
			try {
				return val.matches(rollreg);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isPassword(String val) {

		String passreg = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}";
		if (isNotNull(val)) {
			try {
				return val.matches(passreg);
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isPasswordLength(String val) {
		if (isNotNull(val) && val.length() >= 8 && val.length() < 12) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPhoneNo(String val) {

		String phonereg = "^[6-9][0-9]{9}$";

		if (isNotNull(val)) {
			try {
				return val.matches(phonereg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}
	}

	public static boolean isPhoneLength(String val) {
		if (isNotNull(val) && val.length() == 10) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isSunday(String val) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(DataUtility.getDate(val));
		int i = cal.get(Calendar.DAY_OF_WEEK);

		if (i == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}
}
