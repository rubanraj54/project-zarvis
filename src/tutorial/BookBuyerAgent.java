package tutorial;

import jade.core.AID;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookBuyerAgent extends Agent {

	// The title of the book to buy
	private String targetBookTitle;

	// The list of known seller agents
	// private AID[] sellerAgents = {new AID("seller1", AID.ISLOCALNAME),
	// new AID("seller2", AID.ISLOCALNAME)};

	private AID[] sellerAgents;

	protected void setup() {
		// Printout a welcome message
		System.out.println("Hello! Buyer-agent " + getAID().getName() + " is ready.");
		// Get the title of the book to buy as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			targetBookTitle = (String) args[0];
			System.out.println("Trying to buy " + targetBookTitle);

			// Add a TickerBehaviour that schedules a request to seller agents
			// every 60 second
			addBehaviour(new TickerBehaviour(this, 60000) {
				protected void onTick() {
					// Update the list of seller agents
					DFAgentDescription template = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("book-selling");
					template.addServices(sd);
					try {
						DFAgentDescription[] result = DFService.search(myAgent, template);
						sellerAgents = new AID[result.length];
						for (int i = 0; i < result.length; ++i) {
//							System.out.println("sellers found: " + result[i].getName());
							sellerAgents[i] = result[i].getName();
						}
						System.out.println("Total number of sellers found: " + sellerAgents.length);
					} catch (FIPAException fe) {
						fe.printStackTrace();
					}
					// Perform the request
					myAgent.addBehaviour(new RequestPerformer(sellerAgents,targetBookTitle));
				}
			});
		} else {
			// Make the agent terminate immediately
			System.out.println("No book title specified");
			doDelete();
		}
	}

	// Put agent clean-up operations here
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Buyer-agent " + getAID().getName() + " terminating.");

	}
}