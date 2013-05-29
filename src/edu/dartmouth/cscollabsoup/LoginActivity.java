package edu.dartmouth.cscollabsoup;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.net.InetAddress;
import java.net.UnknownHostException;



public class LoginActivity extends Activity{

	
	private static final String TAG_SUCCESS = "success";
	String username;
	String password;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	String login="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		System.out.println();
		setContentView(R.layout.log_in);
		// hi i am here 
		login = "http://"+Globals.SERVER_IP+"/collabsoup/login.php";

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
	
	//like async task runs in bg
	public void onSigninClicked(View v){
//		Toast.makeText(getApplicationContext(),
//				"intent from Login to main screen..",
//				Toast.LENGTH_SHORT).show();
		
		// Later , need to implement putExtra for additional log in info  
		username = ((EditText)findViewById(R.id.login_username)).getText().toString();
		password = ((EditText)findViewById(R.id.login_password)).getText().toString();
		
		
		//ATTEMPTED TO GET THIS TO WORK... DIDN'T WORK
		if (username.equals(null) || password.equals(null)){
			Toast.makeText(getApplicationContext(), "Please enter your username.", Toast.LENGTH_SHORT).show();
		}
		else
			new loginUser().execute();
	}

class loginUser extends AsyncTask<String, String, String> {

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	
	@Override
	protected void onPreExecute() 
	{
		//shows the little register circle
		super.onPreExecute();
		pDialog = new ProgressDialog(LoginActivity.this);

		pDialog.setMessage("Registering..");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	/**
	 * Creating product
	 * */
	protected String doInBackground(String... args) 
	{
		
		
		
		username = ((EditText)findViewById(R.id.login_username)).getText().toString();
		password = ((EditText)findViewById(R.id.login_password)).getText().toString();
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		
		// getting JSON Object
		// Note that create product url accepts POST method
		
		JSONObject json = jsonParser.makeHttpRequest(login,
				"POST", params);
		
		// check log cat fro response
		Log.d("Create Response", json.toString());

		// check for success tag
		try 
		{
			int success = json.getInt(TAG_SUCCESS);
			
			if (success == 1)
			{
				// successfully created product
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				Globals.USERNAME = username;
				Globals.PASSWORD = password;
				Globals.FIRSTNAME = json.getString("firstname");
				Globals.LASTNAME = json.getString("lastname");
				startActivity(i);
				
				
				
				// closing this screen
				finish();
			}
			else 
			{
				// failed to create product
				Log.d("CollabSoup:SignUpActivity","Failed to regitser ");
			}
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String file_url) 
	{
		// dismiss the dialog once done
		pDialog.dismiss();
	}

}
}
