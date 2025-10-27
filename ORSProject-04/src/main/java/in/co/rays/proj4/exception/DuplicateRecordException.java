package in.co.rays.proj4.exception;


/**
 * Duplicate Record Exception occurs when 
 * duplicate record is being inserted into database.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class DuplicateRecordException extends Exception {
	
	public DuplicateRecordException(String msg) {
		super(msg);
	}
}
