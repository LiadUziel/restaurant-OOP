package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.TreeSet;

import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import application.Main;

public class AI_Machine_Timer extends TimerTask{

	@Override
	public void run() {
//		System.out.println("CheckTime");
		if (Main.getRest().getAI_machine() == null)
			Main.getRest().resetAI_Machine();
		for (DeliveryArea tempDA : Main.getRest().getAI_machine().keySet()) {
			if (Main.getRest().getAI_machine().get(tempDA) == null)
				Main.getRest().resetOrdersIn_AI_Machine(tempDA);
			
			// One order - Express
			if (Main.getRest().getAI_machine().get(tempDA).size() == 1) {
				if (ShoppingCartController.getDpDeliveryOrder() == tempDA.getDelPersons().size()) // Reset DP if his is the last
					ShoppingCartController.setDpDeliveryOrder(0);
				ArrayList<DeliveryPerson> dPersons = new ArrayList<>();
				dPersons.addAll(tempDA.getDelPersons());

				Order o = Main.getRest().getAI_machine().get(tempDA).first();
				
				ExpressDelivery newExpress = new ExpressDelivery(dPersons.get(ShoppingCartController.getDpDeliveryOrder()), tempDA, false, o, 0, LocalDate.now());
				Main.getRest().addDelivery(newExpress);
				
				
	    		if (Main.getRest().getNotDeliveredYet() == null)
	    			Main.getRest().resetNotDeliveredYet();
				Main.getRest().getNotDeliveredYet().add(newExpress);
				
				TreeSet<Order> resetOrdersAI = new TreeSet<>();
				Main.getRest().getAI_machine().put(tempDA, resetOrdersAI);

				System.out.println("New Express from AI Machine");

//				System.out.println("sucesdsdsfffddd 1");
			}
			
			// Two orders - Regular
			else if (Main.getRest().getAI_machine().get(tempDA).size() == 2) {
				if (ShoppingCartController.getDpDeliveryOrder() == tempDA.getDelPersons().size()) // Reset DP if his is the last
					ShoppingCartController.setDpDeliveryOrder(0);
				ArrayList<DeliveryPerson> dPersons = new ArrayList<>();
				dPersons.addAll(tempDA.getDelPersons());
				
				TreeSet<Order> ordersOfDA = Main.getRest().getAI_machine().get(tempDA);
				
				RegularDelivery newRegular = new RegularDelivery(ordersOfDA, dPersons.get(ShoppingCartController.getDpDeliveryOrder()), tempDA, false, LocalDate.now());
				Main.getRest().addDelivery(newRegular);
				
	    		if (Main.getRest().getNotDeliveredYet() == null)
	    			Main.getRest().resetNotDeliveredYet();
				Main.getRest().getNotDeliveredYet().add(newRegular);
				TreeSet<Order> resetOrdersAI = new TreeSet<>();
				Main.getRest().getAI_machine().put(tempDA, resetOrdersAI);

				System.out.println("New Regular from AI Machine");

//				System.out.println("suceedd - 2");
			}
		}
	}

}
