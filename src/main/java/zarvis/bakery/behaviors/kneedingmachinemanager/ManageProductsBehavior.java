package zarvis.bakery.behaviors.kneedingmachinemanager;

import java.util.ArrayList;
import java.util.List;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

public class ManageProductsBehavior extends CyclicBehaviour{
	
	List<Order> orderList = new ArrayList<Order>();
	Order currentOrder = null;
	List<String> nextProducts = new ArrayList<String>();
	

	@Override
	public void action() {
		ACLMessage message = myAgent.receive();
		
		if(message!=null && message.getPerformative() == ACLMessage.INFORM){
			String orderId = message.getContent();
			Order order = Util.getWrapper().getOrderById(orderId);
			orderList.add(order);
		}
		
		if(message!=null && message.getPerformative() == ACLMessage.REQUEST){
			System.out.println("here");
			if(nextProducts.size() > 0){
				System.err.println("reply" + currentOrder.getGuid() + " " + nextProducts.get(0));
//				ACLMessage reply = message.createReply();
//				reply.setPerformative(ACLMessage.PROPOSE);
//				reply.setContent(currentOrder.getGuid() + " " + nextProducts.get(0));
//				reply.setConversationId("book-trade");
			}else{
				currentOrder = orderList.get(0);
				nextProducts = new ArrayList<>(currentOrder.getProducts().keySet());
			}
		}
	}

}