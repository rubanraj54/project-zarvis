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
				String title = msg.getContent();
				//  get the orderID from the message
				String[] titleparts = title.split(" ");
				// store orderID
				String orderID = titleparts[0];
				Order order = Util.getWrapper().getOrderById(orderID);

				if(!bakery.hasAllProducts(order)){
					System.out.println("All products not found in our bakery " + bakery.getName());
					ACLMessage reply = msg.createReply(); 
					reply.setPerformative(ACLMessage.REFUSE); 
					reply.setContent("All products not found in our bakery " + bakery.getName()); 
					reply.setConversationId("customer request"); 
					System.out.println("order placed"); 
					myAgent.send(reply); 

				} else {
					// for available orders to be delivered on day 1 
					if ((order.getDelivery_date().getDay() == 1)){
						this.orderAggregation.put(order.getDelivery_date().getHour(), order.getGuid()); 
					}
					// for available orders to be delivered after day 1 
					else {
						int time = order.getDelivery_date().getHour() + (order.getDelivery_date().getDay()-1)* 24;
						this.orderAggregation.put(time, order.getGuid()); 
					}

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
						cfp.setReplyWith("inform"+System.currentTimeMillis()); // Unique value
						myAgent.send(cfp);

					} catch (FIPAException fe) {
						fe.printStackTrace();
					}
				}

			}				
		}
		catch (Exception e) {e.printStackTrace(); }			
	}
}
