package edu.dartmouth.cscollabsoup;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;
import android.view.View;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;


public class LoginActivity extends Activity {

	public static final int REQUEST_SIGNIN = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println();
		setContentView(R.layout.log_in);
		// hi i am here 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		//betty is committing
	}
	
	// **************** button click callback ********************** //
	
	public void onSigninClicked(View v){
		Toast.makeText(getApplicationContext(),
				"intent from Login to main screen..",
				Toast.LENGTH_SHORT).show();
		
		// Later , need to implement putExtra for additional log in info  
		Intent intent = new Intent(this, MainActivity.class);
		try {
			startActivity(intent);
		}catch(ActivityNotFoundException e){
			//Log.d("DEBUGHERE", "NOOOOO");
			e.printStackTrace();
		}
		//startActivity(intent);
	}
}
