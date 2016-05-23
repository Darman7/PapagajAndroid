package com.darmanoid.papagajrestaurant4waiters;

import android.graphics.Bitmap;

public class GrupeListModel {
	private  String naziv;
    private  String id; 
    private  Bitmap image;
    
	public GrupeListModel(String naziv,String id, Bitmap image) {
		super();
		this.naziv = naziv;

		this.id = id;
		this.image = image;
	}
	 
	public String getNaziv() {
		return naziv; 
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
    
    
}
