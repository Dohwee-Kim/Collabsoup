package edu.dartmouth.cscollabsoup;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import edu.dartmouth.cscollabsoup.ParseDataHelper.postToDatabaseTask;
import android.graphics.Typeface;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class ProfileTabFragment extends Fragment{
	private static final String TAG_SUCCESS = "success";
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	View view = inflater.inflate(R.layout.profile_tab_fragment, container, false);
    	Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/3dumb.otf");
		Typeface btnface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/buttonfont.otf");
    	
		TextView profileTitle = (TextView) view.findViewById(R.id.profile_tab_title);
		
    	TextView userName = (TextView) view.findViewById(R.id.user_profile_tab_name);
    	userName.setText(getString(R.string.ui_profile_name_title) + " " + Globals.USERNAME);
    	profileTitle.setTypeface(typeface);
    	TextView firstName = (TextView) view.findViewById(R.id.user_profile_tab_first_name);
    	firstName.setText(getString(R.string.ui_profile_first_name) + " " + Globals.FIRSTNAME);
    	
    	TextView lastName = (TextView) view.findViewById(R.id.user_profile_tab_last_name);
    	lastName.setText(getString(R.string.ui_profile_last_name) + " " + Globals.LASTNAME);
    	
    	
    	
        Switch toggle = (Switch)view.findViewById(R.id.user_profile_broadcast_switch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The switch is on
                	Globals.SEND_BROADCAST = "on";
                	
                } else {
                    // The switch is off
                	Globals.SEND_BROADCAST = "off";
                }
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("broadcast", Globals.SEND_BROADCAST));
				params.add(new BasicNameValuePair("username", Globals.USERNAME));
				params.add(new BasicNameValuePair("password", Globals.PASSWORD));
		
				Globals.PHP_FILEPATH = "location_update.php";
				new updateAvailTask().execute(params);


            }
        });
		return view;  
    }
		class updateAvailTask extends AsyncTask<List<NameValuePair>, String, Void> {
			protected Void doInBackground(List<NameValuePair>... params) 
			{
				// getting JSON Object
				// Note that create product url accepts POST method
				JSONParser jsonParser = new JSONParser();
				String url_name = "http://"+Globals.SERVER_IP+"/collabsoup/"+Globals.PHP_FILEPATH;
				JSONObject json = jsonParser.makeHttpRequest(url_name,"POST", params[0]);
				// check log cat for response
				Log.d("Create Response", json.toString());

				// check for success tag
				try 
				{
					int success = json.getInt(TAG_SUCCESS);
					if (success == 1)
					{
//						Log.d("Collabsoup","SUCCESSFULLY POSTED TO SERVER");
						Log.d("ProfileTabFragment","Loc updatedddd");
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
