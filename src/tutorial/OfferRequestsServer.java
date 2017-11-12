package tutorial;

import java.util.Hashtable;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class OfferRequestsServer extends CyclicBehaviour {
	
	private Hashtable catalogue;
	
	public OfferRequestsServer(Hashtable catalogue){
		this.catalogue = catalogue;
	}
	
	public void action() {
		ACLMessage msg = myAgent.receive();
		if (msg != null) {
			// Message received. Process it
			String title = msg.getContent();
			ACLMessage reply = msg.createReply();
			Integer price = (Integer) catalogue.get(title);
			if (price != null) {
				// The requested book is available for sale. Reply with the price
				reply.setPerformative(ACLMessage.PROPOSE);
				reply.setContent(String.valueOf(price.intValue()));
				reply.setConversationId("book-trade");
				System.out.println("book found");
			}
			else {
				// The requested book is NOT available for sale.
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent("not-available");
				System.out.println("book not available");
			}
			myAgent.send(reply);
		}
	}
} // End of inner class OfferRequestsServer
