package com.darmanoid.papagajrestaurant4waiters;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuGrupeActivity extends Activity{
	
	public final static String EXTRA_MESSAGE = "MESSAGE";
	private ListView obj;
	TextView stoIme;
	TextView konobarIme;
	Button ponisti;
	Button vratiSe;
	Info menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
     super.onCreate(savedInstanceState);
     setContentView(R.layout.menu);
     TestAdapter mDbHelper = new TestAdapter(this); //Context 
     mDbHelper.createDatabase();
     mDbHelper.open();
     

     stoIme = (TextView) findViewById(R.id.textViewStoID);
     stoIme.setText(Info.stoNaziv);// Za prosledjivanje imena stola
     
     konobarIme = (TextView) findViewById(R.id.textViewKonobarID);
     konobarIme.setText(Info.konobarID);//
     
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
				if(menu.grupaID!=-1)
				{
					Intent nextScreen = new Intent(getApplicationContext(), MenuStavkeIzGrupeActivity.class);
	                startActivity(nextScreen);
				}
				
			}
     });
     
	 ArrayList array_list = mDbHelper.getAllGrupe();
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
	            Info.grupaID=id_To_Search; 
	           
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
}
