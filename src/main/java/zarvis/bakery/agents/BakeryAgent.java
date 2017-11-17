package zarvis.bakery.agents;
import java.util.HashMap; import java.util.Map; import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour; import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BakeryAgent extends Agent {
	private Map<String, Double> data=new HashMap<>();
	@Override
	protected void setup() {
		data.put("bread", new Double(1.19));
		data.put("pie", new Double(15));
		data.put("cookies", new Double(5));
		System.out.println("....... Bakery "+this.getAID().getName());
		addBehaviour(new BakeryBehaviour());
	}
	
class BakeryBehaviour extends CyclicBehaviour{
	@Override
	public void action() {
		try {
			MessageTemplate messageTemplate= MessageTemplate.or(
			MessageTemplate.MatchPerformative(ACLMessage.CFP),
			MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
			ACLMessage aclMessage=receive(messageTemplate);
			if(aclMessage!=null){
				switch(aclMessage.getPerformative()){
					case ACLMessage.CFP :
						System.out.println("--------------------------------");
						System.out.println("Conversation :"+aclMessage.getConversationId());
						String food=aclMessage.getContent();
						//String compteur=aclMessage.getUserDefinedParameter("compteur");
						System.out.println("Receiving a message :"/*+compteur*/);
						System.out.println("Sender :"+aclMessage.getSender().getName());
						System.out.println("contents:"+food);
						System.out.println("--------------------------------");
						Double price=data.get(food);
						if(price!=null){
							ACLMessage reply=aclMessage.createReply();
							reply.setPerformative(ACLMessage.PROPOSE);
							reply.setContent(price.toString());
							System.out.println("...... In progress");
							Thread.sleep(5000);
							send(reply);
						}
						else{
							ACLMessage reply=aclMessage.createReply();
							reply.setPerformative(ACLMessage.REFUSE);
							reply.setContent("sorry :( :( the requested food does not exist!");
							System.out.println("cancellation of the order....");
							Thread.sleep(5000);
							send(reply);
						}
						break;
					case ACLMessage.ACCEPT_PROPOSAL:
						System.out.println("--------------------------------");
						System.out.println("Conversation :"+aclMessage.getConversationId());
						System.out.println("Validation of the transaction .....");
						ACLMessage reply2=aclMessage.createReply();
						reply2.setPerformative(ACLMessage.CONFIRM);
						System.out.println("...... In progress");
						Thread.sleep(5000);
						send(reply2);
						break;
				} 
			}
			else{
				//System.out.println("Block");
				block();
			}
			
		}
		catch (Exception e) {e.printStackTrace(); }
	}
}
	
	@Override
	protected void takeDown() {}
}
		
