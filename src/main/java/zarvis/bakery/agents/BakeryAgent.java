package zarvis.bakery.agents;
import java.util.HashMap;
import java.util.Map;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import zarvis.bakery.models.Bakery;
import zarvis.bakery.models.BakeryJsonWrapper;

public class BakeryAgent extends Agent {
	private Map<String, Double> data=new HashMap<>();
	private Bakery bakery;
	
	public BakeryAgent(Bakery bakery){
		this.bakery= bakery;
	}
	
	@Override
	protected void setup() {
		System.out.println("....... Bakery "+this.getAID().getName());
		
		// Create agent description and set AID 
		DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.setName(getAID());
		
		// Create service description and set type and bakery name
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType("bakery");
		serviceDescription.setName(bakery.getName());
		
		// add the service description to this agent
		agentDescription.addServices(serviceDescription);
		
		// Now add this agent description to yellow pages, so that other agents can identify this agent
		try {
			DFService.register(this, agentDescription);
			System.out.println("Bakery agent is added to yellow pages");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		addBehaviour(new BakeryBehaviour());
	}
	
class BakeryBehaviour extends CyclicBehaviour{
	@Override
	public void action() {
		try {		
			ACLMessage msg = myAgent.receive();
		if (msg != null) {
			System.out.println("=======================");
			System.out.println("order received by the bakery");
			// Message received. Process it
			String title = msg.getContent();
			System.out.println(title);
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.REFUSE);
			reply.setContent("request accepted");
			reply.setConversationId("customer request");
			System.out.println("order placed");
			System.out.println("=======================");
			myAgent.send(reply);
		}
		else
			block();
			
		}
		catch (Exception e) {e.printStackTrace(); }
	}
}
	
	@Override
	protected void takeDown() {}
}
		
