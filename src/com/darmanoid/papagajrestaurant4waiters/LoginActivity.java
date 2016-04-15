package com.darmanoid.papagajrestaurant4waiters;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

 
public class LoginActivity extends Activity {
	
	private EditText username;
	private Button login;
	ActionBar.Tab bastaTab,unutrasnjostTab;
	
	Info konobar;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupVariables(); //ID textfield-a i login buttona
        
    }

    public void authenticateLogin(View view) {
		view = this.getCurrentFocus();
		if (view != null) {  
		    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		if (autentifikacija()==true)
		{				
			Intent nextScreen = new Intent(getApplicationContext(), RegioniActivity.class);
		    startActivity(nextScreen);	
		               
		} else {
			Toast.makeText(getApplicationContext(), "Nepostojeci korisnik!", Toast.LENGTH_SHORT).show();
		}
	}
    
    /*
     * Cita iz textfida i pronalazi ID dugmete za login
     */
    private void setupVariables() {

        username = (EditText) findViewById(R.id.usernameET);
		login = (Button) findViewById(R.id.loginBtn);
    }
    
    
    private boolean autentifikacija()
    {
    	boolean postoji=false;
    	 TestAdapter mDbHelper = new TestAdapter(getBaseContext()); //Context 
         mDbHelper.createDatabase();
         mDbHelper.open();
         
         Cursor korisnici = mDbHelper.getImenaKorisnika();
       
         Info konobar=new Info();
         int ukupno = korisnici.getCount();
         for(int i=1; i<=ukupno; i++)
         {
        	
        	
        	if(username.getText().toString().equals(korisnici.getString(2)))
        	{
        		postoji=true;
        		Info.konobarID=korisnici.getString(4);
        		Toast.makeText(getApplicationContext(), "Ulogovani ste kao:"+korisnici.getString(1), Toast.LENGTH_LONG).show();
        		break;
        	}
        	korisnici.moveToNext();
         }
    	return postoji;
    }
    @Override
	public void onBackPressed() {
		//Da ne moze da se vrati nigdje :) 
	}
}
