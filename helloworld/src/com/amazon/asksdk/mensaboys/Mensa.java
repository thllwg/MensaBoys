package com.amazon.asksdk.mensaboys;

import com.amazon.asksdk.mensaboys.MensaNotFoundException;
import com.amazon.asksdk.mensaboys.SpeiseplanException;
import com.amazon.asksdk.mensaboys.Utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;


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
    private ArrayList<Speiseplan> speiseplan = new ArrayList<Speiseplan>();

    /**
     * Constant defining the potential options for the intent slot mensa
     */
    private static final String[] LIST_OF_MENSEN = {
            "Bistro Denkpause",
            "Bistro Durchblick",
            "Bistro Frieden",
            "Bistro Hüfferstift",
            "Bistro KaBu",
            "Bistro KatHo", // Katholische Hochschule
            "Bistro Oeconomicum",
            "Mensa da Vinci",
            "Mensa Steinfurt",
            "Mensa am Aasee",
            "Mensa am Ring"
    };

    private static final int THRESHOLD = 4;

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

    public Speiseplan getSpeiseplan(Date datum) throws SpeiseplanException {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

        for(Speiseplan s:speiseplan){
            if(fmt.format(s.getDatum()).equals(fmt.format(datum))){
                return s;
            }
        } 
        throw new SpeiseplanException("Mensa ist geschlossen!");
    }

    public ArrayList<Speiseplan> getSpeiseplaene() {
        return speiseplan;
    }

    public void setSpeiseplan(Speiseplan speiseplan) {
        this.speiseplan.add(speiseplan);
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

        // Iteriere durch alle Mensen
        // Bilde bei jedem die Levensthein Distanz
        // Behalte Mensa, falls Distanz die bisher kleinste ist
        // gebe Mensa zurück, wenn Distanz <= theshold
        // sonst gebe MensaNotFoundException zurück

        String candidate = "";
        int distance = 999;
        int temp_distance = 0;

        for(String mensa:Arrays.asList(mensen)){

            String tempnam = name.toLowerCase().replaceAll("Mensa", "").trim();
            String tempmensa = mensa.toLowerCase().replaceAll("Mensa", "").trim();

            temp_distance = Utils.levenshteinDistance(tempnam, tempmensa);

            if(temp_distance < distance){
                candidate = mensa;
                distance = temp_distance;
            }
        }

        if (distance <= THRESHOLD) {
            return new Mensa(candidate);
        } else {
            throw new MensaNotFoundException();
        }
    }
}
