package zarvis.bakery.agents.manager;

import jade.lang.acl.ACLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import zarvis.bakery.agents.BakeryAgent;
import zarvis.bakery.behaviors.kneedingmachinemanager.KneedingMachinesAvailabilityBehavior;
import zarvis.bakery.behaviors.kneedingmachinemanager.ManageProductsBehavior;
import zarvis.bakery.behaviors.kneedingmachinemanager.SendProductsToKneedingMachineBehavior;
import zarvis.bakery.messages.CustomMessage;
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

		Util.registerInYellowPage(this,"KneedingMachineManager","kneedingmachinemanager-"+bakery.getGuid());



		addBehaviour(new ManageProductsBehavior());
//		addBehaviour(new KneedingMachinesAvailabilityBehavior());
		Util.waitForSometime(2000);
//		addBehaviour(new SendProductsToKneedingMachineBehavior());


	}
}
