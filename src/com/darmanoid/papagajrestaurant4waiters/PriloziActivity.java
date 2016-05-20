package com.darmanoid.papagajrestaurant4waiters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

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
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PriloziActivity extends Activity{
	TextView nazivArtikla;
	TextView cijena;
	TextView kolicina;
	Button dodaj;
	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;
	Button btn5;
	Button btn6;
	Button btn7;
	Button btn8;
	Button btn9;
	Button nazad;
	
	String pom;
	float s;
	Info info;
	
	InputStream is=null;
	String result=null;
	String line=null;
	LinearLayout layoutRadioChk;
	int idDefault;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.prilozi);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        
        layoutRadioChk=(LinearLayout) findViewById(R.id.zaRadio);
        
        setupVariables();
        radioDugmad();
    }
	private void radioDugmad()
	{
		try
    	{
    		HttpClient httpclient = new DefaultHttpClient();
    		//Timeout je u milisekundama
    		HttpParams params = httpclient.getParams();
    		HttpConnectionParams.setConnectionTimeout(params, info.timeout);
    		HttpConnectionParams.setSoTimeout(params, info.timeout);
    		//
	        HttpPost httppost = new HttpPost("http://"+info.ip+"/papagaj/artikaldodaci.php?id="+info.jeloID);
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
			JSONArray jsonArray = jsonResponse.optJSONArray("zahtjevi");
			
			for(int i=0; i<jsonArray.length(); i++)
			{
				TableRow tableRow=new TableRow(getApplicationContext());
				//table.addView(tableRow); 
				
				JSONObject opis=jsonArray.getJSONObject(i); 
				String dg_naziv=opis.getString("dg_naziv");
				TextView nazivTV=new TextView(this);
				nazivTV.setText(dg_naziv);
				nazivTV.setTextSize(20f);
				nazivTV.setTypeface(null, Typeface.BOLD);
				layoutRadioChk.addView(nazivTV);
				String dg_tip=opis.getString("dg_tip"); //0 je radio Button
				//Log.i("dg_naziv:",dg_naziv);
				
				JSONArray opcijeArray=opis.optJSONArray("opcije");
				if(dg_tip.equals("0")) //crtam radio dugmad
				{
					RadioGroup group = new RadioGroup(this); 
					group.setOrientation(RadioGroup.HORIZONTAL);
					for(int j=0; j<opcijeArray.length();j++)
					{
						//dohvatam iz najdubljih nizova objekte
						JSONObject child=opcijeArray.getJSONObject(j);
						String dodatak_naziv=child.getString("dodatak_naziv");
						String defaultradio=child.getString("defaultradio");

						RadioButton btn=new RadioButton(this);
						btn.setText(dodatak_naziv);
						btn.setTextSize(20f);
						btn.setId(j);
						if(defaultradio.equals("1")) idDefault=btn.getId() ;
						group.addView(btn);
					}
					group.check(idDefault);
					layoutRadioChk.addView(group);
					
				}
				else if(dg_tip.equals("1")) //Crtam chk boxeve
				{
					
					for(int j=0; j<opcijeArray.length();j++)
					{
						//dohvatam iz najdubljih nizova objekte
						JSONObject child=opcijeArray.getJSONObject(j);
						String dodatak_naziv=child.getString("dodatak_naziv");
						String defaultradio=child.getString("defaultradio");
						
						CheckBox chk=new CheckBox(this);
						chk.setText(dodatak_naziv);
						chk.setTextSize(20f);
						if(defaultradio.equals("1")) chk.setChecked(true);
						layoutRadioChk.addView(chk); 
					}
				}
				TextView razmak=new TextView(this);
				razmak.setText(" ");
				layoutRadioChk.addView(razmak);
				
					
			}	
		}	
		catch (Exception e) {
			Log.i("parser za priloge:","ne radi");
		}
	}	
	
	private void racunaj(int kol)
	{
		pom=info.cijena;
		int i=pom.length();
		pom=pom.substring(0, i-1);
		
		Float z=Float.parseFloat(pom);
		z=z*kol;
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		cijena.setText(df.format(z)+" €");
	}
	private void setupVariables() {
		nazivArtikla = (TextView) findViewById(R.id.textNazivArtikla);
        cijena = (TextView) findViewById(R.id.textCijena);
        kolicina = (TextView) findViewById(R.id.textKolicina);
        
        nazivArtikla.setText(info.nazivJela);
        cijena.setText(info.cijena);
        kolicina.setText("1 X");
        
        dodaj = (Button) findViewById(R.id.buttonDodajStavku);
        dodaj.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY); //Mijenjanje boje iz koda :))
        
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        nazad= (Button) findViewById(R.id.buttonNazad);
        
        btn1.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				kolicina.setText("1 X");
				racunaj(1);
			}
        });
        btn2.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				kolicina.setText("2 X");
				racunaj(2);
			}
        });
        btn3.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				kolicina.setText("3 X");
				racunaj(3);
			}
        });
        btn4.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				kolicina.setText("4 X");
				racunaj(4);
			}
        });
        btn5.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				kolicina.setText("5 X");
				racunaj(5);
			}
        });
        btn6.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				kolicina.setText("6 X");
				racunaj(6);
			}
        });
        btn7.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				kolicina.setText("7 X");
				racunaj(7);
			}
        });
        btn8.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				kolicina.setText("8 X");
				racunaj(8);
			}
        });
        btn9.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				kolicina.setText("9 X");
				racunaj(9);
			}
        });
        nazad.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				Intent nextScreen = new Intent(getApplicationContext(), MenuArtikliActivity.class);
			    startActivity(nextScreen);
			}
        });
        
	}
}
