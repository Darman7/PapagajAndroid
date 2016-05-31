package com.darmanoid.papagajrestaurant4waiters;

public class PorudzbaListModel {
	private String kolicina;
	private String naziv;
	private String cijena;
	public PorudzbaListModel(String kolicina, String naziv, String cijena) {
		super();
		this.kolicina = kolicina;
		this.naziv = naziv;
		this.cijena = cijena;
	}
	public String getKolicina() {
		return kolicina;
	}
	public void setKolicina(String kolicina) {
		this.kolicina = kolicina;
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
	
}
