package com.amazon.asksdk.mensaboys;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Speiseplan {
	
	private int id;
	
	private String name;
	
	private Date datum;
	
	private ArrayList<Gericht> gerichte;
	
	public Speiseplan(){}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id =id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name =name;
	}
	
	public Calendar getDatum(){
		return datum;
	}
	
	public void setDatum(Date datum){
		this.datum = datum;
	}
	
	public ArrayList<Gericht> getGerichte(){
        return gerichte;
    }
    public void setGerichte(ArrayList<Gericht> gerichte){
        this.gerichte=gerichte;
    }

}