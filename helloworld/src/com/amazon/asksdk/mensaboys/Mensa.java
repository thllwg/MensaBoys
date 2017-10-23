package com.amazon.asksdk.mensaboys;
import com.amazon.asksdk.mensaboys.*;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author tacke
 */
public class Mensa {

    private String name;
    // private ArrayList<Gericht> gerichte;
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

    public Mensa(String name) {
        this.name = name;
    }

    /* public ArrayList<Gericht> getGerichte(){
         return gerichte;
     }
     public void setGerichte(ArrayList<Gericht> gerichte){
         this.gerichte=gerichte;
     }
     */
    public Speiseplan getSpeiseplan() {
        return speiseplan;
    }

    public void setSpeiseplan(Speiseplan speiseplan) {
        this.speiseplan = speiseplan;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String[] getListOfMensen() {
        return LIST_OF_MENSEN;
    }

    public static Mensa getMensaByName(String name) throws MensaNotFoundException {

        String[] mensen = getListOfMensen();

        if(Arrays.asList(mensen).contains(name)){
            return new Mensa(name);
        }
        else {
            throw new MensaNotFoundException();
        }
    }
}
