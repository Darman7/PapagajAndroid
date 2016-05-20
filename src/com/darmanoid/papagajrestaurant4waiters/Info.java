package com.darmanoid.papagajrestaurant4waiters;

import java.util.ArrayList;

public class Info {
	/*
	 * Staticka klasa za podesavanje IP-a i prosledjivanje podataka prilikom smjenjivanja activity-a
	 * 
	 */
	
	//Ovdje ubaciti prvatni IP servera
	public static String ip="192.168.1.65";
	public static int timeout=5000; 
	   
	public static String konobarKartica;
	public static String konobarIme;
	public static String regionIDkonabara;
	
	public static String stoNaziv;
	
	public static String grupaIDstr;
	
	public static int stoID;
	public static int grupaID=-1;
	
	public static String jeloID;
	public static String nazivJela;
	public static String cijena;
	
	
	public static ArrayList<String> poruceno=new ArrayList();
	public void ocisti()
	{
		this.jeloID=null;
		this.nazivJela=null;
		this.cijena=null;
		this.stoNaziv=null;
		
	}
	
	public Info(){
		super();
	}
}
