package edu.dartmouth.cscollabsoup;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.ArrayList;
import edu.dartmouth.cscollabsoup.Globals;   // need to access global variable 

public class AddCourses extends Activity{

	private static final String TAG = "COLLAB";
	private Spinner mSpinnerDpmt;
	private Spinner mSpinnerCourseNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_course);
		
		mSpinnerDpmt = (Spinner) findViewById(R.id.dpmt_spinner);
		mSpinnerCourseNumber = (Spinner) findViewById (R.id.course_spinner);
		
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, android.R.id.text1);

		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerCourseNumber.setAdapter(spinnerAdapter);

		for (int i = 1; i <= 250; i++) spinnerAdapter.add(String.valueOf(i));
		spinnerAdapter.notifyDataSetChanged();
	}
	
	public void onAddCourseClicked(View v){
  
		String department = (String) mSpinnerDpmt.getSelectedItem();
		String courseNumber = (String) mSpinnerCourseNumber.getSelectedItem();
		
		// if User already enrolled 4 courses 
		if (Globals.NUMBER_OF_COURSES == 4){
			Toast.makeText(getApplicationContext(), "You already have 4 courses, Cannot add more !",
					Toast.LENGTH_SHORT).show();
			finish();
		}
		else{
			ArrayList<String> courseInfo = new ArrayList<String>();
			courseInfo.add(department);
			courseInfo.add(courseNumber);
			Globals.courseInfo.add(courseInfo);
			Globals.NUMBER_OF_COURSES +=1;
			finish();
		}
		
	}
}
