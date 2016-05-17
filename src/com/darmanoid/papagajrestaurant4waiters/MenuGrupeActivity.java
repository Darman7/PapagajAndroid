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
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class MenuGrupeActivity extends Activity{
	
	public final static String EXTRA_MESSAGE = "MESSAGE";
	private ListView obj;
	TextView stoIme;
	TextView konobarIme;
	Button ponisti;
	Button vratiSe;
	Info info;
	InputStream is=null;
	String result=null;
	String line=null;
	String[] id=null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
     super.onCreate(savedInstanceState);
     requestWindowFeature(Window.FEATURE_NO_TITLE);
     setContentView(R.layout.menu);
     
     stoIme = (TextView) findViewById(R.id.textViewStoID);
     stoIme.setText("sto: "+Info.stoNaziv);// Za prosledjivanje imena stola
     
     konobarIme = (TextView) findViewById(R.id.textViewKonobarID);
     konobarIme.setText(Info.konobarIme);//
     
     ponisti = (Button) findViewById(R.id.nazad);
     ponisti.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
     });
     
     vratiSe = (Button) findViewById(R.id.buttonforward);
     vratiSe.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(info.grupaID!=-1)
				{
					Intent nextScreen = new Intent(getApplicationContext(), MenuStavkeIzGrupeActivity.class);
	                startActivity(nextScreen);
				}
				
			}
     });
     
	 ArrayList array_list = getAllGrupe();
	 ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
	      
	 obj = (ListView)findViewById(R.id.listView1);
	 obj.setAdapter(arrayAdapter);
	 obj.setOnItemClickListener(new OnItemClickListener(){
	         @Override
	         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
	            // TODO Auto-generated method stub
	            //int id_To_Search = arg2 + 1;
	            //Bundle dataBundle = new Bundle();
	            //dataBundle.putInt("id", id_To_Search);
	            //Info.grupaID=id_To_Search;
	            
	            Info.grupaIDstr=id[arg2];
	            //Log.i("moj niz:",Info.grupaIDstr);
	            Intent nextScreen = new Intent(getApplicationContext(), MenuStavkeIzGrupeActivity.class);
                startActivity(nextScreen);
	         }
	      });
	      
	   }
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

	    builder.setTitle("Ponisti porudzbu");
	    builder.setMessage("Da li zelite da ponistite porudzbu?");
 
	    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {

	        @Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent nextScreen = new Intent(getApplicationContext(), RegioniActivity.class);
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
	
	public ArrayList<String> getAllGrupe()
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
	 	        HttpPost httppost = new HttpPost("http://"+info.ip+"/papagaj/grupa.php");
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

	 			JSONArray jsonArray = jsonResponse.optJSONArray("grupa");
	 			id=new String[jsonArray.length()];
	 			for (int i = 0; i < jsonArray.length(); i++) {
	 				JSONObject child = jsonArray.getJSONObject(i);
	 				
	 				String reg_id = child.getString("grupa_id");
	 				String naziv=child.getString("naziv");
	 				id[i]=reg_id;
	 				array_list.add(naziv);
	 			}
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			Log.i("parser menu:","ne radi");
	 		}
	   return array_list;
	 }
}
