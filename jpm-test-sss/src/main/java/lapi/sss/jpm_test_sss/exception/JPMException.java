package lapi.sss.jpm_test_sss.exception;

import org.apache.log4j.Logger;



public class JPMException extends Exception {
	private static final long serialVersionUID = 3086979688584889377L;

	final static Logger log = Logger.getLogger(JPMException.class);
	
	private String message;

	public JPMException(String message) {
		this.message = message;
		
		log.error(message+": "+message);
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	
	
}
