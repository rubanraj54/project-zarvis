package zarvis.bakery.behaviors.bakery;

import java.util.Map;
import java.util.TreeMap;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import zarvis.bakery.models.Bakery;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

public class ProcessOrderBehaviour extends CyclicBehaviour {
	private Map<Integer, String> orderAggregation = new TreeMap<Integer, String>();
	private Bakery bakery;
	
	public ProcessOrderBehaviour(Bakery bakery) {
		this.bakery = bakery;
	}
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

				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("KneedingMachineManager");
				template.addServices(sd);

				try {
					DFAgentDescription[] result = DFService.search(myAgent, template);
					AID[] kneedingMachineManagerAgent = (new AID[result.length]);
					
					AID kneedingmachinemanager = result[0].getName();

					// Send the cfp (call for proposal) to all sellers
					ACLMessage cfp = new ACLMessage(ACLMessage.INFORM);
					cfp.addReceiver(kneedingmachinemanager);

					cfp.setContent(orderID);
					cfp.setConversationId("inform-product-to-kneeding-machine-manager");
					cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
					myAgent.send(cfp);

				} catch (FIPAException fe) {
					fe.printStackTrace();
				}

			}				
		}
		catch (Exception e) {e.printStackTrace(); }			
	}
}
