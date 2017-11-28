package zarvis.bakery.agents.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import zarvis.bakery.agents.BakeryAgent;
import zarvis.bakery.behaviors.kneedingmachinemanager.ManageProductsBehavior;
import zarvis.bakery.models.Bakery;
import zarvis.bakery.utils.Util;

public class KneedingMachineManager extends Agent {
	
	private Logger logger = LoggerFactory.getLogger(KneedingMachineManager.class);
	private Bakery bakery;
	
	public KneedingMachineManager(Bakery bakery) {
		this.bakery = bakery;
	}

	@Override
	protected void setup() {

		Util.registerInYelloPage(this,"KneedingMachineManager","kneedingmachinemanager-"+bakery.getGuid());

		addBehaviour(new ManageProductsBehavior());
		super.setup();
	}
}
