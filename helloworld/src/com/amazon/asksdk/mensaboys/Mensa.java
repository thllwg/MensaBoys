
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tacke
 */
public class Mensa {
    private int id;
    private String name;
   // private ArrayList<Gericht> gerichte;
    private Speiseplan speiseplan;
    public Mensa(){
        
    }
   /* public ArrayList<Gericht> getGerichte(){
        return gerichte;
    }
    public void setGerichte(ArrayList<Gericht> gerichte){
        this.gerichte=gerichte;
    }
    */
    public Speiseplan getSpeiseplan(){
        return speiseplan;
    }
    public void setSpeiseplan(Speiseplan speiseplan){
        this.speiseplan=speiseplan;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
