 package edu.dartmouth.cscollabsoup;

import collabsoupServiceController.MessageNotifier;
import collabsoupServiceController.ServiceEventReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationUpdateReceiver extends ServiceEventReceiver {
	public LocationUpdateReceiver(MessageNotifier n){
		super(n);
	}
}