package edu.dartmouth.cscollabsoup;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import edu.dartmouth.cscollabsoup.JSONParser;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity 
{
	
	private final String signup = "http://"+Globals.SERVER_IP+"/collabsoup/signup.php";
	private static final String TAG_SUCCESS = "success";

	private ProgressDialog pDialog;
	String firstName;
	String lastName;
	String email;
	String password;

	JSONParser jsonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		
	}
		
	public void onSignUpClicked(View v)
	{
		Toast.makeText(getApplicationContext(),
				"intent from Login to main screen..",
				Toast.LENGTH_SHORT).show();
		
		//CHANGED BY SAM!!!
		if (firstName == null || lastName == null || email == null || password == null){
			Toast.makeText(getApplicationContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
		}
		else
			new registerUser().execute();
	}
	

class registerUser extends AsyncTask<String, String, String> {

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
		pDialog = new ProgressDialog(SignUpActivity.this);

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
		firstName = ((EditText)findViewById(R.id.ui_sign_up_edit_first_name)).getText().toString();
		lastName = ((EditText)findViewById(R.id.ui_sign_up_edit_last_name)).getText().toString();
		email = ((EditText)findViewById(R.id.ui_sign_up_edit_email)).getText().toString();
		password = ((EditText)findViewById(R.id.ui_sign_up_edit_password)).getText().toString();

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("firstName", firstName));
		params.add(new BasicNameValuePair("lastName", lastName));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		

		// getting JSON Object
		// Note that create product url accepts POST method
		
		JSONObject json = jsonParser.makeHttpRequest(signup,
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
