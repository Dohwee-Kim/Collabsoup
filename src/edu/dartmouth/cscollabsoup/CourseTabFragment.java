package edu.dartmouth.cscollabsoup;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Button;
import android.content.Context;


public class CourseTabFragment extends Fragment {
	private Context mContext;
	private Button mButton;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	mContext = getActivity();
        View view = inflater.inflate(R.layout.courses, container, false);
        
        mButton = (Button)view.findViewById(R.id.btnSync);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	onSyncClicked(v);
            }
            
        });
        return view;
    }
    
    public void onSyncClicked(View v){
		// Later , need to implement putExtra for additional log in info  
		Intent intent = new Intent(mContext, ParseDataHelper.class);
		startActivity(intent);
    }

}
