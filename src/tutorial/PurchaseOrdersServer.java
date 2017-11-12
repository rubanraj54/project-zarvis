package tutorial;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class PurchaseOrdersServer extends CyclicBehaviour {

	@Override
	public void action() {
		ACLMessage msg = myAgent.receive();
		if (msg != null) {
			System.out.println("purchase order received to server");
			// Message received. Process it
			String title = msg.getContent();
			ACLMessage reply = msg.createReply();

			reply.setPerformative(ACLMessage.INFORM);
			reply.setContent("purchase order confirmed..! your book will be shipped soon");
			reply.setConversationId("book-trade");
			System.out.println("purchase order placed");
			myAgent.send(reply);
		}

	}


}
