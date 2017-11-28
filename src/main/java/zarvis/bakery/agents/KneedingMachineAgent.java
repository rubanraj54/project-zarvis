package zarvis.bakery.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import zarvis.bakery.behaviors.kneedingmachine.CurrentStatusBehaviour;
import zarvis.bakery.models.Bakery;
import zarvis.bakery.utils.Util;

import java.util.concurrent.CyclicBarrier;

public class KneedingMachineAgent extends Agent{
	
	private Bakery bakery;
	
	public KneedingMachineAgent(Bakery bakery) {
		this.bakery = bakery;
	}

	@Override
	protected void setup() {
        Util.registerInYellowPage(this,"KneedingMachineAgent","kneedingmachineagent-"+this.bakery.getGuid());
        addBehaviour(new CurrentStatusBehaviour());
	}

	public class RequestProductFromKneedingManager extends OneShotBehaviour{

        @Override
        public void action() {

//            ACLMessage message = req
//            ACLMessage message = myAgent.receive();
//
//            if (message != null){
//                if (message.getPerformative() == ACLMessage.INFORM) {
//                    ACLMessage
//                }
//            }else{
//                block();
//            }
        }
    }
}
