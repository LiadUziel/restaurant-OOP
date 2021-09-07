package Exceptions;

public class ConvertToExpressException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConvertToExpressException() {
		super("This regular delivery contain one order, please replace it to express delivery");
	}
	
}
