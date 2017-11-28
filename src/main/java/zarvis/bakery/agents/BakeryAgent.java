package zarvis.bakery.agents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import zarvis.bakery.behaviors.bakery.ProcessOrderBehaviour;
import zarvis.bakery.models.Bakery;
import zarvis.bakery.utils.Util;

public class BakeryAgent extends Agent {
	
	private Logger logger = LoggerFactory.getLogger(BakeryAgent.class);
	private Bakery bakery;
	
	public BakeryAgent(Bakery bakery){
		this.bakery= bakery;
	}

	@Override
	protected void setup() {

		Util.registerInYellowPage(this,"BakeryService",bakery.getGuid());
		
		addBehaviour(new ProcessOrderBehaviour(bakery));
	}
	protected void takeDown() {}
}

