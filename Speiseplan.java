import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Speiseplan {
	
	private int id;
	
	private String name;
	
	private Calendar datum;
	
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
	
	public void setDatum(int jahr, int monat, int tag){
		this.datum = new GregorianCalendar(jahr, monat, tag);
	}
	
	public ArrayList<Gericht> getGerichte(){
        return gerichte;
    }
    public void setGerichte(ArrayList<Gericht> gerichte){
        this.gerichte=gerichte;
    }

}
