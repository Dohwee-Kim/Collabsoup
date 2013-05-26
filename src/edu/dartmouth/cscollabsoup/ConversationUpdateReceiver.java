package edu.dartmouth.cscollabsoup;


import collabsoupServiceController.MessageNotifier;
import collabsoupServiceController.ServiceEventReceiver;

public class ConversationUpdateReceiver extends ServiceEventReceiver {

    public ConversationUpdateReceiver(MessageNotifier n) {
	super(n);
    }

}
