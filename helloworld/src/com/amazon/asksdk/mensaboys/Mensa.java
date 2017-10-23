package com.amazon.asksdk.mensaboys;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author tacke
 */
public class Mensa {
    private String id;
    private String name;
    private Speiseplan speiseplan;

    /**
     * Constant defining the potential options for the intent slot mensa
     */
    private static final String[] LIST_OF_MENSEN = {
            "Bistro Denkpause",
            "Bistro Durchblick",
            "Bistro Frieden",
            "Bistro Hüfferstiftung",
            "Bistro KaBu",
            "Bistro Katholische Hochschule",
            "Bistro Oeconomicum",
            "Mensa Da Vinci",
            "Mensa Steinfurt",
            "Mensa am Aasee",
            "Mensa am Ring"
    };

    public Mensa() {

    }
     public Speiseplan getSpeiseplan() {
        return speiseplan;
    }

    public void setSpeiseplan(Speiseplan speiseplan) {
        this.speiseplan = speiseplan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
                this.name=name;
    }
    
        
    

    public static String[] getListOfMensen() {
        return LIST_OF_MENSEN;
    }
}
