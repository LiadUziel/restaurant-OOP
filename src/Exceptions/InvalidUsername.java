package Exceptions;

public class InvalidUsername extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidUsername() {
		super("This username is alredy exists");
	}

}
