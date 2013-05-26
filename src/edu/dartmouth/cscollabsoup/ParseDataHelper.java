package edu.dartmouth.cscollabsoup;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.dartmouth.cscollabsoup.R;
import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	
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
		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			if ((Globals.SEND_BROADCAST).equals("on")) {
				Bundle data = intent.getExtras();
				mLocation = data.getString("key_bio_location", "");
				String[] locationLines = mLocation.split(";");
				
				int x=0;
				int index = 0;
				int max = -999;
				String[] locationStat = new String[locationLines.length];
				for (String line : locationLines) {
					
					String[] wifiComponents = line.split(",");
					
					locationStat[x] = wifiComponents[1] +", "+wifiComponents[2] + "\n";
					Log.e("ParseDataHelper", "GOT HERE" + wifiComponents[2]);
	
					int cur_strength = Integer.parseInt(wifiComponents[2]);
					if (max < cur_strength)
					{
						max = cur_strength;
						index = x;
					}
					x++;
	
				}
				
				mLocation = locationStat[index];
				locationInfo.setText(mLocation);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// Building Parameters
				Globals.PHP_FILEPATH = "find_nearby.php";
				params.add(new BasicNameValuePair("username", Globals.USERNAME));
				params.add(new BasicNameValuePair("password", Globals.PASSWORD));
				params.add(new BasicNameValuePair("location", mLocation));
				params.add(new BasicNameValuePair("send_broadcast", Globals.SEND_BROADCAST));
				params.add(new BasicNameValuePair("course1", "CS1"));
				params.add(new BasicNameValuePair("course2", "CS2"));
				params.add(new BasicNameValuePair("course3", "CS3"));
				params.add(new BasicNameValuePair("course4", "CS4"));
				
				new postToDatabaseTask().execute(params);
			}
			
			else {
				mLocation = "";
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// Building Parameters
				Globals.PHP_FILEPATH = "find_nearby.php";
				params.add(new BasicNameValuePair("username", Globals.USERNAME));
				params.add(new BasicNameValuePair("password", Globals.PASSWORD));
				params.add(new BasicNameValuePair("location", mLocation));
				params.add(new BasicNameValuePair("send_broadcast", Globals.SEND_BROADCAST));				
				params.add(new BasicNameValuePair("course1", "CS1"));
				params.add(new BasicNameValuePair("course2", "CS2"));
				params.add(new BasicNameValuePair("course3", "CS3"));
				params.add(new BasicNameValuePair("course4", "CS4"));
				
				new postToDatabaseTask().execute(params);
				
	//			Log.e("ParseDataHelper", "location" + mLocation);
			}
		}
	};

	
	private BroadcastReceiver conversationReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			
			Bundle data = intent.getExtras();
			mConversation = data.getString("key_bio_conversation", "");
			String[] conversationComponents = mConversation.split(",");

			activityInfo.setText(mActivity);
			colocationInfo.setText(mColocation);
			locationInfo.setText(mLocation);
			conversationInfo.setText("last conversation at: "
					+ parseTime(Double.valueOf(conversationComponents[1])
							.longValue() / 1000));

			
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			// Building Parameters
//			params.add(new BasicNameValuePair("username", Globals.USERNAME));
//			params.add(new BasicNameValuePair("password", Globals.PASSWORD));
//			params.add(new BasicNameValuePair("location", mConversation));
//			postToDatabase(params, "conversation_update.php");
//			Log.e("MainActivity", "conversation" + mConversation);
		
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
		
//		registerReceiver(conversationReceiver, new IntentFilter("bio_conversation"));
	}
	
	
	class postToDatabaseTask extends AsyncTask<List<NameValuePair>, String, Void> {
		protected Void doInBackground(List<NameValuePair>... params) 
		{
			
			// getting JSON Object
			// Note that create product url accepts POST method
			String url_name = "http://"+Globals.SERVER_IP+"/collabsoup/"+Globals.PHP_FILEPATH;
//			Log.d("PHPFILEPATH",Globals.PHP_FILEPATH);
			JSONObject json = jsonParser.makeHttpRequest(url_name,"POST", params[0]);
			
			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try 
			{
				int success = json.getInt(TAG_SUCCESS);
				
				
				if (success == 1)
				{
					Log.d("Collabsoup","SUCCESSFULLY POSTED TO SERVER");
					if ((Globals.SEND_BROADCAST).equals("on")) {
						// successfully created product
//						System.out.println("ON SEND BROADCAST");
						for(int j=0; j< json.length();j++)
							if(json.has(String.valueOf(j)))
								System.out.println(json.getString(String.valueOf(j)));
						
					}
				}
				else 
					Log.d("CollabSoup:SignUpActivity","Failed to register ");

			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}

			return null;
		}
		}

	

	}	
