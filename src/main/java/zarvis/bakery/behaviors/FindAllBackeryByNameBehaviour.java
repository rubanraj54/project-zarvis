package zarvis.bakery.behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class FindAllBackeryByNameBehaviour extends TickerBehaviour{

	public FindAllBackeryByNameBehaviour(Agent a, long period) {
		super(a, period);
	}

	private AID[] backeryAgents;

	public void onTick() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("bakery");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			backeryAgents = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				System.out.println("backery found: " + result[i].getName());
				backeryAgents[i] = result[i].getName();
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

}