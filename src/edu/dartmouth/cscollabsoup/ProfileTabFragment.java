package edu.dartmouth.cscollabsoup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class ProfileTabFragment extends Fragment{
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	View view = inflater.inflate(R.layout.profile_tab_fragment, container, false);
    	
    	
    	TextView userName = (TextView) view.findViewById(R.id.user_profile_tab_name);
    	userName.setText(getString(R.string.ui_profile_name_title) + " " + Globals.USERNAME);
    	
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
            }
        });
		return view;  
        
       

    }
    
    

}
