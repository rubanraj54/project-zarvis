package zarvis.bakery.behaviors.kneedingmachinemanager;

import java.util.ArrayList;
import java.util.List;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zarvis.bakery.agents.CustomerAgent;
import zarvis.bakery.messages.CustomMessage;
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
            if(message.getPerformative() == ACLMessage.INFORM &&
                    message.getConversationId().equals("inform-product-to-kneeding-machine-manager")){
                String orderId = message.getContent();
                Order order = Util.getWrapper().getOrderById(orderId);
                orderList.add(order);
//                logger.info("New order added to kneeding machine manager list {}",order);
                Util.sendReply(myAgent,message,ACLMessage.ACCEPT_PROPOSAL,order.getGuid(),"inform-product-to-kneeding-machine-manager");
            }

            if(message.getPerformative() == ACLMessage.REQUEST && message.getConversationId().equals("next-product-request")){
                if(nextProducts.size() == 0){
                    if (orderList.size() == 0){
                        Util.sendReply(myAgent,message,ACLMessage.REFUSE,"No products available for kneeding","next-product-request");
                    } else {
                        currentOrder = orderList.get(0);
                        nextProducts = new ArrayList<>(currentOrder.getProducts().keySet());
                        orderList.remove(0);
                        Util.sendReply(myAgent,message,CustomMessage.RESPONSE,currentOrder.getGuid() + " " + nextProducts.get(0),
                                "next-product-request");
                        nextProducts.remove(0);
                    }
                }
            }
        } else {
		    block();
        }
	}

}