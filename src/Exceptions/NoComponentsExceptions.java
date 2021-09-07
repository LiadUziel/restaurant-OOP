package Exceptions;


public class NoComponentsExceptions extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoComponentsExceptions(String d) {
		super("You can't remove the last component from " + d);
		
	}
	
}
