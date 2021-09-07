package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;

import Utils.Gender;
import Utils.Neighberhood;

public class Customer extends Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int idCounter = 1;
	private Neighberhood neighberhood;
	private boolean isSensitiveToLactose;
	private boolean isSensitiveToGluten;
	
	private String reasonBlackList;
	private HashSet<Order> ordersHistory;
	
	
	public Customer(String firstName, String lastName, LocalDate birthDay, Gender gender, String userName, String password,
			Neighberhood neighberhood,boolean isSensitiveToLactose, boolean isSensitiveToGluten) {
		super(idCounter++, firstName, lastName, birthDay, gender, userName, password);
		this.neighberhood = neighberhood;
		this.isSensitiveToLactose = isSensitiveToLactose;
		this.isSensitiveToGluten = isSensitiveToGluten;
		
		this.reasonBlackList = "";
		ordersHistory = new HashSet<>();
	}
	
	public Customer(int id) {
		super(id);
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Customer.idCounter = idCounter;
	}

	public Neighberhood getNeighberhood() {
		return neighberhood;
	}

	public void setNeighberhood(Neighberhood neighberhood) {
		this.neighberhood = neighberhood;
	}

	public boolean isSensitiveToLactose() {
		return isSensitiveToLactose;
	}
	public boolean getIsSensitiveToLactose() {
		return isSensitiveToLactose;
	}

	public void setSensitiveToLactose(boolean isSensitiveToLactose) {
		this.isSensitiveToLactose = isSensitiveToLactose;
	}

	public boolean isSensitiveToGluten() {
		return isSensitiveToGluten;
	}
	public boolean getIsSensitiveToGluten() {
		return isSensitiveToGluten;
	}

	public void setSensitiveToGluten(boolean isSensitiveToGluten) {
		this.isSensitiveToGluten = isSensitiveToGluten;
	}
	
	
	public String getReasonBlackList() {
		return reasonBlackList;
	}
	public void setReasonBlackList(String reasonBlackList) {
		this.reasonBlackList = reasonBlackList;
	}
	public HashSet<Order> getOrdersHistory() {
		return ordersHistory;
	}
	
	public void resetOrderHistory() {
		ordersHistory = new HashSet<>();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
