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
		
		
		ArrayList<CheckBox> checkBoxCollection = new ArrayList<CheckBox>();
		final CheckBox cb1 = new CheckBox(this);
		final CheckBox cb2 = new CheckBox(this);
		final CheckBox cb3 = new CheckBox(this);
		final CheckBox cb4 = new CheckBox(this);
		
		checkBoxCollection.add(cb1);
		checkBoxCollection.add(cb2);
		checkBoxCollection.add(cb3);
		checkBoxCollection.add(cb4);
		
		for(int i = 0; i < Globals.NUMBER_OF_COURSES; i++){
			checkBoxCollection.get(i).setText(Globals.courseInfo.get(i).get(0) + " " + Globals.courseInfo.get(i).get(1));
			//cb.setText(Globals.courseInfo.get(i).get(0) + " " + Globals.courseInfo.get(i).get(1));			
			ll.addView(checkBoxCollection.get(i));
		}
		
		Button b = new Button(this);
		b.setText("Delete !"); 
		b.setId(1004);
		ll.addView(b);
		
		b.setOnClickListener(new View.OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				Toast.makeText(getApplicationContext(), "Deleting ....", Toast.LENGTH_SHORT).show();
 				if(cb1.isChecked()) {
 					Globals.courseInfo.remove(0);
 					Globals.NUMBER_OF_COURSES-=1;
 				}
 				if(cb2.isChecked()){
 					Globals.courseInfo.remove(1);
 					Globals.NUMBER_OF_COURSES-=1;
 				}
 				if(cb3.isChecked()){
 					Globals.courseInfo.remove(2);
 					Globals.NUMBER_OF_COURSES-=1;
 				}
 				if(cb4.isChecked()){
 					Globals.courseInfo.remove(4);
 					Globals.NUMBER_OF_COURSES-=1;
 				}
 				finish();
 			}
 	});
		
		
		
		this.setContentView(sv);
		//setContentView(R.layout.delete_course);
		
	}
	
}