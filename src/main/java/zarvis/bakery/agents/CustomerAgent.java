package zarvis.bakery.agents;

import jade.core.Agent;
import zarvis.bakery.behaviors.FindAllBackeryByNameBehaviour;
import zarvis.bakery.models.Customer;

public class CustomerAgent extends Agent {
	private Customer customer;
	
	public CustomerAgent(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	protected void setup() {
		System.out.println("Hi I'm the agent Customer my name is:"+this.getAID().getName());
		addBehaviour(new FindAllBackeryByNameBehaviour(this,10000));			
	}
	
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" terminating.");
	}
}