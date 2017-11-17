package zarvis.bakery;

import zarvis.bakery.agents.BakeryAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
public class MainContainer {
	public static void main(String[] args) {
		try{
			
			Runtime runtime = Runtime.instance();
			runtime.setCloseVM(true);
			
			Properties properties=new ExtendedProperties();
			properties.setProperty(Profile.GUI,"true");
			
			ProfileImpl profileImpl=new ProfileImpl(properties);
			
			AgentContainer mainContainer = runtime.createMainContainer(profileImpl);
			
			mainContainer.acceptNewAgent("bakery", new BakeryAgent()).start();
			mainContainer.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}