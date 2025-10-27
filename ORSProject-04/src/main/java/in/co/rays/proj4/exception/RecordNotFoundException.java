package in.co.rays.proj4.exception;


/**
 * Record Not Found Exception occurs when
 * record is not found in database.
 * 
 * @author Nidhi
 * @version 1.0
 */
public class RecordNotFoundException extends Exception {
	
	public RecordNotFoundException(String msg) {
		super(msg);
	} 
}
