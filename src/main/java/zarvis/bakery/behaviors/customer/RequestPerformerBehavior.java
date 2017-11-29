package zarvis.bakery.behaviors.customer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zarvis.bakery.models.Customer;
import zarvis.bakery.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RequestPerformerBehavior extends Behaviour {

    private int step = 0;
    private boolean stopFlag = false;

    private MessageTemplate mt;
    private Logger logger = LoggerFactory.getLogger(RequestPerformerBehavior.class);
    private Customer customer;

    private DFAgentDescription[] bakeries;

    private HashMap<String, Integer> bakeryResponses = new HashMap<>();
    private TreeMap<String, Integer> aggregatedBakeryResponses;
    private TreeMap<String, Integer> aggregatedOrders;




    public RequestPerformerBehavior(Customer customer,TreeMap<String, Integer> aggregatedOrders) {
        this.customer = customer;
        this.aggregatedOrders = aggregatedOrders;

    }

    @Override
    public void action() {

        switch (step) {
            case 0:
                bakeries = Util.searchInYellowPage(myAgent, "BakeryService",null);
                if (bakeries.length > 0)
                    step = 1;
                break;

            case 1:

                for (DFAgentDescription bakery : bakeries) {
                    Util.sendMessage(myAgent, bakery.getName(), ACLMessage.CFP, aggregatedOrders.firstKey() + " " + customer.getGuid(), "place-order");
                }
                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("place-order"),
                        MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

                step = 2;
                break;

            case 2:
                ACLMessage message = myAgent.receive(mt);

                if (message != null) {
                    String[] parsedMessage = message.getContent().split(" ");
                    bakeryResponses.put(parsedMessage[0],Integer.parseInt(parsedMessage[1]));

                    if (bakeryResponses.size() == bakeries.length) {
                        aggregatedBakeryResponses = Util.sortMapByValue(bakeryResponses);
                        step = 3;
                    }
                } else {
                    block();
                }
                break;

            case 3:
                if (aggregatedBakeryResponses.size() == 0) {
                    logger.info("No bakeries found");
                    stopFlag = true;
                } else {
                    AID bestBakery = Util.searchInYellowPage(myAgent,"BakeryService",aggregatedBakeryResponses.firstKey())[0].getName();

                    Util.sendMessage(myAgent, bestBakery, ACLMessage.ACCEPT_PROPOSAL, aggregatedOrders.firstKey() + " " + customer.getGuid(), "place-order");
                    mt = MessageTemplate.MatchConversationId("place-order");
                    step = 4;
                }
                break;

            case 4:
                ACLMessage confirmationMessage = myAgent.receive(mt);

                if (confirmationMessage != null) {

                    if (confirmationMessage.getPerformative() == ACLMessage.CONFIRM) {
                        String[] parsedConfirmationMessage = confirmationMessage.getContent().split(" ");
                        logger.info("Order {} successfully accepted by {}",parsedConfirmationMessage[1],parsedConfirmationMessage[0]);
                        aggregatedOrders.remove(parsedConfirmationMessage[1]);
                        if (aggregatedOrders.size() == 0){
                            logger.info("All orders from {} successfully placed",customer.getGuid());
                            stopFlag = true;
                        }else{
                            step = 1;
                        }
                    }

                    if (confirmationMessage.getPerformative() == ACLMessage.REFUSE) {
                        aggregatedBakeryResponses.remove(aggregatedBakeryResponses.firstKey());
                        step = 3;
                    }

                } else {
                    block();
                }
                break;
        }
    }

    @Override
    public boolean done() {
        return stopFlag;
    }
}