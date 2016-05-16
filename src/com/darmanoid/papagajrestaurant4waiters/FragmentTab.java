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

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTab extends Fragment {
	TextView konobarIme;
	Info info;
	ImageButton stoTest;
	Button brza;
	Button logout;
	
	private static final int NUM_COLS = 3;
	int NUM_ROWS; 
	int brojStolova;
	int id=1;
	public String regionID;
	
	InputStream is=null;
	String result=null;
	String line=null;
	int brojac=0; //brojac torke
	
	public FragmentTab(String regionID) {
		//Konstruktro koji sadrzi ID regiona
		super();
		this.regionID = regionID;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
		View rootView = inflater.inflate(R.layout.stotest, container, false);
		TableLayout table=(TableLayout) rootView.findViewById(R.id.tableForButtons);
		
		konobarIme = (TextView) rootView.findViewById(R.id.textViewKONOBAR);
		konobarIme.setText("Konobar: "+Info.konobarIme);

		brza = (Button) rootView.findViewById(R.id.buttonBrzaPorudzba);
        brza.setOnClickListener(new OnClickListener () {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Info.stoNaziv="brza porudzba";
					Intent intent = new Intent();
					intent.setClass(getActivity(), MenuGrupeActivity.class);
					getActivity().startActivity(intent);
				}
        });
        logout = (Button) rootView.findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new OnClickListener () {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onLogoutPressed();
				}
        });
        	
		
		try
    	{
    		HttpClient httpclient = new DefaultHttpClient();
    		//Timeout je u milisekundama
    		HttpParams params = httpclient.getParams();
    		HttpConnectionParams.setConnectionTimeout(params, info.timeout);
    		HttpConnectionParams.setSoTimeout(params, info.timeout);
    		//
	        HttpPost httppost = new HttpPost("http://"+info.ip+"/papagaj/sto.php?id="+this.regionID);
	        HttpResponse response = httpclient.execute(httppost); 
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	        Log.e("pass 1", "connection success ");
    	}
        catch(Exception e)
        {
        	Log.e("Fail 1", e.toString());
        	//Potencijalno pucanje
	    	Toast.makeText(getActivity(), "Konekcija na server nije uspjela!",
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

			JSONArray jsonArray = jsonResponse.optJSONArray("sto");
			
			brojStolova=jsonArray.length();
	        NUM_ROWS=(brojStolova/NUM_COLS) + 1 ;
			
	        for(int row=0; row<NUM_ROWS; row++)
			{
				TableRow tableRow=new TableRow(getActivity());
				table.addView(tableRow); 
				for(int col=0; col<NUM_COLS && brojStolova>0; col++)
				{
					JSONObject child = jsonArray.getJSONObject(brojac);
					brojac++;
					
					String sto_id = child.getString("sto_id"); //Paznja kod kasnije generisanja poruka serveru
					String naziv=child.getString("naziv");
					
					final Button button=new Button(getActivity());
					button.setMinWidth(200);
					button.setMinHeight(160);
					tableRow.addView(button);
					brojStolova--;
					/*
					 * Dodajem svojstva dugmadima
					 */
					button.setText(naziv); 
					button.setId(id);
					id++;
					button.setLayoutParams(new TableRow.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f)); 
					button.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View arg0) {
							
							// TODO Auto-generated method stub
							Info.stoNaziv=(String) button.getText();
							//info.stoID=1;
							/*
							 * Regulisati ovdje ili u porudzba Activity id stola ako bude potrebno
							 */
							Intent intent = new Intent();
							intent.setClass(getActivity(), MenuGrupeActivity.class);
							getActivity().startActivity(intent);
						}
					});	
				}
			}
	        brojac=0; //osigurati se
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("parser za stolove:","ne radi");
		}
		
		/***BAZA****/
		return rootView; 
        
    }
	public void onLogoutPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	    builder.setTitle("Logout");
	    builder.setMessage("Da li zelite da se izlogujete?");

	    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
	        @Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent nextScreen = new Intent(getActivity(), LoginActivity.class);
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
