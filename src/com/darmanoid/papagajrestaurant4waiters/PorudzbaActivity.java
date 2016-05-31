package com.darmanoid.papagajrestaurant4waiters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PorudzbaActivity extends Activity {
		Info info;
		Button nazad;
		Button potvrdi;
		TextView racun;
		private ListView obj;
		
		protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.porudzba);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        
        postaviDugmad();
        ArrayList array_list = info.poruceno;
        final ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        
        obj = (ListView)findViewById(R.id.poruceno);
   	 	obj.setAdapter(arrayAdapter);
   	 	obj.setOnItemLongClickListener(new OnItemLongClickListener(){
   	         @Override
   	         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
   	        	//Toast.makeText(getApplicationContext(), "Ukloni ga", Toast.LENGTH_SHORT).show();
   	        	String item = (String) arrayAdapter.getItem(position);
                arrayAdapter.remove(arrayAdapter.getItem(position));
                arrayAdapter.notifyDataSetChanged();
   	        	return true;
   	         }
   	         
   	      });
   	      

   	 	racun=(TextView)findViewById(R.id.textViewIznos);
   	 	racun.setText("Iznos računa: "+info.ukupanIznos);
   	 	
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
					info.poruceno.clear();
					info.ukupanIznos="0.0 €";
					//info.ocisti(); // WUT?
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
}
