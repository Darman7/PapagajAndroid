package com.darmanoid.papagajrestaurant4waiters;

//Klasa koja definise objekat nad kojim radi ArrayAdapter
public class ArtikliListModel {
	private  String naziv;
    private  String cijena;
    private  String id; 

	public ArtikliListModel(String naziv, String cijena, String id) {
		super();
		this.naziv = naziv;
		this.cijena = cijena;
		this.id=id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getCijena() {
		return cijena;
	}

	public void setCijena(String cijena) {
		this.cijena = cijena;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
     
    
}
