package zarvis.bakery.behaviors.kneedingmachine;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import zarvis.bakery.messages.CustomMessage;
import zarvis.bakery.models.Status;

public class CurrentStatusBehaviour extends CyclicBehaviour {

    private Status status = new Status(true);


    @Override
    public void action() {

        ACLMessage message = myAgent.receive();

        if (message != null) {

            if (message.getPerformative() == CustomMessage.REQUEST_STATUS && message.getConversationId().equals("kneeding-machine-availability")) {
                ACLMessage reply = message.createReply();
                reply.setPerformative(CustomMessage.RESPONSE_STATUS);
                reply.setContent(status.getStatus() ? "Available" : "Unavailable");
                reply.setConversationId("kneeding-machine-status");
                myAgent.send(reply);

            }

            if (message.getPerformative() == CustomMessage.CHANGE_STATUS) {
                status.setStatus(!status.getStatus());
            }

        } else {
            block();
        }

    }

}