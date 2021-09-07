package controller;

import java.util.TimerTask;

import Model.Customer;
import Model.Delivery;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import application.Main;

public class IsDeliver extends TimerTask{

	
	@Override
	public void run() {
		
//		System.out.println("IsDeliver");
		if (Main.getRest().getNotDeliveredYet() == null) 
			Main.getRest().resetNotDeliveredYet();

		for (Delivery delivery : Main.getRest().getNotDeliveredYet()) {
			delivery.setDelivered(true);
			
			// Update Order History
			if (delivery instanceof ExpressDelivery) {
				Customer currentCust = ( (ExpressDelivery) delivery).getOrder().getCustomer();
				if (currentCust.getOrdersHistory() == null)
					currentCust.resetOrderHistory();

				currentCust.getOrdersHistory().add(( (ExpressDelivery) delivery).getOrder());
			}
			else { // Regular Delivery
				for (Order o : ((RegularDelivery) delivery).getOrders()) {
					Customer currentCust = o.getCustomer();
					if (currentCust.getOrdersHistory() == null)
						currentCust.resetOrderHistory();

					currentCust.getOrdersHistory().add(o);
				}
			}
			System.out.println("IsDeliverDone");
		}
		Main.getRest().resetNotDeliveredYet();

		
	}

}
