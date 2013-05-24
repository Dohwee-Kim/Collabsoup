package edu.dartmouth.cscollabsoup;



import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import edu.dartmouth.cscollabsoup.R;
import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class ParseDataHelper extends Activity {
	private String mActivity;
	private String mColocation;
	private String mLocation;
	private String mConversation;

	private TextView activityInfo;
	private TextView colocationInfo;
	private TextView locationInfo;
	private TextView conversationInfo;
	
	public static final String DATE_FORMAT = "H:mm:ss MMM d yyyy";
	
	private BroadcastReceiver colocationReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle data = intent.getExtras();
			mColocation = data.getString("key_bio_colocation", "");
			String[] colocationLines = mColocation.split(";");
			String colocationStat = "";
			for (String line : colocationLines) {
				String[] bluetoothComponents=line.split(",");
				colocationStat=colocationStat
						+ parseTime(Double.valueOf(bluetoothComponents[0]).longValue())+","
						+ bluetoothComponents[1] +","+ bluetoothComponents[2] + "\n";
			}
			mColocation = colocationStat;
			colocationInfo.setText(mColocation);
			Log.e("ParseDataHelper", "colocation" + mColocation);
		}
	};
	
	
	private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle data = intent.getExtras();
			mLocation = data.getString("key_bio_location", "");
			String[] locationLines = mLocation.split(";");
			String locationStat = "";
			for (String line : locationLines) {
				String[] wifiComponents = line.split(",");
				locationStat = locationStat
						+ parseTime(Double.valueOf(wifiComponents[0]).longValue())+","
						+ wifiComponents[1] +","+wifiComponents[2] + "\n";
			}
			mLocation = locationStat;
			locationInfo.setText(mLocation);
			Log.e("ParseDataHelper", "location" + mLocation);
		}
	};
	
	//Private function for parsingTime 
	private String parseTime(long timeInSec) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timeInSec * 1000);
		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(calendar.getTime());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parsetesting);
		
		activityInfo = (TextView) findViewById(R.id.activityInfo);
		colocationInfo = (TextView) findViewById(R.id.colocationInfo);
		locationInfo = (TextView) findViewById(R.id.locationInfo);
		conversationInfo = (TextView) findViewById(R.id.conversationInfo);
		
		
		// co location data 
		// registerReceiver(colocationReceiver, new IntentFilter("bio_colocation"));
		registerReceiver(locationReceiver, new IntentFilter("bio_location"));
		
	}
}