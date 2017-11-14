package zarvis.bakery;
import jade.wrapper.AgentController;
import jade.wrapper.AgentContainer;
import jade.core.Runtime;
import java.util.Scanner;
import jade.core.ProfileImpl;

public class CustomerContainer {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Hi, Hello!! enter your name dear customer");
		String nom=scanner.nextLine();
		try {
		Runtime runtime=Runtime.instance();
		ProfileImpl profileImpl=new ProfileImpl(false);
		profileImpl.setParameter(ProfileImpl.MAIN_HOST,"localhost");
		AgentContainer agentContainer=runtime.createAgentContainer(profileImpl);
		AgentController agentController=agentContainer.createNewAgent(nom,"agents.CustomerAgent", new Object[]{});
		agentController.start();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}