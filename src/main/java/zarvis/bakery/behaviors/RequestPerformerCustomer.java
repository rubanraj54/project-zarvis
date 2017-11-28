package zarvis.bakery.behaviors;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RequestPerformerCustomer extends Behaviour {
	private int bakeryIndex = 0;
	private int step = 0;
	private AID[] backeryAgents;
	private MessageTemplate mt; // The template to receive replies
	String customerId;
	String orderId;
	private int totalBakeries;
	
	public RequestPerformerCustomer(String customerId , String orderId) {
			this.customerId = customerId;
			this.orderId = orderId;
	}
	
	public int getBakeriesByName() throws FIPAException{
		int total=0;
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("bakery");
		template.addServices(sd);
		DFAgentDescription[] result = DFService.search(myAgent, template);
		backeryAgents = new AID[result.length];
		
		for (int i = 0; i < result.length; ++i){ 
			backeryAgents[i] = result[i].getName();
			total++;
			//System.out.println("bakery :"+result[i].getName());
		}
		return total;
	}
	
	@Override
	public void action() {
		
		switch (step) {
		case 0:
			// Send the CFP (call for proposal) to all bakeries one by one
			try {
				totalBakeries = this.getBakeriesByName();
				while(backeryAgents.length == 0){
					Thread.sleep(500);
					totalBakeries = this.getBakeriesByName();
				}
				if(bakeryIndex < totalBakeries){
					ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
					System.out.println(backeryAgents[bakeryIndex]);
					cfp.addReceiver(backeryAgents[bakeryIndex]);
					cfp.setContent(this.orderId+" "+customerId);
					cfp.setConversationId("customer "+customerId+" request");
					cfp.setReplyWith("cfp"+orderId+System.currentTimeMillis()); // Unique value
					System.out.println("sending order to bakery");
					myAgent.send(cfp);
					step = 1;
					break;
					
				}
				else{
					step = 3;
					System.out.println("the order "+orderId+" is refused by all the Bakeries");
					break;
				}
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		case 1:
			ACLMessage reply = myAgent.receive(mt);
			if (reply != null) {
				// Reply received
				if (reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
					System.out.println("=======================");
					System.out.println("customer : "+this.customerId);
					System.out.println("Received Accept from bakery : "+backeryAgents[bakeryIndex]);
					System.out.println("=======================");
					block();
				}
				else if(reply.getPerformative() == ACLMessage.REFUSE){
					System.out.println("the order "+orderId+" is refused by bakery: "+backeryAgents[bakeryIndex]);
					bakeryIndex++;
					step = 0;// for chose other bakery
				}
			}
			else {
				block();
			}
			break;
		case 3: 
			block();
			break;
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}
}