package edu.dartmouth.cscollabsoup;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;


public class LoginActivity extends Activity{

	public static final int REQUEST_SIGNIN = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println();
		setContentView(R.layout.log_in);
		// hi i am here 
		
		TextView noAccountTextView = (TextView) findViewById (R.id.NoAccountTextView);
		noAccountTextView.setTextColor(Color.BLUE);
		noAccountTextView.setOnClickListener(new OnClickListener(){
			public void onClick (View v){
				Toast.makeText(getApplicationContext(), "intent from Login to create account",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent (LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});
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
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
	}
}
