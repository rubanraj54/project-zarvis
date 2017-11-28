package zarvis.bakery.behaviors.kneedingmachinemanager;

import java.util.ArrayList;
import java.util.List;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zarvis.bakery.agents.CustomerAgent;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

public class ManageProductsBehavior extends CyclicBehaviour{
    private Logger logger = LoggerFactory.getLogger(ManageProductsBehavior.class);
	List<Order> orderList = new ArrayList<Order>();
	Order currentOrder = null;
	List<String> nextProducts = new ArrayList<String>();
	

	@Override
	public void action() {
		ACLMessage message = myAgent.receive();

		if (message != null) {
            if(message.getPerformative() == ACLMessage.INFORM){
                String orderId = message.getContent();
                Order order = Util.getWrapper().getOrderById(orderId);
                orderList.add(order);
                ACLMessage reply = message.createReply();
                reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                reply.setContent(order.getGuid());
                myAgent.send(reply);
            }

            if(message.getPerformative() == ACLMessage.REQUEST){
                if(nextProducts.size() > 0){
                    ACLMessage reply = message.createReply();
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(currentOrder.getGuid() + " " + nextProducts.get(0));
                    reply.setConversationId("");
                }else{
                    currentOrder = orderList.get(0);
                    nextProducts = new ArrayList<>(currentOrder.getProducts().keySet());
                }
            }
        } else {
		    block();
        }
	}

}