package com.darmanoid.papagajrestaurant4waiters;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Info {
	/*
	 * Staticka klasa za podesavanje IP-a i prosledjivanje podataka prilikom smjenjivanja activity-a
	 * 
	 */
	
	//Ovdje ubaciti prvatni IP servera
	public static String ip="192.168.1.77";
	public static int timeout=3000; 
	   
	public static String konobarKartica;
	public static String konobarIme;
	public static String konobarID;
	
	public static String regionIDkonabara;
	
	public static String grupaIDstr;
	public static String grupaNaziv;
	
	public static String stoID;
	public static String stoNaziv;
	public static int grupaID=-1;  
	
	public static String jeloID;
	public static String nazivJela;
	public static String cijena;
	public static String kolicina;
	public static String ukupanIznos="0.0 €";
	
	public static ArrayList<String> poruceno=new ArrayList();
	public void ocisti()
	{
		this.jeloID=null;
		this.nazivJela=null;
		this.cijena=null; 
		this.stoNaziv=null;
		this.ukupanIznos="0.0 €";
	}
	public static void cijena(String c)
	{
		int d=ukupanIznos.length();
		ukupanIznos=ukupanIznos.substring(0, d-1);
		Float stara=Float.parseFloat(ukupanIznos);
		Float z=Float.parseFloat(c); //Nova
		stara=stara+z;
		DecimalFormat df = new DecimalFormat("0.00");
		df.setMaximumFractionDigits(2);
		ukupanIznos=df.format(stara)+" €";
	}
	public Info(){
		super();
	}
}
