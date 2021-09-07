package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.DeliveryType;

public abstract class Delivery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int idCounter = 1;
	private int id;
	private DeliveryPerson deliveryPerson;
	private DeliveryArea area;
	private boolean isDelivered;
	private LocalDate deliveredDate;
	private DeliveryType type;
	
	public Delivery(DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered,LocalDate diliveredDate, DeliveryType type) {
		super();
		this.id = idCounter++;
		this.deliveryPerson = deliveryPerson;
		this.area = area;
		this.isDelivered = isDelivered;
		this.deliveredDate = diliveredDate;
		this.type = type;
	}
	
	public Delivery(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public DeliveryPerson getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	public DeliveryArea getArea() {
		return area;
	}

	public void setArea(DeliveryArea area) {
		this.area = area;
	}

	public boolean isDelivered() {
		return isDelivered;
	}
	public boolean getIsDelivered() {
		return isDelivered;
	}


	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}
	

	public LocalDate getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(LocalDate deliveredDate) {
		this.deliveredDate = deliveredDate;
	}
	
	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Delivery.idCounter = idCounter;
	}


	public DeliveryType getType() {
		return type;
	}

	public void setType(DeliveryType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Delivery other = (Delivery) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (isDelivered)
			return type.toString() + ": " + id + ", has delivered";
		else
			return type.toString() + ": " + id + ", has not delivered yet";
	}

}
