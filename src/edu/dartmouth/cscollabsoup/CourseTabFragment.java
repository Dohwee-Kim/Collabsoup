package edu.dartmouth.cscollabsoup;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.Context;


public class CourseTabFragment extends Fragment {
	private Context mContext;
	private Button mButton, mAddCoursesButton;
	private static final String TAG = "COLLAB";
	private int first_check_flag = 0;
	
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
        
        
        mAddCoursesButton = (Button) view.findViewById(R.id.coursePickerButton);
        
        Log.d(TAG, "setting on click Listener");
        mAddCoursesButton.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				Intent intent = new Intent (getActivity(), AddCourses.class);
 				Log.d(TAG, "intent Created");
 				getActivity().startActivity(intent);
 				
 			}
 	});
        return view;
    }
    

    
    public void onSyncClicked(View v){
		// Later , need to implement putExtra for additional log in info  
		Intent intent = new Intent(mContext, ParseDataHelper.class);
		startActivity(intent);
    }
    
    @Override
    public void onResume()
    {
    	SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

    	TextView firstCourse = (TextView) getView().findViewById(R.id.first_course);
		TextView secondCourse = (TextView) getView().findViewById(R.id.second_course);
		TextView thirdCourse = (TextView) getView().findViewById(R.id.third_course);
		TextView fourthCourse = (TextView) getView().findViewById(R.id.fourth_course);
		
		Log.d(TAG, firstCourse.getText().toString());
		Log.d(TAG, secondCourse.getText().toString());
		Log.d(TAG, thirdCourse.getText().toString());
		Log.d(TAG, fourthCourse.getText().toString());
		
		if (first_check_flag == 0)
		{
			first_check_flag = 1;
		}
		else if (firstCourse.getText().toString().equals(" "))
		{
    		Log.d(TAG, "course 1 setText");
    		firstCourse.setText(s_pref.getString("course1", "default text"));
		}
    	if (secondCourse.getText().toString().equals(" "))
		{
    		Log.d(TAG, "course 2 setText");
    		secondCourse.setText(s_pref.getString("course2", " "));
		}
    	if (thirdCourse.getText().toString().equals(" "))
		{
    		Log.d(TAG, "course 3 setText");
    		thirdCourse.setText(s_pref.getString("course3", " "));
		}
    	if (fourthCourse.getText().toString().equals(" "))
		{
    		Log.d(TAG, "course 4 setText");
    		fourthCourse.setText(s_pref.getString("course4", " "));
		}
		
        super.onResume();
    }
}
