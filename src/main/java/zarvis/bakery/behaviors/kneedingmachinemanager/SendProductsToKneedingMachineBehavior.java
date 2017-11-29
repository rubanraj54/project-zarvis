package zarvis.bakery.behaviors.kneedingmachinemanager;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zarvis.bakery.messages.CustomMessage;
import zarvis.bakery.utils.Util;

import java.util.HashSet;

public class SendProductsToKneedingMachineBehavior extends CyclicBehaviour {
    private Logger logger = LoggerFactory.getLogger(SendProductsToKneedingMachineBehavior.class);

    private HashSet<String> availableKneedingMachines = new HashSet<String>();

    private int step = 0;

    private MessageTemplate mt;

    private static final int blockingTime = 3000;


    private String product = "";
    private AID avaialbleKneedingMachine = null;


    @Override
    public void action() {


        switch (step) {

            case 0:
                if (product.isEmpty()) {
//                    logger.info("req product");
                    Util.sendMessage(myAgent, myAgent.getAID(), ACLMessage.REQUEST, "", "next-product-request");
                    step = 1;
                }
                break;
            case 1:
                ACLMessage message = myAgent.receive();
                if (message != null) {
                    if (message.getPerformative() == CustomMessage.RESPONSE && message.getConversationId().equals("next-product-request")) {
                        product = message.getContent();
                        logger.info("product received"+message.getContent());
                    }
                    if (message.getPerformative() == ACLMessage.REFUSE && message.getConversationId().equals("next-product-request")) {
                        step = 0;
                    }
                } else {
                    block();
                }
                break;
        }

//        ACLMessage message = myAgent.receive();
//        if (message != null){
//            if (message.getPerformative() == CustomMessage.RESPONSE && message.getConversationId().equals("next-product-request")){
//                product = message.getContent();
//                logger.info(message.getContent());
////                Util.waitForSometime(1000);
////                product = "";
//            }
////            if (message.getPerformative() == CustomMessage.RESPONSE && message.getConversationId().equals("kneeding-machine-status")){
////                if (message.getContent().equals("Available")) {
////
////                }
////                avaialbleKneedingMachine = message.getContent();
////                logger.info(message.getContent());
//////                Util.waitForSometime(1000);
//////                product = "";
////            }
//        } else{
//            if (product.isEmpty()){
//                Util.sendMessage(myAgent,myAgent.getAID(),ACLMessage.REQUEST,"","next-product-request");
////                product = ""
//            }else if(avaialbleKneedingMachine == null){
//                logger.info("sending reqqq");
//                Util.sendMessage(myAgent,myAgent.getAID(),ACLMessage.REQUEST,"","kneeding-machine-status");
//            }
//            block(blockingTime);
//        }
//        switch (step) {
//            case 0:
//                if (product.isEmpty()) {
//                    Util.sendMessage(myAgent,myAgent.getAID(),);
//
////                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("kneeding-machine-status"),
////                            MessageTemplate.MatchPerformative(CustomMessage.RESPONSE_STATUS));
//                }
//                break;
//            case 1:
//                ACLMessage message = myAgent.receive(mt);
//                if (message != null) {
//                    if (message.getPerformative() == CustomMessage.RESPONSE_STATUS && message.getConversationId().equals("kneeding-machine-status")) {
//                        if (message.getContent().equals("Available")) {
//                            availableKneedingMachines.add(message.getSender().getName());
//                        }
//                    }
//
//                } else {
//                    block(blockingTime);
//                    step = 0;
//                }
//                break;
//        }
        }


    }