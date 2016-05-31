package com.darmanoid.papagajrestaurant4waiters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
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
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PorudzbaActivity extends Activity {
		Info info;
		Button nazad;
		Button potvrdi;
		TextView racun;
		InputStream is=null;
		String result=null;
		String line=null;
		
		ListView lista;
		float iznos_racuna=0;
	    PorudzbaCustomAdapter adapter;
	    public  PorudzbaActivity CustomListView = null;
	    public  ArrayList<PorudzbaListModel> CustomListViewValuesArr = new ArrayList<PorudzbaListModel>();
	    
		protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.porudzba);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        
        CustomListView = this; //dodato
        setListData(info.konobarID,info.stoID);
        
        Resources res =getResources();
        lista= ( ListView )findViewById(R.id.poruceno);          
        adapter=new PorudzbaCustomAdapter( CustomListView, CustomListViewValuesArr,res );
        lista.setAdapter(adapter);
        
        postaviDugmad();
        racun=(TextView)findViewById(R.id.textViewIznos);
        DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		racun.setText("Iznos računa: "+df.format(iznos_racuna)+" €");
   	 	
    }
	private void setListData(String konobar_id,String sto_id) {
		try 
     	{
      		
     		HttpClient httpclient = new DefaultHttpClient();
     		//Timeout je u milisekundama
    		HttpParams params = httpclient.getParams();
    		HttpConnectionParams.setConnectionTimeout(params, info.timeout);
    		HttpConnectionParams.setSoTimeout(params, info.timeout);
    		//
 	        HttpPost httppost = new HttpPost("http://"+info.ip+"/papagaj/porudzba.php?sto_id="+sto_id+"&korisnik_id="+konobar_id);
 	        Log.e("sto id:", sto_id);
 	        
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

 			JSONArray jsonArray = jsonResponse.optJSONArray("porudzba");
 			//Log.i("stavke JSON:",result);
 			for (int i = 0; i < jsonArray.length(); i++) {
 				JSONObject child = jsonArray.getJSONObject(i);
 				
 				String kolicina=child.getString("kolicina");
 				String naziv=child.getString("artikal");
 				String cijena=child.getString("cijena");
 				iznos_racuna=iznos_racuna+Float.parseFloat(cijena);
 				PorudzbaListModel stavka = new PorudzbaListModel(kolicina,naziv,cijena);
 				CustomListViewValuesArr.add( stavka );
 			}
 			
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			Log.i("parser porudzba:","ne radi");
 		}
			
		}
	private void postaviDugmad()
	{
		 	nazad = (Button) findViewById(R.id.buttonNazad);
	        nazad.setOnClickListener(new OnClickListener () {
				@Override
				public void onClick(View v) {
					onBackPressed();
				}
	        });
	        potvrdi = (Button) findViewById(R.id.buttonPotvrdi);
	        potvrdi.setOnClickListener(new OnClickListener () {
				@Override
				//Sredjivati kasnije 
				public void onClick(View v) {
					porukaServeru(); 
					
					Toast.makeText(getApplicationContext(), "Porudzba zavrsena!", Toast.LENGTH_SHORT).show();
					Intent nextScreen = new Intent(getApplicationContext(), RegioniActivity.class);
		 		    startActivity(nextScreen);
				}
	        });
	}
	//Praviti moj JSON i proslijediti ga serveru
	private void porukaServeru()
	{
		
	}
	public void onItemClick(int mPosition) {
		// TODO Auto-generated method stub
		
	}
}
