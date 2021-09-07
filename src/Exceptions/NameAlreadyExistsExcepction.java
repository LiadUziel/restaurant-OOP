package Exceptions;

public class NameAlreadyExistsExcepction extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameAlreadyExistsExcepction() {
		super("This name is already exists in the restaurant\nPlease choose another name");
	}
	
	

}
