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
		
		SharedPreferences s_pref = 
				PreferenceManager.getDefaultSharedPreferences(v.getContext());
		Editor edit=s_pref.edit();
		
		String course1Value = s_pref.getString("course1", "notset");
		String course2Value = s_pref.getString("course2", "notset");
		String course3Value = s_pref.getString("course3", "notset");
		String course4Value = s_pref.getString("course4", "notset");

		if (course1Value.equals("notset"))
		{
			Log.d(TAG, "course 1 putString");
			edit.putString("course1", department + " " + courseNumber);
		}
		else if (course2Value.equals("notset"))
		{
			Log.d(TAG, "course 2 putString");
			edit.putString("course2", department + " " + courseNumber);
		}
		else if (course3Value.equals("notset"))
		{
			Log.d(TAG, "course 3 putString");
			edit.putString("course3", department + " " + courseNumber);
		}
		else if (course4Value.equals("notset"))
		{
			Log.d(TAG, "course 4 putString");
			edit.putString("course4", department + " " + courseNumber);
		}

        edit.commit();
        
		finish();
    }
}
