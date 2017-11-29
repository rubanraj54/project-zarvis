package zarvis.bakery.behaviors.bakery;

import java.util.HashMap;
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

	private HashMap<String, Integer> orders = new HashMap<>();
	private TreeMap<String, Integer> aggregatedOrders;

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

			else if (message.getPerformative() == ACLMessage.CFP && message.getConversationId().equals("place-order")) {
				String[] titleparts = message.getContent().split(" ");
				String orderID = titleparts[0];

				Order order = Util.getWrapper().getOrderById(orderID);

				Util.sendReply(myAgent,message,ACLMessage.PROPOSE,
						bakery.getGuid() +" "+String.valueOf(bakery.missingProductCount(order)),"place-order");


			} else if (message.getPerformative() == ACLMessage.ACCEPT_PROPOSAL && message.getConversationId().equals("place-order")) {

				String[] titleparts = message.getContent().split(" ");
				String orderID = titleparts[0];
				Order order = Util.getWrapper().getOrderById(orderID);

				// for available orders to be delivered on day 1
				if ((order.getDelivery_date().getDay() == 1)) {
					this.orders.put(order.getGuid(),order.getDelivery_date().getHour());
				}
				// for available orders to be delivered after day 1
				else {
					int time = order.getDelivery_date().getHour() + (order.getDelivery_date().getDay() - 1) * 24;
					this.orders.put(order.getGuid(),time);
				}

				aggregatedOrders = Util.sortMapByValue(this.orders);

				Util.sendReply(myAgent,message,ACLMessage.CONFIRM,bakery.getGuid()+" "+order.getGuid(),"place-order");
//				logger.info("order {} successfully received from {}",order.getGuid(),titleparts[1]);
				informKneedingManager();
			}
		}
		catch (Exception e) {e.printStackTrace(); }			
	}

	private void informKneedingManager(){
		AID kneedingmachinemanager = Util.searchInYellowPage(myAgent,"KneedingMachineManager",null)[0].getName();

		String orderGuid = aggregatedOrders.firstKey();

		Util.sendMessage(myAgent,kneedingmachinemanager,ACLMessage.INFORM,orderGuid,"inform-product-to-kneeding-machine-manager");

		orders.remove(orderGuid);
		aggregatedOrders.remove(orderGuid);
//		logger.info("order {} sent to kneeding manager : {} ",orderGuid,kneedingmachinemanager.getName());

	}
}
