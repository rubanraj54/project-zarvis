package tutorial;

import java.util.Hashtable;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookSellerAgent extends Agent {

	// The catalogue of books for sale (maps the title of a book to its price)
	private Hashtable catalogue;

	@Override
	protected void setup() {
		
		// Create the catalogue
		catalogue = new Hashtable();
		catalogue.put("avatar", 123);
		catalogue.put("avengers", 32);
		catalogue.put("wonder woman", 354);
		catalogue.put("last air bender", 234);
		
		DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.setName(getAID());
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType("book-selling");
		serviceDescription.setName("JADE-book-trading");
		agentDescription.addServices(serviceDescription);
		try {
			DFService.register(this, agentDescription);
			System.out.println("book selling service added to Directory facilitator");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		//adding offer request behavior, this behavior will keep on checking the incoming messages from the buyers
		addBehaviour(new OfferRequestsServer(catalogue));
		System.out.println("Offer request server behavior added successfully");
		
		// Add the behaviour serving purchase orders from buyer agents
		addBehaviour(new PurchaseOrdersServer());
	}

	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
			System.out.println("Book seller agent takeDown and service deregistered successfully");
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Printout a dismissal message
		System.out.println("Seller-agent " + getAID().getName() + " terminating.");
	}
}
