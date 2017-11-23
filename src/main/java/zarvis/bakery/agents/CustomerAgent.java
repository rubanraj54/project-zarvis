package zarvis.bakery.agents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import zarvis.bakery.behaviors.RequestPerformerCustomer;
import zarvis.bakery.models.Customer;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

public class CustomerAgent extends Agent {
	private Customer customer;
	private List<Order> orders;
	private HashMap<String, Integer> orderAggregation = new HashMap<String, Integer>();

	
	public CustomerAgent(Customer customer) {
		this.customer = customer;
		this.orders = Util.getWrapper().getOrderByIdCustomer(customer.getName());
		for(Order order : orders){
			if (order.getOrder_date().getDay()==1)
				this.orderAggregation.put(order.getGuid(), order.getOrder_date().getHour()); 
			else{
				int time = order.getOrder_date().getHour() + order.getOrder_date().getDay()* 24;
				this.orderAggregation.put(order.getGuid(), time); 
			}
		}
	}
	
	public List<Entry<String, Integer>> doAggregation(){
		 List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(this.orderAggregation.entrySet());
		 Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
			    public int compare(final Entry<String, Integer> e1, final Entry<String, Integer> e2) {
			      return e1.getValue().compareTo(e2.getValue());
			    }
		});
		 //System.out.println("size = "+entries.size());
		return entries;
	}
	
	@Override
	protected void setup() {
		System.out.println("Hi I'm the agent Customer my name is:"+this.getAID().getName());
		List<Entry<String, Integer>> entries = doAggregation();
		int timeDiff =0;
		for (final Entry<String, Integer> entry : entries) {
			try {
				Thread.sleep(entry.getValue()-timeDiff);
				timeDiff = entry.getValue();
				addBehaviour(new RequestPerformerCustomer(customer.getName(),entry.getKey()));
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" terminating.");
	}
}

