package Exceptions;

public class InvalidPassword extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPassword() {
		super("The password's length must be at least 6 letters"
				+ "\nThe password must conatain capital letter, digit and punctuation");
	}

}
