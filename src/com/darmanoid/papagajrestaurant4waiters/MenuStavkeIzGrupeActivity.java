package com.darmanoid.papagajrestaurant4waiters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MenuStavkeIzGrupeActivity extends Activity{

	//public final static String EXTRA_MESSAGE = "MESSAGE";
	private ListView obj;
	
	TextView nazivXML;
	Info stavkeInfo;
	
	TextView stoIme;
	TextView konobarIme;
	Button ponisti;
	Info info;
	InputStream is=null;
	String result=null;
	String line=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu);
        
        stoIme = (TextView) findViewById(R.id.textViewStoID);
        stoIme.setText(Info.stoNaziv);
        
        konobarIme = (TextView) findViewById(R.id.textViewKonobarID);
        konobarIme.setText(Info.konobarIme);//
        
        ponisti = (Button) findViewById(R.id.nazad);
        ponisti.setOnClickListener(new OnClickListener () {
   			@Override
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				Intent nextScreen = new Intent(getApplicationContext(), MenuGrupeActivity.class);
			    startActivity(nextScreen);
   			}
        });
        imeGrupe(info.grupaIDstr);
        ArrayList array_list = getAllJela(info.grupaIDstr);
     	ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
   	      
   	 	obj = (ListView)findViewById(R.id.listView1);
   	 	obj.setAdapter(arrayAdapter);
   	 	obj.setOnItemClickListener(new OnItemClickListener(){
   	         @Override
   	         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
   	            // TODO Auto-generated method stub
   	            int id_To_Search = arg2 + 1;
   	            
   	            Bundle dataBundle = new Bundle();
   	            dataBundle.putInt("id", id_To_Search);
   	           //Potencijalne grese zbog nesortiranosti po ID-u u bazi
   	         }
   	      });
   	      
   	   }
	 	public ArrayList<String> getAllJela(String  grupa_id)
	   {
	 		ArrayList<String> array_list = new ArrayList<String>();
	      
	      	try 
	     	{
	      		
	     		HttpClient httpclient = new DefaultHttpClient();
	     		//Timeout je u milisekundama
	    		HttpParams params = httpclient.getParams();
	    		HttpConnectionParams.setConnectionTimeout(params, info.timeout);
	    		HttpConnectionParams.setSoTimeout(params, info.timeout);
	    		//
	 	        HttpPost httppost = new HttpPost("http://"+info.ip+"/papagaj/artikal.php?id="+grupa_id);
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

	 			JSONArray jsonArray = jsonResponse.optJSONArray("artikal");
	 			//Log.i("stavke JSON:",result);
	 			for (int i = 0; i < jsonArray.length(); i++) {
	 				JSONObject child = jsonArray.getJSONObject(i);
	 				
	 				//String reg_id = child.getString("grupa_id");
	 				String naziv=child.getString("naziv");
	 				
	 				array_list.add(naziv);
	 			}
	 			
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			Log.i("parser artikli:","ne radi");
	 		}
	      //hp = new HashMap();
			return array_list;
	   }
	 	
	 	 private void imeGrupe(String idGrupe)
	     {
			
			try
	     	{
	     		HttpClient httpclient = new DefaultHttpClient();
	     		//Timeout je u milisekundama
	    		HttpParams params = httpclient.getParams();
	    		HttpConnectionParams.setConnectionTimeout(params, info.timeout);
	    		HttpConnectionParams.setSoTimeout(params, info.timeout);
	    		//
	 	        HttpPost httppost = new HttpPost("http://"+info.ip+"/papagaj/nazivgrupe.php?id="+idGrupe);
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

	 			JSONArray jsonArray = jsonResponse.optJSONArray("grupa");
	 			
	 			for (int i = 0; i < jsonArray.length(); i++) {
	 				JSONObject child = jsonArray.getJSONObject(i);

	 				String naziv=child.getString("naziv");
	 				//Log.i("naziv grupe:",naziv);
	 				nazivXML = (TextView) findViewById(R.id.textViewNaziv);
	 				nazivXML.setText(naziv);	
	 			}
	 			
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			Log.i("parser za stavke","ne radi");
	 		}
	     }
}
