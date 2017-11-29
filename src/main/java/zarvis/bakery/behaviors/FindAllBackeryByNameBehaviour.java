package zarvis.bakery.behaviors;

import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

public class FindAllBackeryByNameBehaviour extends CyclicBehaviour{

	private AID[] backeryAgents;
	private MessageTemplate mt;

	@Override
	public void action() {

		ACLMessage message = myAgent.receive();

		if ( message!= null) {
			if (message.getPerformative() == ACLMessage.REQUEST){
				DFAgentDescription findBakeryTemplate = new DFAgentDescription();

				ServiceDescription serviceDescription = new ServiceDescription();
				serviceDescription.setType("BakeryService");

				findBakeryTemplate.addServices(serviceDescription);

				try {
					DFAgentDescription[] foundBackeryAgents = DFService.search(myAgent, findBakeryTemplate);
					backeryAgents = new AID[foundBackeryAgents.length];

					for (int i = 0; i < foundBackeryAgents.length; ++i) {
						backeryAgents[i] = foundBackeryAgents[i].getName();
					}
				} catch (FIPAException fe) {
					fe.printStackTrace();
				}
			}

		} else {
			block();
		}
	}


}