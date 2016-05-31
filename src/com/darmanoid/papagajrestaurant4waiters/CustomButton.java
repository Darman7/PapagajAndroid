package com.darmanoid.papagajrestaurant4waiters;

import android.content.Context;
import android.widget.Button;

public class CustomButton extends Button{
	private String imeKonobara;
	private String iznos;
	private String naziv;
	public CustomButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub		
	}
	public String getImeKonobara() {
		return imeKonobara;
	}
	public void setImeKonobara(String imeKonobara) {
		this.imeKonobara = imeKonobara;
	}
	public String getIznos() {
		return iznos;
	}
	public void setIznos(String iznos) {
		this.iznos = iznos;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	
}
