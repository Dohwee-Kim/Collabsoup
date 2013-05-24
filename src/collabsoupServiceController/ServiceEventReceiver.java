package collabsoupServiceController;

import collabsoupServiceController.MessageNotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * @author Rui Wang
 *   Thanks to Rui Wang ! 
 */

public class ServiceEventReceiver extends BroadcastReceiver {

    private MessageNotifier notifier = null;

    public ServiceEventReceiver(MessageNotifier n)
    {
    	notifier = n;
    }
    
    @Override
    public void onReceive(Context ctx, Intent intent) {
	if (notifier != null) {
	    notifier.onNotifyNewMessage(intent);
	}
    }

}