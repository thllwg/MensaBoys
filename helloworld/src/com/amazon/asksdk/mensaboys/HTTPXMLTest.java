package com.amazon.asksdk.mensaboys;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;


public class HTTPXMLTest
{
	ArrayList<String> urls = new ArrayList<String>();
    
	public HTTPXMLTest(){
		String daVinci = "http://speiseplan.stw-muenster.de/mensa_da_vinci.xml";
		String aasee = "http://speiseplan.stw-muenster.de/mensa_aasee.xml";
		String ring = "http://speiseplan.stw-muenster.de/mensa_am_ring.xml";
		String denkpause = "http://speiseplan.stw-muenster.de/bistro_denkpause.xml";
		String durchblick = "http://speiseplan.stw-muenster.de/bistro_durchblick.xml";
		
		urls.add(daVinci);
		urls.add(aasee);
		urls.add(ring);
		urls.add(denkpause);
		urls.add(durchblick);
				
		
	}
	
	public static void main(String[] args) 
    {
     HTTPXMLTest mp = new HTTPXMLTest();
     List<Mensa>mensen = mp.getAllMensen();
     for(Mensa ms:mensen){
    	 System.out.println(ms.getName());
     }
    }
	
	public List<Mensa> getAllMensen(){
		List<Mensa>mensen = new ArrayList<Mensa>();
		for (String url:this.urls){
			Mensa mensa = null;
			try {
				mensa = this.getMensaPlan(url);
			} catch (IOException | JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (mensa!=null){
				mensen.add(mensa);
			}
		}
		return mensen;
	}
	
	public Mensa getMensaPlan(String mensaUrl) throws IOException, JDOMException{
		
		 
		   URL url = new URL(mensaUrl);
	        URLConnection connection = url.openConnection();
	        SAXBuilder saxBuilder = new SAXBuilder(); 
	        Document document = saxBuilder.build(connection.getInputStream());
	        Element classElement = document.getRootElement();
	        Mensa mensa = new Mensa();
	        mensa.setName(classElement.getAttributeValue("location"));
	      //returns a list of all child nodes 
	       List<Element>speiseplaene =  classElement.getChildren("date");
	       for(Element eplan:speiseplaene){
	    	   Speiseplan plan = new Speiseplan();
	    	  
	    	   plan.setName("Speiseplan");
	    //	   plan.setDatum(new Date(eplan.getAttribute("timestamp").getLongValue()));
	    	   List<Element>ngerichte = eplan.getChildren("item"); //items
	    	   for(Element egericht:ngerichte){
	    		   Gericht gericht = new Gericht();
	   
	    		   try{
					   gericht.setName(egericht.getChildText("meal"));
				   } catch (Exception e){
					   
				   }
	    		   try{
	    			   gericht.setKategorie(egericht.getChildText("category"));
				   } catch (Exception e){
					   
				   }
	    		   try{
	    			   gericht.setfoodicon(egericht.getChildText("foodicon"));
				   } catch (Exception e){
					   
				   }
	    		   try{
	    			   gericht.setPrice1(Float.parseFloat(egericht.getChildText("price1")));
				   } catch (Exception e){
					   
				   }
	    		   try{
	    			   gericht.setPrice2(Float.parseFloat(egericht.getChildText("price2")));
				   } catch (Exception e){
					   
				   }
	    		   try{
	    			   gericht.setPrice3(Float.parseFloat(egericht.getChildText("price3")));
	    					   } catch (Exception e){
	    						   
	    					   }	
	    			
	    		   

	    		   plan.getGerichte().add(gericht);
	    	   }
	    	   mensa.getSpeiseplan().add(plan);
	       }
	      return mensa;
	}
      
} 