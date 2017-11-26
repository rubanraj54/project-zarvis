package zarvis.bakery.agents;
import java.util.Map;
import java.util.TreeMap;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import zarvis.bakery.models.Bakery;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

public class BakeryAgent extends Agent {
	private Bakery bakery;
	public BakeryAgent(Bakery bakery){
		this.bakery= bakery;
	}

	@Override
	protected void setup() {
		System.out.println("....... Bakery "+this.getAID().getName());

		// Create agent description and set AID 
		DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.setName(getAID());

		// Create service description and set type and bakery name
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType("bakery");
		serviceDescription.setName(bakery.getName());

		// add the service description to this agent
		agentDescription.addServices(serviceDescription);

		// Now add this agent description to yellow pages, so that other agents can identify this agent
		try {
			DFService.register(this, agentDescription);
			System.out.println("Bakery agent is added to yellow pages");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		//addBehaviour(new BakeryBehaviour());
		addBehaviour(new ProcessOrderBehaviour());
	}
	public class ProcessOrderBehaviour extends CyclicBehaviour {
		private Map<Integer, String> orderAggregation = new TreeMap<Integer, String>();
		@Override
		public void action() {
			try{
				ACLMessage msg = myAgent.receive();
				if (msg != null) {
					System.out.println("=======================");
					String title = msg.getContent();
					//  get the orderID from the message
					String[] titleparts = title.split(" ");
					// store orderID
					String orderID = titleparts[0];
					Order order = Util.getWrapper().getOrderById(orderID);
					System.out.println(orderID);
					// check and print if the items ordered by the customers are available in the bakery
					System.out.println(bakery.hasAllProducts(order));			
					System.out.println("=======================");
					// for available orders to be delivered on day 1 
					if (bakery.hasAllProducts(order) && (order.getDelivery_date().getDay() == 1)){
						this.orderAggregation.put(order.getDelivery_date().getHour(), order.getGuid()); 
						System.out.println("1");
					}
					// for available orders to be delivered after day 1 
					else if(bakery.hasAllProducts(order)){
						int time = order.getDelivery_date().getHour() + (order.getDelivery_date().getDay()-1)* 24;
						this.orderAggregation.put(time, order.getGuid()); 
						System.out.println(order.getProducts());
					}
					System.out.println(orderAggregation);		
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					reply.setContent("request accepted");
					reply.setConversationId("customer request");
					myAgent.send(reply);
					}
				else{
					block();
				}				
			}
			catch (Exception e) {e.printStackTrace(); }			
		}
	}
	protected void takeDown() {}
}

