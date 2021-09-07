package Exceptions;

public class OrderHistoryException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderHistoryException() {
		super("There are no orders in the order history");
	}

}
