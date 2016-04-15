package com.darmanoid.papagajrestaurant4waiters;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;


public class TabListener implements ActionBar.TabListener  {
	 
		private Fragment fragment;
		/*
		 * Uvijek se mora doraditi po potrebi tab listener
		 */
		
		public TabListener(Fragment fragment) {
			this.fragment = fragment;
		}
		
			// Transakcija framenata kada tapnemo na neki tab
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				ft.replace(R.id.stolovi_frame, fragment);
				
			}

			// Kada unselektujemo tab, moramo ga sakriti 
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				ft.remove(fragment);
			}

			// Neka je za sad prazna
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				
			}
		
	}
