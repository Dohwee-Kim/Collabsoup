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
import android.graphics.Typeface;

public class CourseTabFragment extends Fragment {
	private Context mContext;
	private Button mButton, mAddCoursesButton, mDeleteCoursesButton;
	private static final String TAG = "COLLAB";
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	mContext = getActivity();
        View view = inflater.inflate(R.layout.courses, container, false);
        
        mButton = (Button)view.findViewById(R.id.btnSync);
        Typeface btnface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/buttonfont.otf");
        mButton.setTypeface(btnface);
        
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	onSyncClicked(v);
            }
            
        });
        
        
        mAddCoursesButton = (Button) view.findViewById(R.id.coursePickerButton);
        mAddCoursesButton.setTypeface(btnface);
        Log.d(TAG, "setting on click Listener");
        mAddCoursesButton.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				Intent intent = new Intent (getActivity(), AddCourses.class);
 				Log.d(TAG, "intent Created");
 				getActivity().startActivity(intent);
 				
 			}
        });
        
        mDeleteCoursesButton = (Button) view.findViewById(R.id.deletebtn);
        mDeleteCoursesButton.setTypeface(btnface);
        Log.d(TAG, "setting on click Listener");
        mDeleteCoursesButton.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				
 				Intent intent = new Intent(mContext, DeleteCourses.class);
 				Log.d(TAG, "intent Created");
 				startActivity(intent);
 				
 			}
        });
        
        return view;
    }
    
    
    public void onSyncClicked(View v){
		// Later , need to implement putExtra for additional log in info 
    	SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
    	Typeface coursefont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/capture_it.otf");
    	String course1=s_pref.getString("course1", " ");
    	String course2=s_pref.getString("course2", " ");
    	String course3=s_pref.getString("course3", " ");
    	String course4 =s_pref.getString("course4", " ");
		Globals.course1 = course1;
		Globals.course2 = course2;
		Globals.course3 = course3;
		Globals.course4 = course4;
		
		Log.d("course1:",course1);
		Log.d("course2:",course2);
		Log.d("course3:",course3);
		Log.d("course4:",course4);

    	if (Globals.SEND_BROADCAST.equals("on")){
			Intent intent = new Intent(mContext, ParseDataHelper.class);
			startActivity(intent);
    	}
    	else
				Toast.makeText(mContext, "You have to broadcast your location if you want help, don't be greedy.", Toast.LENGTH_SHORT).show();
			

		
		
    }
    
    
    public void refreshViews()
    {
    	SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
    	Typeface coursefont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/capture_it.otf");
    	TextView firstCourse = (TextView) getView().findViewById(R.id.first_course);
		TextView secondCourse = (TextView) getView().findViewById(R.id.second_course);
		TextView thirdCourse = (TextView) getView().findViewById(R.id.third_course);
		TextView fourthCourse = (TextView) getView().findViewById(R.id.fourth_course);
		
		Log.d(TAG, firstCourse.getText().toString());
		Log.d(TAG, secondCourse.getText().toString());
		Log.d(TAG, thirdCourse.getText().toString());
		Log.d(TAG, fourthCourse.getText().toString());
		
		if (!s_pref.getString("course1", " ").equals(" "))
		{
    		Log.d(TAG, "course 1 setText");
    		firstCourse.setTypeface(coursefont);
    		firstCourse.setText(s_pref.getString("course1", " "));
		}

    	if (!s_pref.getString("course2", " ").equals(" "))
		{
    		Log.d(TAG, "course 2 setText");
    		secondCourse.setTypeface(coursefont);
    		secondCourse.setText(s_pref.getString("course2", " "));
		}

    	if (!s_pref.getString("course3", " ").equals(" "))
		{
    		Log.d(TAG, "course 3 setText");
    		thirdCourse.setTypeface(coursefont);
    		thirdCourse.setText(s_pref.getString("course3", " "));
		}
    	
    	if (!s_pref.getString("course4", " ").equals(" "))
		{
    		Log.d(TAG, "course 4 setText");
    		fourthCourse.setTypeface(coursefont);
    		fourthCourse.setText(s_pref.getString("course4", " "));
		}
    	
    	if(Globals.JUST_PRESSED_DELETE == 1)
    	{
    		
    		if (s_pref.getString("course1", " ").equals(" "))
    		{
        		Log.d(TAG, "course 1 setText");
        		firstCourse.setText("");
    		}

        	if (s_pref.getString("course2", " ").equals(" "))
    		{
        		Log.d(TAG, "course 2 setText");
        		secondCourse.setText("");
    		}

        	if (s_pref.getString("course3", " ").equals(" "))
    		{
        		Log.d(TAG, "course 3 setText");
        		thirdCourse.setText("");
    		}
        	
        	if (s_pref.getString("course4", " ").equals(" "))
    		{
        		Log.d(TAG, "course 4 setText");
        		fourthCourse.setText("");
    		}
    		Globals.JUST_PRESSED_DELETE = 0;
    	}
    }
    
    @Override
    public void onResume()
    {
    	refreshViews();
        super.onResume();
    }
}
