/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tacke
 */
public class Gericht {
    private int id;
    private String name;
    private float price1,price2,price3;
    private String foodicon;
    private String kategorie; 
    public Gericht(){
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
    public float getPrice1(){
        return price1;
    }
    public void setPrice1(float price1){
        this.price1=price1;
    }
     public float getPrice2(){
        return price2;
    }
    public void setPrice2(float price2){
        this.price2=price2;
    }
     public float getPrice3(){
        return price3;
    }
    public void setPrice3(float price3){
        this.price3=price3;
    }
    public String getfoodicon(){
        return foodicon;
    }
    public void setfoodicon(String foodicon){
        this.foodicon=foodicon;
    }
    public String getKategorie(){
        return kategorie;
    }
    public void setKategorie(String kategorie){
        this.kategorie=kategorie;
    }
}
