package zarvis.bakery.behaviors;

import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

public class FindAllBackeryByNameBehaviour extends TickerBehaviour{

	public FindAllBackeryByNameBehaviour(Agent a, long period) {
		super(a, period);
	}

	private AID[] backeryAgents;
	private MessageTemplate mt; 

	public void onTick() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("bakery");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			backeryAgents = new AID[result.length];
			List<Order> orders = Util.getWrapper().getOrders();
			for (int i = 0; i < result.length; ++i) {
				System.out.println("backery found: " + result[i].getName());
				backeryAgents[i] = result[i].getName();
				
				// Send the cfp (call for proposal) to all sellers
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				cfp.addReceiver(backeryAgents[i]);
				
				cfp.setContent(orders.get(0).getGuid());
				cfp.setConversationId("book-trade");
				cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
				myAgent.send(cfp);
				// Prepare the template to get proposals
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("bakery"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
				
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

}