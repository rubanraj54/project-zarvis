package zarvis.bakery.agents.manager;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import zarvis.bakery.behaviors.kneedingmachinemanager.ManageProductsBehavior;
import zarvis.bakery.models.Bakery;

public class KneedingMachineManager extends Agent {
	
	private Bakery bakery;
	
	public KneedingMachineManager(Bakery bakery) {
		this.bakery = bakery;
	}

	@Override
	protected void setup() {
		// Create agent description and set AID 
		DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.setName(getAID());

		// Create service description and set type and bakery name
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType("KneedingMachineManager");
		serviceDescription.setName("kneedingmachinemanager");

		// add the service description to this agent
		agentDescription.addServices(serviceDescription);

		// Now add this agent description to yellow pages,
		try {
			DFService.register(this, agentDescription);
			System.out.println("Kneeding machine manager is added to yellow pages");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		addBehaviour(new ManageProductsBehavior());
		super.setup();
	}
}
