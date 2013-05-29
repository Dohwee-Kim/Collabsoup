package edu.dartmouth.cscollabsoup;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class ParseDataHelper extends Activity {
	private String mActivity;
	private String mColocation;
	private String mLocation;
	private String mConversation;
	private String nearest_neighbors="";
	private TextView activityInfo;
	private TextView colocationInfo;
	private TextView locationInfo;
	private TextView conversationInfo;
	String course1="";
	String course2="";
	String course3="";
	String course4="";
	Handler mHandler = new Handler();
	
	
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
			//colocationInfo.setText(mColocation);
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
					int cur_strength = Integer.parseInt(wifiComponents[2]);
					if (max < cur_strength)
					{
						max = cur_strength;
						index = x;
					}
					x++;
				}
				
				String[] loc_string = locationStat[index].split(",");
				mLocation = loc_string[0];
				Log.d("LOC",mLocation);
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// Building Parameters
				Globals.PHP_FILEPATH = "find_nearby.php";
				params.add(new BasicNameValuePair("username", Globals.USERNAME));
				params.add(new BasicNameValuePair("password", Globals.PASSWORD));
				params.add(new BasicNameValuePair("location", mLocation));
				params.add(new BasicNameValuePair("send_broadcast", Globals.SEND_BROADCAST));
				params.add(new BasicNameValuePair("course1", Globals.course1));
				params.add(new BasicNameValuePair("course2", Globals.course2));
				params.add(new BasicNameValuePair("course3", Globals.course3));
				params.add(new BasicNameValuePair("course4", Globals.course4));
				
				//System.out.println(ighbors);
				Log.d("NEAREST_NEIGHBORS broad", nearest_neighbors);

//				locationInfo.setText(nearest_neighbors);
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
				params.add(new BasicNameValuePair("course1", Globals.course1));
				params.add(new BasicNameValuePair("course2", Globals.course2));
				params.add(new BasicNameValuePair("course3", Globals.course3));
				params.add(new BasicNameValuePair("course4", Globals.course4));
				
				new postToDatabaseTask().execute(params);
//				locationInfo.setText(nearest_neighbors);
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
//
//			activityInfo.setText(mActivity);
//			colocationInfo.setText(mColocation);
//			locationInfo.setText(mLocation);
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
		Bundle extras = getIntent().getExtras();
		

		
//		activityInfo = (TextView) findViewById(R.id.activityInfo);
//		colocationInfo = (TextView) findViewById(R.id.colocationInfo);
		locationInfo = (TextView) findViewById(R.id.locationInfo);
//		conversationInfo = (TextView) findViewById(R.id.conversationInfo);
		/*Runnable runnable = new Runnable() {	
		@Override
            public void run() {  
		
			locationInfxt(nearest_neighbors);
			Log.d("UPDATING IN HANDLER", nearest_neighbors);
			//mHandler.postDelayed(this, 1000);
		}
		};*/
		//mHandler.post(runnable);
		// registerReceiver(colocationReceiver, new IntentFilter("bio_colocation"));
		registerReceiver(locationReceiver, new IntentFilter("bio_location"));		
//		registerReceiver(conversationReceiver, new IntentFilter("bio_conversation"));
	}
	
	
	class postToDatabaseTask extends AsyncTask<List<NameValuePair>, String, String> {
		protected String doInBackground(List<NameValuePair>... params) 
		{
			// getting JSON Object
			// Note that create product url accepts POST method
			String url_name = "http://"+Globals.SERVER_IP+"/collabsoup/"+Globals.PHP_FILEPATH;
			JSONObject json = jsonParser.makeHttpRequest(url_name,"POST", params[0]);
			String nearby = null;
			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try 
			{
				int success = json.getInt(TAG_SUCCESS);
				if (success == 1)
				{
//					Log.d("Collabsoup","SUCCESSFULLY POSTED TO SERVER");
					if ((Globals.SEND_BROADCAST).equals("on")) {
						// successfully created product
//						System.out.println("ON SEND BROADCAST");
						String[] to_parse = new String[json.length()-2];
						Log.d("FLOOOO", "String RECEIVED");
						for(int j=0; j< json.length()-2;j++)
						{
//							if(json.has(String.valueOf(j));	
							to_parse[j] = json.getString(String.valueOf(j));	
							Log.d("LOG PARSE", to_parse[j]);
//							Log.d("FLOOOO:jjjjj",String.valueOf(j));
						}
//						Log.d("TOPARSE", to_parse);
						nearby = new String();
						nearby = parseJsonData(to_parse);	
						Log.d("NEAREST_NEIGHBORS ASYNC", "parseJsonData");
							
						//			publishProgress(nearest_neighbors);
					}
				}
				else 
					Log.d("CollabSoup:SignUpActivity","Failed to register ");

			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
			Log.d("Nearest neighboiurs doinbackground",nearby);
			
			return nearby;
		}
		@Override
		protected void onPostExecute(String nearby)
		{
			Log.d("ONPOSTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT","CALLLLLLLLLLLLLLLLLEDDDDDDDDDDDDDDD");
			Log.d("Nearest neighboiurs on possssssssssssssst",nearby);
			locationInfo.setText(nearby);
			
			
		}
		
		
	/*	protected void onProgressUpdate(String s)
		{
			super.onProgressUpdate(s);
			Log.d("ONPROGRESSUPDATE", s);
			//locationInfo.setText(s);
		}*/
		}
	String parseRouter(String location)
	{
		if(location.equals("Unavailable"))
			return location;
		String [] array;
		array= location.split("-");
		String ret = "Building:"+array[0].toString()+" Floor:"+  array[1].toString() + " Room:"+  array[2].toString();
		return ret;
	}
	public String parseJsonData(String[] p) {
		//convert array to m so each row corresponds to one row in m
		String[][] m = new String[p.length/5][5];
		int i = 0;
		int j = 0;
		Log.d("parseJSONDATA","called1");
		
		String s = "";
		for (int x = 0; x < p.length; x++)
		{
			Log.d("p in JSONDATA", p[x]);
			if (j == 4) {
				m[i][j] = p[x];
				Log.d("m in JSONDATA", m[i][j]);
				
				j = 0;
				i++;
			}
			else {
				//Log.d("m in JSONDATAin ELSE", m[i][j]);
				m[i][j] = p[x];
				j++;
			}
		}
		int rows = m.length;
		Log.d("parseJSONDATA","called2");
		Hashtable<String, ArrayList<String>> user_to_courses = new Hashtable<String, ArrayList<String>>();

		for(i = 0; i < rows; i++)
		{
//		 |	Betty	Huang	betty	sudikoff-1-2-ap, -56	CS1	|
			String usrname = m[i][2];
			if (usrname.equals(Globals.USERNAME)) {}
			else
			{
				//username already exists
				if (user_to_courses.containsKey(usrname)) { 
					//just add course
					user_to_courses.get(usrname).add(m[i][4]);
				}
				else
				{
					ArrayList<String> arr = new ArrayList<String>();
					arr.add(m[i][0]); //first name
					arr.add(m[i][1]); //last name
					String parseLocation = parseRouter(m[i][3]);
					arr.add(parseLocation); //location
					arr.add(m[i][4]); //course
					user_to_courses.put(usrname, arr);
					s = Utils.hashToString(user_to_courses);
					Log.d("parseJSONDATA:SSSSS",s);

				}
			}
			Log.d("parseJSONDATA","called3");
			
		}
		Log.d("returning.......................................",s);
		return s;
	}

	}

	


