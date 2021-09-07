package Exceptions;


public class SensitiveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SensitiveException(String customerName, String dishName) {
		super("Customer " + customerName + " is sensitive to one of the components in the dish " + dishName + "!");
		
	}
	
}
