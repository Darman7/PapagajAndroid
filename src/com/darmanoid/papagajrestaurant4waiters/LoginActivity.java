package com.darmanoid.papagajrestaurant4waiters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	InputStream is=null;
	String result=null;
	String line=null;
	
	private EditText username;
	private Button login;
	ActionBar.Tab bastaTab,unutrasnjostTab;  
	 
	Info info;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        setupVariables(); //ID textfield-a i login buttona
    }

    public void authenticateLogin(View view) {
		view = this.getCurrentFocus();
		if (view != null) {  
		    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		if (autentifikacija()==true)
		{				
			Intent nextScreen = new Intent(getApplicationContext(), RegioniActivity.class);
		    startActivity(nextScreen);	
		               
		} else {
			Toast.makeText(getApplicationContext(), "Nepostojeci korisnik!", Toast.LENGTH_SHORT).show();
		}
	}
    
    /*
     * Cita iz textfida i pronalazi ID dugmete za login
     */
    private void setupVariables() {

        username = (EditText) findViewById(R.id.usernameET);
		login = (Button) findViewById(R.id.loginBtn);
    }
    

    private boolean autentifikacija()
    {
    	boolean postoji=false;
    	try
    	{
    		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://"+info.ip+"/papagaj/korisnik.php");
	        HttpResponse response = httpclient.execute(httppost); 
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	        Log.e("pass 1", "connection success ");
    	}
        catch(Exception e)
        {
        	Log.e("Fail 1", e.toString());
	    	Toast.makeText(getApplicationContext(), "Konekcija na server nije uspjela!",
			Toast.LENGTH_LONG).show();
        }     
        
        try
        {
         		BufferedReader reader = new BufferedReader
				(new InputStreamReader(is,"iso-8859-1"),8);
            	StringBuilder sb = new StringBuilder();
            	while ((line = reader.readLine()) != null)
            	{
            		sb.append(line + "\n");
           		}
            	is.close();
            	result = sb.toString();
            	//Log.i("izgled:",result);
            	Log.e("pass 2", "connection success ");
		}
	    catch(Exception e)
	    {
	    		Log.e("Fail 2", e.toString());
		}     
   
		JSONObject jsonResponse;
	
		try {
			jsonResponse = new JSONObject(result);

			JSONArray jsonArray = jsonResponse.optJSONArray("korisnik");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject child = jsonArray.getJSONObject(i);
				
				String kartica = child.getString("kartica");
				String ime=child.getString("ime");
				if(username.getText().toString().equals(kartica)) {
					postoji=true;
					info.konobarID=ime;
					//Log.i("ime:",ime);
					break;
				}
				postoji=false; //ako ponovo pokusavamo :) 
			}
			
			//parsirani.setText(output);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("parser Login:","ne radi");
		}
		return postoji;
    }
    @Override
    public void onBackPressed() {
       
    }
        
}