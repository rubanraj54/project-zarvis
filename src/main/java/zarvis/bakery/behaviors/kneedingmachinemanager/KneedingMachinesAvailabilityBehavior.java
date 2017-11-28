package zarvis.bakery.behaviors.kneedingmachinemanager;

import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zarvis.bakery.messages.CustomMessage;
import zarvis.bakery.models.Order;
import zarvis.bakery.utils.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class KneedingMachinesAvailabilityBehavior extends CyclicBehaviour {
    private Logger logger = LoggerFactory.getLogger(KneedingMachinesAvailabilityBehavior.class);

    private HashSet<String> availableKneedingMachines = new HashSet<String>();

    private int step = 0;

    private MessageTemplate mt;

    private static final int blockingTime = 3000;

    public KneedingMachinesAvailabilityBehavior() {

    }

    @Override
    public void action() {
        switch (step) {
            case 0:
                if (requestKneedingMachineAvailability()) {
                    step = 1;
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("kneeding-machine-status"),
                            MessageTemplate.MatchPerformative(CustomMessage.RESPONSE_STATUS));
                }
                break;
            case 1:
                ACLMessage message = myAgent.receive(mt);
                if (message != null) {
                    if (message.getPerformative() == CustomMessage.RESPONSE_STATUS && message.getConversationId().equals("kneeding-machine-status")) {
                        if (message.getContent().equals("Available")) {
                            availableKneedingMachines.add(message.getSender().getName());
                        }
                    }

                } else {
                    block(blockingTime);
                    step = 0;
                }
                break;
        }
    }

    public boolean requestKneedingMachineAvailability() {

        DFAgentDescription[] kneedingMachines = Util.searchInYellowPage(myAgent, "KneedingMachineAgent");

        if (kneedingMachines.length == 0)
            return false;

        for (DFAgentDescription kneedingMachine : kneedingMachines) {
            Util.sendMessage(myAgent, kneedingMachine.getName(), CustomMessage.REQUEST_STATUS, "", "kneeding-machine-availability");
        }
        return true;
    }

}