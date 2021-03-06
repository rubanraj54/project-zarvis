package zarvis.bakery.agents;

import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.Agent;
import zarvis.bakery.behaviors.customer.RequestPerformerBehavior;
import zarvis.bakery.models.Customer;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

public class CustomerAgent extends Agent {
	
	private Logger logger = LoggerFactory.getLogger(CustomerAgent.class);
	private Customer customer;
	private List<Order> orders;
	private HashMap<String, Integer> orderAggregation = new HashMap<String, Integer>();

	
	public CustomerAgent(Customer customer) {
		this.customer = customer;
		this.orders = Util.getWrapper().getOrderByIdCustomer(customer.getGuid());
		for(Order order : orders){
			if (order.getOrder_date().getDay()==1)
				this.orderAggregation.put(order.getGuid(), order.getOrder_date().getHour()); 
			else{
				int time = order.getOrder_date().getHour() + (order.getOrder_date().getDay()-1)* 24;
				this.orderAggregation.put(order.getGuid(), time); 
			}
		}
		logger.info(orders.toString());
	}
	

	
	@Override
	protected void setup() {

		Util.registerInYellowPage(this,"Customer",customer.getGuid());
		logger.info("Hi I'm the Customer agent, my name is:"+this.getAID().getName());

		TreeMap<String, Integer> aggregatedOrders = Util.sortMapByValue(orderAggregation);

//		logger.info(aggregatedOrders.toString());

//		addBehaviour(new RequestPerformerBehavior(customer,aggregatedOrders));

//		int timeDiff =0;
//		for (final Entry<String, Integer> entry : entries) {
//
//			try {
//				Thread.sleep(entry.getValue()-timeDiff);
//				timeDiff = entry.getValue();
//			}
//			catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}
	
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" terminating.");
	}
}

