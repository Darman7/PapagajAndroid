package com.darmanoid.papagajrestaurant4waiters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MenuGrupeActivity extends Activity{
	
	
	private ListView obj;
	TextView stoIme;
	TextView konobarIme;
	Button ponisti;
	Button vratiSe;
	Button porudzba;
	Info info;
	InputStream is=null;
	String result=null;
	String line=null;
	String slika=null;
	
	ListView lista;
    GrupeCustomAdapter adapter;
    public  MenuGrupeActivity CustomListView = null;
    public  ArrayList<GrupeListModel> CustomListViewValuesArr = new ArrayList<GrupeListModel>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 
     super.onCreate(savedInstanceState);
     requestWindowFeature(Window.FEATURE_NO_TITLE);
     setContentView(R.layout.menu);
     
     
     CustomListView = this; //dodato
     setListData(info.grupaIDstr);
     Resources res =getResources();
     lista= ( ListView )findViewById( R.id.listView1 );          
     adapter=new GrupeCustomAdapter( CustomListView, CustomListViewValuesArr,res );
     lista.setAdapter( adapter );
     
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
					Intent nextScreen = new Intent(getApplicationContext(), MenuArtikliActivity.class);
	                startActivity(nextScreen);
				}
				
			}
     });
     porudzba = (Button) findViewById(R.id.buttonPorudzba);
     porudzba.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent nextScreen = new Intent(getApplicationContext(), PorudzbaActivity.class);
	                startActivity(nextScreen);
			}
     });
        
	   }
	  
	private void setListData(String grupaIDstr) {
		try
     	{
     		HttpClient httpclient = new DefaultHttpClient();
     		//Timeout je u milisekundama
    		HttpParams params = httpclient.getParams();
    		HttpConnectionParams.setConnectionTimeout(params, info.timeout);
    		HttpConnectionParams.setSoTimeout(params, info.timeout);
    		//
 	        HttpPost httppost = new HttpPost("http://"+info.ip+"/papagaj/grupaslike.php");
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
 				
 				String reg_id = child.getString("grupa_id");
 				String naziv=child.getString("naziv");
 				String image=child.getString("slika");
 				Bitmap sl=base64ToBitmap(image);
 				GrupeListModel stavka = new GrupeListModel(naziv,reg_id,sl);
 				CustomListViewValuesArr.add( stavka );
 			}
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			Log.i("parser menu:","ne radi");
 		}
		
	}
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

	    builder.setTitle("Poništi porudžbu");
	    builder.setMessage("Da li želite da poništite porudžbu?");
 
	    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {

	        @Override
			public void onClick(DialogInterface dialog, int which) {
	        	info.poruceno.clear();
	        	info.ukupanIznos="0.0 €";
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
	
	//Za dekodiranje slika
	private Bitmap base64ToBitmap(String b64) {
	    byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
	    return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
	}

	public void onItemClick(int mPosition) {
		GrupeListModel tempValues = ( GrupeListModel ) CustomListViewValuesArr.get(mPosition);
		info.grupaIDstr=tempValues.getId();
		info.grupaNaziv=tempValues.getNaziv();
		
		Intent nextScreen = new Intent(getApplicationContext(), MenuArtikliActivity.class);
        startActivity(nextScreen);
		
	}
}
