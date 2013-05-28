package edu.dartmouth.cscollabsoup;

import android.app.Activity;
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
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

import edu.dartmouth.cscollabsoup.Globals;


public class DeleteCourses extends Activity{
	private static final String TAG = "COLLAB";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);
		
		// Adding Textview 
		TextView tv = new TextView(this);
		tv.setText("Check Courses that you want to delete");
		ll.addView(tv);
		
		final CheckBox cb1 = new CheckBox(this);
		final CheckBox cb2 = new CheckBox(this);
		final CheckBox cb3 = new CheckBox(this);
		final CheckBox cb4 = new CheckBox(this);
		
		SharedPreferences s_pref =
				PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor edit=s_pref.edit();
		
		String course1Value = s_pref.getString("course1", "notset");
		String course2Value = s_pref.getString("course2", "notset");
		String course3Value = s_pref.getString("course3", "notset");
		String course4Value = s_pref.getString("course4", "notset");
		
		if (!course1Value.equals("notset")){
			cb1.setText(course1Value);			
			ll.addView(cb1);
		}
		if (!course2Value.equals("notset")){
			cb2.setText(course2Value);			
			ll.addView(cb2);
		}
		if (!course3Value.equals("notset")){
			cb3.setText(course3Value);			
			ll.addView(cb3);
		}
		if (!course4Value.equals("notset")){
			cb4.setText(course4Value);			
			ll.addView(cb4);
		}
			
		Button b = new Button(this);
		b.setText("Delete !"); 
		b.setId(1004);
		ll.addView(b);
		
		b.setOnClickListener(new View.OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				Toast.makeText(getApplicationContext(), "Deleting ....", Toast.LENGTH_SHORT).show();
 				
 				SharedPreferences s_pref =
 						PreferenceManager.getDefaultSharedPreferences(v.getContext());
 				Editor edit=s_pref.edit();
 				
 				if(cb1.isChecked()) {
 					edit.remove("course1");
 				}
 				if(cb2.isChecked()){
 					edit.remove("course2");
 				}
 				if(cb3.isChecked()){
 					edit.remove("course3");
 				}
 				if(cb4.isChecked()){
 					edit.remove("course4");
 				}
 				
 				edit.commit();
 				Globals.JUST_PRESSED_DELETE = 1;
 				finish();
 			}
 	});
		
		
		
		this.setContentView(sv);
		//setContentView(R.layout.delete_course);
		
	}
	
}