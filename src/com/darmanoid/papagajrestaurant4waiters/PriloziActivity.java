package com.darmanoid.papagajrestaurant4waiters;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.prilozi);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
   
        setupVariables();   
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
