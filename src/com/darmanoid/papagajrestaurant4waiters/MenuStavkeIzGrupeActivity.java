package com.darmanoid.papagajrestaurant4waiters;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuStavkeIzGrupeActivity extends Activity{

	//public final static String EXTRA_MESSAGE = "MESSAGE";
	private ListView obj;
	
	TextView nazivXML;
	Info stavkeInfo;
	
	TextView stoIme;
	TextView konobarIme;
	Button ponisti;
	Info menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        TestAdapter mDbHelper = new TestAdapter(this); //Context 
        mDbHelper.createDatabase();
        mDbHelper.open();
        
        Cursor grupaNaziv = mDbHelper.getImeGrupePoIDu(Info.grupaID);
        stoIme = (TextView) findViewById(R.id.textViewStoID);
        stoIme.setText(Info.stoNaziv);
        
        konobarIme = (TextView) findViewById(R.id.textViewKonobarID);
        konobarIme.setText(Info.konobarID);//
        
        ponisti = (Button) findViewById(R.id.nazad);
        ponisti.setOnClickListener(new OnClickListener () {
   			@Override
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				Intent nextScreen = new Intent(getApplicationContext(), MenuGrupeActivity.class);
			    startActivity(nextScreen);
   			}
        });
        ///Ovo ocu dinamicki
        nazivXML = (TextView) findViewById(R.id.textViewNaziv);
        nazivXML.setText(grupaNaziv.getString(1));
        
     ArrayList array_list = mDbHelper.getAllJela(Info.grupaID);
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
   	           
   	         }
   	      });
   	      
   	   }
    
   }
