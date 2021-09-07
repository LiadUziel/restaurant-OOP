package Exceptions;

public class IllegalCustomerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalCustomerException() {
		super("The restaurant is in conflict with this customer so this customer does not will order a new order!");
	}

	
}
