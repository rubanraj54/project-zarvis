package zarvis.bakery.agents;

import jade.core.Agent;
import zarvis.bakery.models.Bakery;

public class KneedingMachineAgent extends Agent{
	
	private Bakery bakery;
	
	public KneedingMachineAgent(Bakery bakery) {
		this.bakery = bakery;
	}

	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		super.setup();
	}
}
