package com.darmanoid.papagajrestaurant4waiters;



import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

public class RegioniActivity extends Activity{
	
	/*
	 *  Stolovi se prikazuju u dva taba
	 */
	Info info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stolovi_frame);
		
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); //NAVIGATION_MODE_TABS
        // 
        actionBar.setDisplayShowHomeEnabled(false);
 
        // Da mi ne prikazuje ime aplikacije
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        //Dinamicki po DUjovim instrukcijama :) 
        //Prvo procitati imena iz baze
        
        TestAdapter mDbHelper = new TestAdapter(this); //Context 
        mDbHelper.createDatabase();
        mDbHelper.open();
        
        Cursor regioni = mDbHelper.getRegioni();
        
        Info konobar=new Info();
        int ukupno = regioni.getCount();
        for(int i=1; i<=ukupno; i++)
        {
        	ActionBar.Tab noviTab;
        	noviTab=actionBar.newTab().setText(regioni.getString(1));
        	
        	Fragment noviFragment = new FragmentTab(regioni.getString(0)); 
        	noviTab.setTabListener(new TabListener(noviFragment));
        	actionBar.addTab(noviTab);
       		
       	regioni.moveToNext();
        }
	}
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

	    builder.setTitle("Logout");
	    builder.setMessage("Da li zelite da se izlogujete?");

	    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {

	        @Override
			public void onClick(DialogInterface dialog, int which) {
	        	Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
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
