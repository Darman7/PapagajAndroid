package com.darmanoid.papagajrestaurant4waiters;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class RegioniActivity extends Activity{
	
	/*
	 *  Stolovi se prikazuju u dva taba
	 */
	Info info;
	InputStream is=null;
	String result=null;
	String line=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stolovi_frame);
		
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); //NAVIGATION_MODE_TABS
        // 
        actionBar.setDisplayShowHomeEnabled(false);
 
        // Da mi ne prikazuje ime aplikacije
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

       
        //Citanje baze:        
        try
    	{
    		HttpClient httpclient = new DefaultHttpClient();
    		//Timeout je u milisekundama
    		HttpParams params = httpclient.getParams();
    		HttpConnectionParams.setConnectionTimeout(params, info.timeout);
    		HttpConnectionParams.setSoTimeout(params, info.timeout);
    		//
	        HttpPost httppost = new HttpPost("http://"+info.ip+"/papagaj/region.php");
	        //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
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

			JSONArray jsonArray = jsonResponse.optJSONArray("region");
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject child = jsonArray.getJSONObject(i);
				
				String reg_id = child.getString("region_id");
				String naziv=child.getString("naziv");
				//UBACIVANJE NOVIH TABOVA! :) 
				ActionBar.Tab noviTab;
	        	noviTab=actionBar.newTab().setText(naziv);
	        	Fragment noviFragment = new FragmentTab(reg_id); 
	        	noviTab.setTabListener(new TabListener(noviFragment));
	        	actionBar.addTab(noviTab);   	
			}
			
			if(!(info.regionIDkonabara.equals("")))
			{
				if(info.regionIDkonabara.equals("1"))actionBar.setSelectedNavigationItem(2);
				else if(info.regionIDkonabara.equals("2"))actionBar.setSelectedNavigationItem(1);
				Log.i("broj regiona:",info.regionIDkonabara);
			}
			//parsirani.setText(output);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("parser regioni:","ne radi");
		}
		
	}
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

	    builder.setTitle("Logout");
	    builder.setMessage("Da li zelite da se izlogujete?");

	    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {

	        @Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
			    startActivity(nextScreen);
	            dialog.dismiss();
	        }

	    });

	    builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing
	            dialog.dismiss();
	        }
	    });

	    AlertDialog alert = builder.create();
	    alert.show();
	}

}
