package com.darmanoid.papagajrestaurant4waiters;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
	
	public FragmentTab(String regionID) {
		super();
		this.regionID = regionID;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
		View rootView = inflater.inflate(R.layout.stotest, container, false);
		TableLayout table=(TableLayout) rootView.findViewById(R.id.tableForButtons);
		konobarIme = (TextView) rootView.findViewById(R.id.textViewKONOBAR);
		konobarIme.setText("Konobar: "+Info.konobarID);
		
		//brza = (Button) findButtonById(R.id.buttonBrzaPorudzba);
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
        
		TestAdapter mDbHelper = new TestAdapter(getActivity()); //Context 
        mDbHelper.createDatabase();
        mDbHelper.open();
        
        Cursor stolovi = mDbHelper.getStoloviPoRegionima(this.regionID); //1 je unutra
        brojStolova=stolovi.getCount();
        NUM_ROWS=(brojStolova/NUM_COLS) + 1 ;
        
		for(int row=0; row<NUM_ROWS; row++)
		{
			TableRow tableRow=new TableRow(getActivity());
			table.addView(tableRow); 
			for(int col=0; col<NUM_COLS && brojStolova>0; col++)
			{
				final Button button=new Button(getActivity());
				button.setMinWidth(200);
				button.setMinHeight(160);
				tableRow.addView(button);
				brojStolova--;
				/*
				 * Dodajem svojstva dugmadima
				 */
				button.setText(stolovi.getString(1));
				button.setId(id);
				id++;
				button.setLayoutParams(new TableRow.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f)); 
				
				button.setOnClickListener(new View.OnClickListener() {
					
					

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Info.stoNaziv=(String) button.getText();
						//info.stoID=1;
						Intent intent = new Intent();
						intent.setClass(getActivity(), MenuGrupeActivity.class);
						getActivity().startActivity(intent);
					}
				});
				stolovi.moveToNext();
			}
			
		}
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
