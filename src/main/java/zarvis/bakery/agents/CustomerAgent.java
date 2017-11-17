package zarvis.bakery.agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class CustomerAgent extends Agent {
	
	@Override
	protected void setup() {
		System.out.println("Hi I'm the agent Customer my name is:"+this.getAID().getName());
		addBehaviour(new FindAllBackeryByNameBehaviour());			
	}
	
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" terminating.");
	}
//========================================================
	class FindAllBackeryByNameBehaviour extends OneShotBehaviour{
		private AID[] backeryAgents;
		
		public void action() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("backery");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				backeryAgents = new AID[result.length];
				for (int i = 0; i < result.length; ++i) {
					System.out.println("backery found: " + result[i].getName());
					backeryAgents[i] = result[i].getName();
				}
				System.out.println("Total number of backries found: " + backeryAgents.length);
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}
		}
		
	}
}