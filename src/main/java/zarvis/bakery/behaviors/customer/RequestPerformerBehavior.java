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

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RequestPerformerBehavior extends Behaviour {

    private int step = 0;
    private MessageTemplate mt;
    private String customerId;
    private String orderId;

    private DFAgentDescription[] bakeries;
    private List<Map.Entry<String, Integer>> bakeryResponses;
    private int blockCounter = 0;
    private boolean stopFlag = false;
    private Logger logger = LoggerFactory.getLogger(RequestPerformerBehavior.class);

    private Customer customer;
    List<Map.Entry<String, Integer>> aggregatedOrders;

    int counter = 0;

    public RequestPerformerBehavior(Customer customer,List<Map.Entry<String, Integer>> aggregatedOrders) {
        this.customer = customer;
        this.aggregatedOrders = aggregatedOrders;

    }

    private boolean removeOrder(String guid){
        int index = 0;
        for (Map.Entry<String,Integer> order : aggregatedOrders) {
            if(order.getKey() == guid){
                aggregatedOrders.remove(index);
                return true;
            }
            index++;
        }
        return false;
    }

    @Override
    public void action() {

        switch (step) {
            case 0:
                bakeries = Util.searchInYellowPage(myAgent, "BakeryService");
                if (bakeries.length > 0)
                    step = 1;
                break;

            case 1:

                for (DFAgentDescription bakery : bakeries) {
                    Util.sendMessage(myAgent, bakery.getName(), ACLMessage.CFP, aggregatedOrders.get(0) + " " + customerId, "place-order");
                }
                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("place-order"),
                        MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));

                step = 2;
                break;

//            case 2:
//                for (DFAgentDescription bakery : bakeries) {
//                    Util.sendMessage(myAgent, bakery.getName(), ACLMessage.CFP, this.orderId + " " + customerId, "place-order");
//                }
//                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("place-order"),
//                        MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
//
//                // Waiting for 3 second to receive the responses from bakeries
//                Util.waitForSometime(3000);
//                step = 2;
//                break;

            case 2:
                ACLMessage message = myAgent.receive(mt);

                if (message != null) {
//                    Map.Entry<String,Integer> response = new Map.Entry<String,Integer>();
//                    bakeryResponses.put();
//                    bakeryResponses.put(Integer.parseInt(message.getContent()), message.getSender());

                    if (bakeryResponses.size() == bakeries.length) {
                        step = 3;
                    }
                } else {
                    blockCounter++;
                    block();
                }
                break;
//
//            case 3:
//                if (bakeryResponses.size() == 0) {
//                    logger.info("No bakeries found");
//                    stopFlag = true;
//                } else {
//                    AID bestBakery = bakeryResponses.get(bakeryResponses.keySet().toArray()[0]);
//                    Util.sendMessage(myAgent, bestBakery, ACLMessage.ACCEPT_PROPOSAL, this.orderId, "place-order");
//                    mt = MessageTemplate.MatchConversationId("place-order");
//                    step = 4;
//                }
//                break;
//
//            case 4:
//                ACLMessage confirmationMessage = myAgent.receive(mt);
//
//                if (confirmationMessage != null) {
//
//                    if (confirmationMessage.getPerformative() == ACLMessage.CONFIRM) {
//                        logger.info("Order successfully accepted by {}", confirmationMessage.getSender().getName());
//                        stopFlag = true;
//                    }
//
//                    if (confirmationMessage.getPerformative() == ACLMessage.REFUSE) {
//                        bakeryResponses.remove(bakeryResponses.keySet().toArray()[0]);
//                        step = 3;
//                    }
//
//                } else {
//                    block();
//                }
//                break;
        }
    }

    @Override
    public boolean done() {
        return stopFlag;
    }
}