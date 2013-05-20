package edu.dartmouth.cscollabsoup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SignUpActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		
	}
		
		public void onSignUpClicked(View v){
			Toast.makeText(getApplicationContext(),
					"intent from Login to main screen..",
					Toast.LENGTH_SHORT).show();
			
			// Later , need to implement putExtra for additional log in info  
			Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
			startActivity(intent);
	}
	
}
