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


import java.util.ArrayList;
import edu.dartmouth.cscollabsoup.Globals;

public class CourseTabFragment extends Fragment {
	private Context mContext;
	private Button mButton, mAddCoursesButton, mdeleteButton;
	private static final String TAG = "COLLAB";
	private int first_check_flag = 0;
	public static final int REQUEST_CODE = 1;
	int code = 1;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	mContext = getActivity();
        View view = inflater.inflate(R.layout.courses, container, false);
        
        mButton = (Button)view.findViewById(R.id.btnSync);
        mdeleteButton = (Button)view.findViewById(R.id.courseDeleteButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	onSyncClicked(v);
            }
            
        });
        
        mdeleteButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onDeleteClicked(v);
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
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    	Log.d(Globals.TAG, "in onActivityResult");
    	super.onActivityResult(requestCode, resultCode, intent);
    	
    	if (requestCode == REQUEST_CODE)
    	{
    		updateTextView();
    	}
    }
    
    public void onDeleteClicked(View v){
		// Later , need to implement putExtra for additional log in info  
		Intent intent = new Intent(mContext, DeleteCourses.class);
		startActivityForResult(intent, code);
    }
    
    public void onSyncClicked(View v){
		// Later , need to implement putExtra for additional log in info  
		Intent intent = new Intent(mContext, ParseDataHelper.class);
		startActivity(intent);
    }
    
    @Override
    public void onResume()
    {
    	updateTextView();
    	super.onResume();
    }
    
    public void updateTextView(){
    	//SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

    	TextView firstCourse = (TextView) getView().findViewById(R.id.first_course);
		TextView secondCourse = (TextView) getView().findViewById(R.id.second_course);
		TextView thirdCourse = (TextView) getView().findViewById(R.id.third_course);
		TextView fourthCourse = (TextView) getView().findViewById(R.id.fourth_course);
		
		ArrayList<TextView> TextViewCollection = new ArrayList<TextView>();
		
		TextViewCollection.add(firstCourse);
		TextViewCollection.add(secondCourse);
		TextViewCollection.add(thirdCourse);
		TextViewCollection.add(fourthCourse);
		
		//if(Globals.NUMBER_OF_COURSES == )
		
		for( int i = 0 ; i < Globals.NUMBER_OF_COURSES; i++){
			TextViewCollection.get(i).setText(Globals.courseInfo.get(i).get(0) + " " + Globals.courseInfo.get(i).get(1));
		}
		
//		Log.d(TAG, firstCourse.getText().toString());
//		Log.d(TAG, secondCourse.getText().toString());
//		Log.d(TAG, thirdCourse.getText().toString());
//		Log.d(TAG, fourthCourse.getText().toString());
//		
////		if (first_check_flag == 0)
//		{
//			first_check_flag = 1;
//		} else 
		
    }
        
}
