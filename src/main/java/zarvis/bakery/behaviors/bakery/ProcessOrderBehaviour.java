package zarvis.bakery.behaviors.bakery;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import zarvis.bakery.agents.manager.KneedingMachineManager;
import zarvis.bakery.models.Bakery;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

public class ProcessOrderBehaviour extends CyclicBehaviour {
	
	private Logger logger = LoggerFactory.getLogger(ProcessOrderBehaviour.class);
	private Map<Integer, String> orderAggregation = new TreeMap<Integer, String>();
	private Bakery bakery;

	public ProcessOrderBehaviour(Bakery bakery) {


		this.bakery = bakery;
	}
	@Override
	public void action() {

		try{
			ACLMessage message = myAgent.receive();
			if (message == null) {
				block();
			}

			else if (message.getPerformative() == ACLMessage.ACCEPT_PROPOSAL &&
					message.getConversationId().equals("inform-product-to-kneeding-machine-manager")){
				logger.info("Order {} stored in {} successfully",message.getContent(),message.getSender().getName());
			}

			else if (message.getPerformative() == ACLMessage.CFP) {
				String[] titleparts = message.getContent().split(" ");
				String orderID = titleparts[0];

				Order order = Util.getWrapper().getOrderById(orderID);

				ACLMessage reply = message.createReply();

				if(!bakery.hasAllProducts(order)){
					reply.setPerformative(ACLMessage.REFUSE);
					logger.warn("All products not found in our bakery " + bakery.getName());
					reply.setContent("All products not found in our bakery " + bakery.getName()); 
					reply.setConversationId("customer request");  
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

					reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					reply.setContent("request accepted");
					reply.setConversationId("customer request");
					myAgent.send(reply);
					informKneedingManager(orderID);
				}

			}
		}
		catch (Exception e) {e.printStackTrace(); }			
	}

	private void informKneedingManager(String orderID){
		AID kneedingmachinemanager = Util.searchInYellowPage(myAgent,"KneedingMachineManager")[0].getName();

		ACLMessage inform = new ACLMessage(ACLMessage.INFORM);

		inform.addReceiver(kneedingmachinemanager);
		inform.setContent(orderID);
		inform.setConversationId("inform-product-to-kneeding-machine-manager");
		inform.setReplyWith("inform"+System.currentTimeMillis()); // Unique value

		myAgent.send(inform);
		logger.info("order sent to kneeding manager : {}",kneedingmachinemanager.getName());

	}
}
