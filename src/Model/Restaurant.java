package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import Exceptions.ConvertToExpressException;
//import Exceptions.IllegalCustomerException;
import Exceptions.NoComponentsExceptions;
import Exceptions.SensitiveException;
import Utils.Expertise;
import Utils.MyFileLogWriter;
import Utils.Neighberhood;


public class Restaurant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Restaurant restaurant = null;

	private HashMap<Integer, Cook> cooks;
	private HashMap<Integer, DeliveryPerson> deliveryPersons;
	private HashMap<Integer, Customer> customers;
	private HashMap<String, Dish> dishes;
	private HashMap<String, Component> componenets;
	private HashMap<Integer, Order> orders;
	private HashMap<Integer, Delivery> deliveries;
	private HashMap<String, DeliveryArea> areas;

	/*NEW*/
	private HashMap<Customer, TreeSet<Order>> orderByCustomer; //-- Reomve
	private HashMap<DeliveryArea, HashSet<Order>> orderByDeliveryArea; //-- ?
	private HashSet<Customer> blackList;
	
	// New For Gui
	private HashSet<String> userNames; //-- �����
	
	// New For GUI
	private HashMap<String, String> forgotMyPassWord;
	private HashMap<String, Integer> getIdByUserCust;
	private HashMap<String, Integer> getIdByUserCook; //-- Maybe change - if not use
	private HashMap<String, Integer> getIdByUserDP; //-- Maybe change - if not use
	
	// New For GUI
	private HashMap<DeliveryArea, TreeSet<Order>> AI_machine;
	
	private HashSet<Delivery> notDeliveredYet;
	
	private HashSet<Neighberhood> neighsInRest;

	
	public static Restaurant getInstance()
	{
		if(restaurant == null)
			restaurant = new Restaurant();
		return restaurant;
	}

	private Restaurant() {
		cooks = new HashMap<>();
		deliveryPersons = new HashMap<>();
		customers = new HashMap<>();
		dishes = new HashMap<>();
		componenets = new HashMap<>();
		orders = new HashMap<>();
		deliveries = new HashMap<>();
		areas = new HashMap<>();
		orderByCustomer = new HashMap<>();
		orderByDeliveryArea = new HashMap<>();
		blackList = new HashSet<>();
		
		// New For GUI
		userNames = new HashSet<>();
		getIdByUserCust = new HashMap<>();
		getIdByUserCook = new HashMap<>();
		getIdByUserDP = new HashMap<>();
		forgotMyPassWord = new HashMap<>();
		
		AI_machine = new HashMap<>();
		notDeliveredYet = new HashSet<>();
		
		neighsInRest = new HashSet<>();
	}

	// New For GUI
	public HashSet<String> getUserNames() {
		return userNames;
	}
	public void resetUserNames() {
		userNames = new HashSet<>();
	}
	public HashMap<String, Integer> getGetIdByUserCust() {
		return getIdByUserCust;
	}
	public HashMap<String, Integer> getGetIdByUserCook() {
		return getIdByUserCook;
	}
	public HashMap<String, Integer> getGetIdByUserDP() {
		return getIdByUserDP;
	}
	public HashMap<String, String> getForgotMyPassWord() {
		return forgotMyPassWord;
	}

	

	// ---------------------------------------
	

	public HashMap<Integer, Cook> getCooks() {
		return cooks;
	}

	public void setCooks(HashMap<Integer, Cook> cooks) {
		this.cooks = cooks;
	}

	public HashMap<Integer, DeliveryPerson> getDeliveryPersons() {
		return deliveryPersons;
	}

	public void setDeliveryPersons(HashMap<Integer, DeliveryPerson> deliveryPersons) {
		this.deliveryPersons = deliveryPersons;
	}

	public HashMap<Integer, Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(HashMap<Integer, Customer> customers) {
		this.customers = customers;
	}

	public HashMap<String, Dish> getDishes() {
		return dishes;
	}

	public void setDishes(HashMap<String, Dish> dishes) {
		this.dishes = dishes;
	}

	public HashMap<String, Component> getComponenets() {
		return componenets;
	}

	public void setComponenets(HashMap<String, Component> componenets) {
		this.componenets = componenets;
	}

	public HashMap<Integer, Order> getOrders() {
		return orders;
	}

	public void setOrders(HashMap<Integer, Order> orders) {
		this.orders = orders;
	}

	public HashMap<Integer, Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(HashMap<Integer, Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public HashMap<String, DeliveryArea> getAreas() {
		return areas;
	}

	public void setAreas(HashMap<String, DeliveryArea> areas) {
		this.areas = areas;
	}

	public HashMap<Customer, TreeSet<Order>> getOrderByCustomer() {
		return orderByCustomer;
	}

	public void setOrderByCustomer(HashMap<Customer, TreeSet<Order>> orderByCustomer) {
		this.orderByCustomer = orderByCustomer;
	}

	public HashMap<DeliveryArea, HashSet<Order>> getOrderByDeliveryArea() {
		return orderByDeliveryArea;
	}

	public void setOrderByDeliveryArea(HashMap<DeliveryArea, HashSet<Order>> orderByDeliveryArea) {
		this.orderByDeliveryArea = orderByDeliveryArea;
	}

	public HashSet<Customer> getBlackList() {
		return blackList;
	}

	public void setBlackList(HashSet<Customer> blackList) {
		this.blackList = blackList;
	}
	
	// New
	public HashMap<DeliveryArea, TreeSet<Order>> getAI_machine() {
		return AI_machine;
	}
	public void resetAI_Machine() {
		AI_machine = new HashMap<>();
	}
	public void resetOrdersIn_AI_Machine(DeliveryArea da) {
		TreeSet<Order> orders = new TreeSet<>();
		AI_machine.put(da, orders);
	}
	
	// New
	public HashSet<Delivery> getNotDeliveredYet() {
		return notDeliveredYet;
	}
	public void resetNotDeliveredYet() {
		notDeliveredYet = new HashSet<>();
	}
	
	// New
	public HashSet<Neighberhood> getNeighsInRest() {
		return neighsInRest;
	}
	public void resetNeighsInRest() {
		neighsInRest = new HashSet<>();
	}




	public boolean addCook(Cook cook) {
		if(cook == null || getCooks().containsKey(cook.getId()))
			return false;
		
		// New For GUI
		if (getIdByUserCook == null)
			getIdByUserCook = new HashMap<>();
		if (forgotMyPassWord == null)
			forgotMyPassWord = new HashMap<>();
		getIdByUserCook.put(cook.getUserName(), cook.getId());
		forgotMyPassWord.put(cook.getUserName() + cook.getBirthDay().toString(), cook.getPassword());

		return getCooks().put(cook.getId(), cook) == null;
	}


	public boolean addDeliveryPerson(DeliveryPerson dp, DeliveryArea da) {
		if(dp == null || getDeliveryPersons().containsKey(dp.getId()) || !getAreas().containsKey(da.getAreaName()))
			return false;
		
		// New For GUI
		if (getIdByUserDP == null)
			getIdByUserDP = new HashMap<>();
		if (forgotMyPassWord == null)
			forgotMyPassWord = new HashMap<>();
		getIdByUserDP.put(dp.getUserName(), dp.getId());
		forgotMyPassWord.put(dp.getUserName() + dp.getBirthDay().toString(), dp.getPassword());

		return deliveryPersons.put(dp.getId() , dp) ==null && da.addDelPerson(dp);
	}

	public boolean addCustomer(Customer cust) {
		if(cust == null || getCustomers().containsKey(cust.getId())) //  
			return false;
		
		// New For GUI
		if (getIdByUserCust == null)
			getIdByUserCust = new HashMap<>();
		if (forgotMyPassWord == null)
			forgotMyPassWord = new HashMap<>();
		getIdByUserCust.put(cust.getUserName(), cust.getId());
		forgotMyPassWord.put(cust.getUserName() + cust.getBirthDay().toString(), cust.getPassword());
		
		
		return getCustomers().put(cust.getId(), cust) ==null;
	}

	public boolean addDish(Dish dish) {
		if(dish == null || getDishes().containsKey(dish.getDishName()))
			return false;
		for(Component c : dish.getComponenets()) {
			if(!getComponenets().containsKey(c.getComponentName()))
				return false;
		}

		return getDishes().put(dish.getDishName(), dish) ==null;
	}

	public boolean addComponent(Component comp) {
		if(comp == null || getComponenets().containsKey(comp.getComponentName()))
			return false;

		return getComponenets().put(comp.getComponentName(), comp) ==null;
	}

	public boolean addOrder(Order order) throws SensitiveException {
//		try {
			if(order == null || getOrders().containsKey(order.getId()))
				return false;
			if(order.getCustomer() == null || !getCustomers().containsKey(order.getCustomer().getId()))
				return false;
//			if(getBlackList().contains(order.getCustomer())) {
//				throw new IllegalCustomerException();
//			}
			for(Dish d : order.getDishes()){
				if(!getDishes().containsKey(d.getDishName()))
					return false;
				for(Component c: d.getComponenets()) {
					if(order.getCustomer().isSensitiveToGluten() && c.isHasGluten()) {
						throw new SensitiveException(order.getCustomer().getFirstName() + " " +order.getCustomer().getLastName(), d.getDishName());
					}
					else if(order.getCustomer().isSensitiveToLactose() && c.isHasLactose()) {
						throw new SensitiveException(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(), d.getDishName());
					}
				}
			}
//			if (order.getCustomer().getOrdersHistory() == null)
//				order.getCustomer().resetOrderHistory();
// 			order.getCustomer().getOrdersHistory().add(order); // New For GUI
			return getOrders().put(order.getId(), order) == null;
//		}
//		catch(SensitiveException e) {
//			Utils.MyFileLogWriter.println(e.getMessage());
//			return false;
//		}catch(IllegalCustomerException e) {
//			Utils.MyFileLogWriter.println(e.getMessage());
//			return false;
//		}
	}

	public boolean addDelivery(Delivery delivery) {
		try {
			if(delivery == null || getDeliveries().containsKey(delivery.getId()) || !getDeliveryPersons().containsKey(delivery.getDeliveryPerson().getId()))
			{
				return false;
			}
			if(delivery instanceof RegularDelivery) {
				RegularDelivery rd = (RegularDelivery)delivery;
				for(Order o : rd.getOrders()){
					if(!getOrders().containsKey(o.getId()))
						return false;
					o.setDelivery(delivery);
				}
				if(rd.getOrders().size() ==1) {
					throw new ConvertToExpressException();
				}
				if(rd.getOrders().isEmpty())
					return false;
			}
			else {
				ExpressDelivery ed = (ExpressDelivery)delivery;
				if(!getOrders().containsKey(ed.getOrder().getId()))
						return false;
					ed.getOrder().setDelivery(delivery);
			}
		}catch(ConvertToExpressException e) {
//			Utils.MyFileLogWriter.println(e.getMessage());
			RegularDelivery rd = (RegularDelivery)delivery;
			delivery = new ExpressDelivery(rd.getDeliveryPerson(), rd.getArea(),rd.isDelivered(), rd.getOrders().first() ,rd.getDeliveredDate());
		}finally {
			delivery.getArea().addDelivery(delivery);
			if(delivery instanceof RegularDelivery) {
//				RegularDelivery rg = (RegularDelivery)delivery;
//				for(Order o: rg.getOrders()) {
//					TreeSet<Order> orders = orderByCustomer.get(o);
//					if(orders == null)
//						orders = new TreeSet<>(new Comparator<Order>() {
//
//							@Override
//							public int compare(Order o1, Order o2) {
//								return o1.getDelivery().getDeliveredDate().compareTo(o2.getDelivery().getDeliveredDate());
//							}
//						});
//					orders.add(o);
//					orderByCustomer.put(o.getCustomer(), orders);
//				}
			}
			else {
//				ExpressDelivery ex = (ExpressDelivery)delivery;
//				TreeSet<Order> orders = orderByCustomer.get(ex.getOrder());
//				if(orders == null)
//					orders = new TreeSet<>(new Comparator<Order>() {
//
//						@Override
//						public int compare(Order o1, Order o2) {
//							return o1.getDelivery().getDeliveredDate().compareTo(o2.getDelivery().getDeliveredDate());
//						}
//					});
//				orders.add(ex.getOrder());
//				orderByCustomer.put(ex.getOrder().getCustomer(), orders);
			}
		}
		return getDeliveries().put(delivery.getId(),delivery) ==null;
	}

	public boolean addDeliveryArea(DeliveryArea da) {
		if(da == null || getAreas().containsKey(da.getAreaName()))
			return false;
		
		// New For GUI
		if (AI_machine == null)
			AI_machine = new HashMap<>();
		AI_machine.put(da, null);
		
		if (neighsInRest == null)
			resetNeighsInRest();
		neighsInRest.addAll(da.getNeighberhoods()); // New For GUI
		
		return getAreas().put(da.getAreaName(), da) ==null;
	}
	
	public boolean addCustomerToBlackList(Customer c) {
		if(c == null)
			return false;
		return getBlackList().add(c);
	}

	public boolean removeCook(Cook cook) {
		if(cook == null || !getCooks().containsKey(cook.getId()))
			return false;
		return getCooks().remove(cook.getId()) !=null;
	}

	public boolean removeDeliveryPerson(DeliveryPerson dp) {
		if(dp == null || !getDeliveryPersons().containsKey(dp.getId()))
			return false;
		for(Delivery d : getDeliveries().values()) {
			if(d.getDeliveryPerson().equals(dp)) {
				d.setDeliveryPerson(null);
			}
		}
		return getDeliveryPersons().remove(dp.getId())!= null && dp.getArea().removeDelPerson(dp);
	}

	public boolean removeCustomer(Customer cust) {
		if(cust == null || !getCustomers().containsKey(cust.getId()))
			return false;
		
		getIdByUserCust.remove(cust.getUserName());
		return getCustomers().remove(cust.getId())!=null;
	}

	public boolean removeDish(Dish dish) {
		if(dish == null || !getDishes().containsKey(dish.getDishName()))
			return false;
		for(Delivery d : deliveries.values()) {
			if(!d.isDelivered()) {
				if(d instanceof RegularDelivery) {
					RegularDelivery rd = (RegularDelivery)d;
					for(Order o : rd.getOrders()) {
						o.removeDish(dish);
					}
				}
				else {
					ExpressDelivery ed = (ExpressDelivery)d;
					ed.getOrder().removeDish(dish);
				}
			}
		}
		return getDishes().remove(dish.getDishName())!=null;
	}

	public boolean removeComponent(Component comp) {
		Dish removeDish = null;
		try {
			if(comp == null || !getComponenets().containsKey(comp.getComponentName()))
				return false;
			for(Dish d : getDishes().values()) {
				d.removeComponent(comp);
				if(d.getComponenets().isEmpty()) {
					removeDish = d;
					throw new NoComponentsExceptions(d.getDishName());
				}
			}
		}catch(NoComponentsExceptions e) {
//			Utils.MyFileLogWriter.println(e.getMessage());
			removeDish(removeDish);
		}
		return getComponenets().remove(comp.getComponentName())!=null;
	}

	public boolean removeOrder(Order order) {
		if(order == null || !getOrders().containsKey(order.getId()))
			return false;
		if(getOrders().remove(order.getId())!=null) {
			if(order.getDelivery() instanceof RegularDelivery) {
				RegularDelivery rd = (RegularDelivery) order.getDelivery();
				
				if (!order.getDelivery().isDelivered()) // New For GUI
					order.getCustomer().getOrdersHistory().remove(order);
				
				return rd.removeOrder(order);
			}
			else {
				ExpressDelivery ed = (ExpressDelivery) order.getDelivery();
				ed.setOrder(null);
				
				if (!order.getDelivery().isDelivered()) // New For GUI
					order.getCustomer().getOrdersHistory().remove(order);

				return true;
			}
		}
		return false;
	}

	public boolean removeDelivery(Delivery delivery) {
		if(delivery == null || !getDeliveries().containsKey(delivery.getId()))
			return false;
		if(delivery instanceof RegularDelivery) {
			RegularDelivery rd = (RegularDelivery)delivery;
			for(Order o : rd.getOrders()) {
				o.setDelivery(null);
			}
		}
		else {
			ExpressDelivery ed = (ExpressDelivery) delivery;
			ed.getOrder().setDelivery(null);
		}
		return getDeliveries().remove(delivery.getId()) != null && delivery.getArea().removeDelivery(delivery);
	}

	public boolean removeDeliveryArea(DeliveryArea oldArea, DeliveryArea newArea) {
		if(oldArea == null || newArea == null || !getAreas().containsKey(oldArea.getAreaName()) || !getAreas().containsKey(newArea.getAreaName()))
			return false;
		for(Neighberhood n : oldArea.getNeighberhoods()) {
			newArea.addNeighberhood(n);
		}
		for(Delivery d : oldArea.getDelivers()) {
			newArea.addDelivery(d);
		}
		for(DeliveryPerson dp : oldArea.getDelPersons()) {
			newArea.addDelPerson(dp);
		}
		for(DeliveryPerson dp : oldArea.getDelPersons()) {
			dp.setArea(newArea);
		}
		for(Delivery d : oldArea.getDelivers()) {
			d.setArea(newArea);
		}
		
		// New For GUI
		TreeSet<Order> ordersAI = new TreeSet<>();
		if (AI_machine.get(oldArea) != null) {
			ordersAI = AI_machine.get(oldArea);
			AI_machine.get(newArea).addAll(ordersAI);
			AI_machine.put(newArea, AI_machine.get(newArea));
		}
		
		return getAreas().remove(oldArea.getAreaName()) != null;
	}

	public Cook getRealCook(int id) {
		return getCooks().get(id);
	}

	public DeliveryPerson getRealDeliveryPerson(int id) {
		return getDeliveryPersons().get(id);
	}

	public Customer getRealCustomer(int id) {
		return getCustomers().get(id);
	}

	public Dish getRealDish(String str) {
		return getDishes().get(str);
	}

	public Component getRealComponent(String str) {
		return getComponenets().get(str);
	}

	public Order getRealOrder(int id) {
		return getOrders().get(id);
	}

	public Delivery getRealDelivery(int id) {
		return getDeliveries().get(id);
	}

	public DeliveryArea getRealDeliveryArea(String name) {
		return getAreas().get(name);
	}


	/*QUEREIES*/
	public Collection<Dish> getReleventDishList(Customer c){
		ArrayList<Dish> dishList = new ArrayList<>();
		if(!c.isSensitiveToGluten() && !c.isSensitiveToLactose())
			return getDishes().values();
		for(Dish d : getDishes().values()) {
			boolean isValid = true;
			for(Component comp : d.getComponenets()) {
				if(c.isSensitiveToGluten() && c.isSensitiveToLactose()) {
					if(comp.isHasGluten() || comp.isHasLactose())
						isValid = false;
				}
				else if(c.isSensitiveToGluten() && comp.isHasGluten()) {
					isValid = false;
				}
				else if(c.isSensitiveToLactose() && comp.isHasLactose()) {
					isValid = false;
				}
			}
			if(isValid)
				dishList.add(d);
		}
		return dishList;
	}
	
	public void deliver(Delivery d) {
		d.setDelivered(true);
		if(d instanceof RegularDelivery) {
			RegularDelivery rd = (RegularDelivery)d;
			for(Order o : rd.getOrders()) {
				MyFileLogWriter.println(o+" had reached to Customer "+o.getCustomer());
			}
		}
		else {
			ExpressDelivery ed = (ExpressDelivery)d;
			MyFileLogWriter.println(ed.getOrder()+" had reached to Customer "+ed.getOrder().getCustomer());
		}
		
	}
	
	public ArrayList<Cook> GetCooksByExpertise(Expertise e){
		ArrayList<Cook> cooks = new ArrayList<>();
		for(Cook c : getCooks().values()) {
			if(c.getExpert().equals(e))
				cooks.add(c);
		}
		return cooks;
	}
	
	/*NEW QUERIES*/
	public TreeSet<Delivery> getDeliveriesByPerson(DeliveryPerson dp , int month){
		TreeSet<Delivery> delivered = new TreeSet<>(new Comparator<Delivery>() {

			@Override
			public int compare(Delivery o1, Delivery o2) {
				if(o1.getDeliveredDate().getDayOfMonth() > o2.getDeliveredDate().getDayOfMonth())
					return 1;
				if(o1.getDeliveredDate().getDayOfMonth() < o2.getDeliveredDate().getDayOfMonth())
					return -1;
				return o1.getId()>o2.getId() ? 1 :-1;
			}
		});
		for(Delivery d: getDeliveries().values()) {
			if(d.getDeliveryPerson().equals(dp) && d.getDeliveredDate().getMonthValue() == month)
				delivered.add(d);
		}
		return delivered;
	}
	
	public HashMap<String,Integer> getNumberOfDeliveriesPerType(){
		HashMap<String, Integer> deliveriesPerType = new HashMap<>();
		deliveriesPerType.put("Regular delivery", 0);
		deliveriesPerType.put("Express delivery", 0);
		int amount;
		for(Delivery d: getDeliveries().values()) {
			LocalDate today = LocalDate.now();
			if(d instanceof RegularDelivery && d.getDeliveredDate().getYear() == today.getYear()) {
				amount = deliveriesPerType.get("Regular delivery");
				deliveriesPerType.put("Regular delivery",amount+1);
			}
			else {
				if(d.getDeliveredDate().getYear() == today.getYear()) {
					amount = deliveriesPerType.get("Express delivery");
					deliveriesPerType.put("Express delivery",amount+1);
				}
			}
		}
		return deliveriesPerType;
	}
	
	public double revenueFromExpressDeliveries() {
		HashSet<Customer> customers = new HashSet<>();
		double amountOfPostages = 0;
		for(Delivery d: getDeliveries().values()) {
			if(d instanceof ExpressDelivery) {
				ExpressDelivery ed = (ExpressDelivery)d;
				amountOfPostages+=ed.getPostage();
				customers.add(ed.getOrder().getCustomer());
			}
		}
		amountOfPostages+=(30*customers.size());
		return amountOfPostages;
	}
	
	private HashMap<Component, Integer> componentsandAmount;
	public HashMap<Component, Integer> getComponentsandAmount() {
		return componentsandAmount;
	}
	public void resetCompsAmount() {
		componentsandAmount = new HashMap<>();
	}

	public LinkedList<Component> getPopularComponents(){
		componentsandAmount = new HashMap<>();
		for(Dish d: getDishes().values()) {
			for(Component c: d.getComponenets()) {
				Integer numOfComponent = componentsandAmount.get(c);
				if(numOfComponent == null)
					numOfComponent = 0;
				componentsandAmount.put(c, numOfComponent+1);
			}
		}
		LinkedList<Component> popularComponents = new LinkedList<>();
		for(Component c: componentsandAmount.keySet()) {
			popularComponents.add(c);
		}
		popularComponents.sort(new Comparator<Component>() {

			@Override
			public int compare(Component o1, Component o2) {
				int result = -1 * componentsandAmount.get(o1).compareTo(componentsandAmount.get(o2));
				if(result !=0)
					return result;
				if(o1.getId() > o2.getId())
					return -1;
				return 1;
			}
		});
		return popularComponents;
	}
	
	public TreeSet<Dish> getProfitRelation(){
		TreeSet<Dish> profit = new TreeSet<Dish>((Dish o1, Dish o2) -> {
			if((o2.getPrice()/o2.getTimeToMake())>(o1.getPrice()/o1.getTimeToMake()))
				return 1;
			else if((o2.getPrice()/o2.getTimeToMake())<(o1.getPrice()/o1.getTimeToMake()))
				return -1;
			return -1*o1.getId().compareTo(o2.getId());
		});
		for(Dish d: getDishes().values()) {
			profit.add(d);
		}
		return profit;
	}
	
	public TreeSet<Delivery> createAIMacine(DeliveryPerson dp, DeliveryArea da, TreeSet<Order> orders){
		TreeSet<Delivery> AIDecision = new TreeSet<>(new Comparator<Delivery>() {

			@Override
			public int compare(Delivery o1, Delivery o2) {
				if(o2 instanceof RegularDelivery && o1 instanceof ExpressDelivery)
					return -1;
				if(o2 instanceof ExpressDelivery && o1 instanceof RegularDelivery)
					return 1;
				return o2.getId()>o1.getId() ? -1: 1;
			}
		});
		TreeSet<Order> toRegularDelivery = new TreeSet<>();
		if(orders.size()<=2) {
			for(Order o: orders) {
				ExpressDelivery ed = new ExpressDelivery(dp, da, false, o,LocalDate.of(2021,1,1));
				AIDecision.add(ed);
			}
		}
		else {
			for(Order o: orders) {
				if(o.getCustomer().isSensitiveToGluten() || o.getCustomer().isSensitiveToLactose()) {
					ExpressDelivery ed = new ExpressDelivery(dp, da, false, o,LocalDate.of(2021,1,1));
					AIDecision.add(ed);
				}
				else
					toRegularDelivery.add(o);
			}
			if(toRegularDelivery.size()<2) {
				ExpressDelivery ed = new ExpressDelivery(dp, da, false, toRegularDelivery.first(),LocalDate.of(2021, 1, 1));
				AIDecision.add(ed);
			}
			else {
				RegularDelivery rd = new RegularDelivery(toRegularDelivery, dp, da, false, LocalDate.of(2021, 1, 1));
				AIDecision.add(rd);
			}
		}
		return AIDecision;
	}
	
	
//	// Serialization
//	public void serialize() {
//		try {
//	         FileOutputStream fileOut = new FileOutputStream("Album.ser");//name of the folder we create
//	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
//	         out.writeObject(this);
//	         out.close();
//	         fileOut.close();
//	         System.out.println("The song list was serialized successfully");
//	      } catch (IOException i) {
//	         i.printStackTrace();
//	      }		
//	}
//	
//	// Serialization
//	public void serialize() {
//		try {
//			FileOutputStream fileOut = new FileOutputStream("Rest.ser"); // Name Of File
//			ObjectOutputStream out = new ObjectOutputStream(fileOut);
//			out.writeObject(this);
//			out.close();
//			fileOut.close();
//			System.out.println("Rest was serialized successfully");
//		} catch (IOException i) {
//			i.printStackTrace();
//		}
//	}
//	
//	// DeSerialization
//	public void deserialize()
//	{
//		Restaurant r;
//		 try {
//	         FileInputStream fileIn = new FileInputStream("Rest.ser");
//	         ObjectInputStream in = new ObjectInputStream(fileIn);
//	         r = (Restaurant) in.readObject();
//	         in.close();
//	         fileIn.close();
//	         System.out.println("The 'Rest' was deserialized successfully");
//	      } catch (IOException i) {
//	         i.printStackTrace();
//	         System.out.println("DeSerialization not succedded");
//	         return;
//	      } catch (ClassNotFoundException c) {
//	         System.out.println("Resturant class not found");
//	         System.out.println("DeSerialization not succedded");
//	         c.printStackTrace();
//	         return;
//	      }
//	}


}
