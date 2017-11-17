package zarvis.bakery.agents;
import java.util.Scanner;

import jade.core.AID; 
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CustomerAgent extends Agent {
	
	@Override
	protected void setup() {
		System.out.println("Hi I'm the agent Customer my name is:"+this.getAID().getName());
		addBehaviour(new ResquestBehaviour());
		addBehaviour(new ConversationBehaviour());
			
	}
	
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" terminating.");
	}
//========================================================
class ResquestBehaviour extends OneShotBehaviour{
	
	public void action() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("enter the Bakery's name");
			String name=scanner.nextLine();

			System.out.println("Enter the content of your request");
			String contentsMsg=scanner.nextLine();
			
			ACLMessage msg=new ACLMessage(ACLMessage.CFP);
			msg.setContent(contentsMsg);
			msg.setConversationId(contentsMsg);
			msg.addReceiver(new AID(name,AID.ISLOCALNAME));
			System.out.println("....... In progress");
			Thread.sleep(5000);
			send(msg);
			scanner.close();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
//========================================================

class ConversationBehaviour extends Behaviour {
	private AID requester;
	private String food;
	private double price;
	boolean finished = false;
	@Override
	public void action() {
		try {
			MessageTemplate template=MessageTemplate.or(
					MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
					MessageTemplate.or(
					MessageTemplate.MatchPerformative(ACLMessage.CONFIRM),
					MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchPerformative(ACLMessage.REFUSE))));
			ACLMessage aclMessage=receive(template);
			if(aclMessage!=null){
				switch(aclMessage.getPerformative()){
				case ACLMessage.REFUSE :
					String msg = aclMessage.getContent();
					System.out.println("***********************************");
					System.out.println("Conversation :"+aclMessage.getConversationId());
					System.out.println("Reception of the Reffuse :");
					System.out.println("From :"+aclMessage.getSender().getName());
					System.out.println(msg);
					System.out.println("see you soon");
					System.out.println("-----------------------------------");
					finished = true;
					doDelete();
					break;
				case ACLMessage.PROPOSE :
					price=Double.parseDouble(aclMessage.getContent());
					System.out.println("***********************************");
					System.out.println("Conversation :"+aclMessage.getConversationId());
					System.out.println("Reception of the offer :");
					System.out.println("From :"+aclMessage.getSender().getName());
					System.out.println("price="+price);
					System.out.println("-----------------------------------");
					System.out.println("Conclusion of the transaction.......");
					ACLMessage aclMessage2=aclMessage.createReply();
					aclMessage2.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					System.out.println("...... In progress");
					Thread.sleep(5000);
					send(aclMessage2);
					break;
				case ACLMessage.CONFIRM:
					System.out.println(".........................");
					System.out.println("Receipt of confirmation ...");
					System.out.println("Conversation :"+aclMessage.getConversationId());
					ACLMessage msg3=new ACLMessage(ACLMessage.INFORM);
					msg3.addReceiver(requester);
					msg3.setConversationId(aclMessage.getConversationId());
					msg3.setContent("<transaction>"
					+ "<book>"+food+"</book>"
					+ "<price>"+food+"</price>"
					+ "<fournisseur>"+aclMessage.getSender().getName()+"</fournisseur>"
					+ "</transaction");
					send(msg3);
					break;
				}
			}
			else{ block(); }
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return finished;
	}
}
}