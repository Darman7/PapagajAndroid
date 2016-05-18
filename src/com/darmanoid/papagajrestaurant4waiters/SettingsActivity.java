package com.darmanoid.papagajrestaurant4waiters;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	Info info;
	
	private EditText ipET;
	private EditText timeoutET;
	private Button ipBTN;
	private Button timeoutBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
              
        ipET = (EditText) findViewById(R.id.editIP);
        timeoutET=(EditText) findViewById(R.id.editTimeout);
		ipBTN = (Button) findViewById(R.id.buttonIP);
		timeoutBTN = (Button) findViewById(R.id.buttonTimeout);
        
		ipBTN.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				info.ip=ipET.getText().toString();
				Toast.makeText(getApplicationContext(), "Uspjesno promijenjena IP adresa servera",
						Toast.LENGTH_SHORT).show();
			}
		});
		timeoutBTN.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				info.timeout=Integer.parseInt(timeoutBTN.getText().toString());
				Toast.makeText(getApplicationContext(), "Uspjesno promijenjen timeout servera",
						Toast.LENGTH_SHORT).show();
			}
		});
	    
	    
    }

}
