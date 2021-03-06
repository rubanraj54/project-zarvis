package tutorial;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RequestPerformer extends Behaviour {

	private AID bestSeller; // The agent who provides the best offer
	private int bestPrice; // The best offered price
	private int repliesCnt = 0; // The counter of replies from seller agents
	private MessageTemplate mt; // The template to receive replies
	private int step = 0;
	private AID[] sellerAgents;
	private String targetBookTitle;
	
	public RequestPerformer(AID[] sellerAgents,String targetBookTitle) {
		System.out.println("book title received in request performer " + targetBookTitle);
		this.sellerAgents = sellerAgents;
		this.targetBookTitle = targetBookTitle;
	}
	@Override
	public void action() {
		switch (step) {
		case 0:
			// Send the cfp (call for proposal) to all sellers
			ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
			for (int i = 0; i < sellerAgents.length; ++i) {
				System.out.println(sellerAgents[i]);
				cfp.addReceiver(sellerAgents[i]);
			}
			cfp.setContent(targetBookTitle);
			cfp.setConversationId("book-trade");
			cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
			myAgent.send(cfp);
			System.out.println("book title sent to all available sellers");
			// Prepare the template to get proposals
			mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
					MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
			step = 1;
			break;
		case 1:
			// Receive all proposals/refusals from seller agents
			ACLMessage reply = myAgent.receive(mt);
			if (reply != null) {
				// Reply received
				if (reply.getPerformative() == ACLMessage.PROPOSE) {
					System.out.println("Received proposal from seller");
					// This is an offer
					int price = Integer.parseInt(reply.getContent());
					if (bestSeller == null || price < bestPrice) {
						// This is the best offer at present
						bestPrice = price;
						bestSeller = reply.getSender();
					}
				}
				repliesCnt++;
				if (repliesCnt >= sellerAgents.length) {
					// We received all replies
					step = 2;
				}
				System.out.println("the best price is: " + bestPrice);
			}
			else 
			{
				System.out.println("No response from any seller");
				block();
			}
			break;
		case 2:
			// Send the purchase order to the seller that provided the best offer
			ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			order.addReceiver(bestSeller);
			order.setContent(targetBookTitle);
			order.setConversationId("book-trade");
			order.setReplyWith("order"+System.currentTimeMillis());
			myAgent.send(order);
			// Prepare the template to get the purchase order reply
			mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
					MessageTemplate.MatchInReplyTo(order.getReplyWith()));
			step = 3;
			System.out.println("sent purchase order to best seller");
			break;
		case 3:
			// Receive the purchase order reply
			reply = myAgent.receive(mt);
			if (reply != null) {
				// Purchase order reply received
				if (reply.getPerformative() == ACLMessage.INFORM) {
					// Purchase successful. We can terminate
					System.out.println(targetBookTitle+" successfully purchased.");
					System.out.println("Price = "+bestPrice);
					myAgent.doDelete();
				}
				step = 4;
			}
			else {
				block();
			}
			break;
		}


	}

	@Override
	public boolean done() {
		return ((step == 2 && bestSeller == null) || step == 4);
	}

}
