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
import android.content.res.Resources;
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

public class MenuArtikliActivity extends Activity{

	//public final static String EXTRA_MESSAGE = "MESSAGE";
	private ListView obj;
	
	TextView nazivXML;
	Info stavkeInfo;
	 
	TextView stoIme;
	TextView konobarIme;
	Button nazad;
	Button porudzba;
	Info info;
	InputStream is=null;
	String result=null;
	String line=null;
	
	///NOVO
	ListView lista;
    ArtikliCustomAdapter adapter;
    public  MenuArtikliActivity CustomListView = null;
    public  ArrayList<ArtikliListModel> CustomListViewValuesArr = new ArrayList<ArtikliListModel>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu);

        CustomListView = this; //dodato
        setListData(info.grupaIDstr);
        Resources res =getResources();
        lista= ( ListView )findViewById( R.id.listView1 );          
        adapter=new ArtikliCustomAdapter( CustomListView, CustomListViewValuesArr,res );
        lista.setAdapter( adapter );
        
        stoIme = (TextView) findViewById(R.id.textViewStoID);
        stoIme.setText("sto: "+Info.stoNaziv);
        
        konobarIme = (TextView) findViewById(R.id.textViewKonobarID);
        konobarIme.setText(Info.konobarIme);//
        
        nazad = (Button) findViewById(R.id.nazad);
        nazad.setOnClickListener(new OnClickListener () {
   			@Override
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				Intent nextScreen = new Intent(getApplicationContext(), MenuGrupeActivity.class);
			    startActivity(nextScreen);
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
        nazivXML = (TextView) findViewById(R.id.textViewNaziv);
		nazivXML.setText(info.grupaNaziv); 	      
   	   }
		private void setListData(String  grupa_id) {
		// TODO Auto-generated method stub
			
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
	 				
	 				
	 				String naziv=child.getString("naziv");
	 				String cijena=child.getString("cijena");
	 				String artikal_id=child.getString("artikal_id");
	 				ArtikliListModel stavka = new ArtikliListModel(naziv,cijena+" €",artikal_id);
	 				CustomListViewValuesArr.add( stavka );
	 			}
	 			
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			Log.i("parser artikli:","ne radi");
	 		}
		}
		
	 	
		public void onItemClick(int mPosition) {
			ArtikliListModel tempValues = ( ArtikliListModel ) CustomListViewValuesArr.get(mPosition);
			info.jeloID=tempValues.getId();
			info.nazivJela=tempValues.getNaziv();
			info.cijena=tempValues.getCijena();
			
			Intent nextScreen = new Intent(getApplicationContext(), PriloziActivity.class);
            startActivity(nextScreen);
            //Toast.makeText(CustomListView,""+tempValues.getNaziv()+" "+tempValues.getId(),Toast.LENGTH_LONG).show();
			//kasnije da prebaci na drugi ACtivity
		}
}
