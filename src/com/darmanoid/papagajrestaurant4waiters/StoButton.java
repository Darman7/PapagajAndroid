package com.darmanoid.papagajrestaurant4waiters;

import android.content.Context;
import android.widget.Button;

public class StoButton extends Button{
	private String imeKonobara;
	private String iznos;
	private String naziv;
	private String id_stola;
	public StoButton(Context context,String id_stola) {
		super(context);
		// TODO Auto-generated constructor stub
		this.id_stola=id_stola;
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
	public String getIdStola()
	{
		return id_stola;
	}
	public void setIdStola(String id)
	{
		this.id_stola=id;
	}
	
}
