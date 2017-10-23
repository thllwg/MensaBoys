/**
 * Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at
 * <p>
 * http://aws.amazon.com/apache2.0/
 * <p>
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.amazon.asksdk.mensaboys;

import com.amazon.asksdk.mensaboys.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;


/**
 * This sample shows how to create a simple speechlet for handling speechlet requests.
 */
public class MensaBoysSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(MensaBoysSpeechlet.class);

    /**
     * Constant defining session attribute key for the intent slot key for the date of menu.
     */
    private static final String SLOT_DAY = "tag";

    /**
     * Constant defining session attribute key for the intent slot key for the date of menu.
     */
    private static final String SLOT_MENSA = "mensa";


    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));

        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        try{
            if ("GetSpeiseplanTag".equals(intentName)) {
                return getSpeiseplanResponse(intent);
            } else if ("AMAZON.HelpIntent".equals(intentName)) {
                return getHelpResponse();
            } else {
                throw new SpeechletException("Invalid Intent");
            }
        } catch (Exception e){
            PlainTextOutputSpeech exceptionSpeech = new PlainTextOutputSpeech();
            exceptionSpeech.setText("Eine Schande, " + e.getMessage());
            return SpeechletResponse.newTellResponse(exceptionSpeech);
        }

    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any cleanup logic goes here
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {

        String speechText = "Mahlzeit! Du kannst die Mensa Boys fragen, was es zu Essen gibt!";
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // If the user either does not reply to the welcome message or says something that is not
        // understood, they will be prompted again with this text.
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Mensa Boys");
        card.setContent(speechText);

        // Create reprompt
        String repromptText =
                "Du kannst die Mensa Boys fragen, was es an einem beliebigen Tag in einer der Mensen in Münster "
                        + " zu essen gibt. Zum Beispiel kannst Du fragen: Was gibt es heute in der Mensa Da Vinci?";

        Reprompt reprompt = new Reprompt();
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a help message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {

        // Create reprompt
        String helpText =
                "Du kannst die Mensa Boys fragen, was es an einem beliebigen Tag in einer der Mensen in Münster "
                        + " zu essen gibt. Zum Beispiel kannst Du fragen: Was gibt es heute in der Mensa Da Vinci?";
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(helpText);

        // If the user either does not reply to the welcome message or says something that is not
        // understood, they will be prompted again with this text.
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Mensa Boys");
        card.setContent(helpText);


        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the Speiseplan Tag intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getSpeiseplanResponse(Intent intent) throws SpeechletException {

        Date day = getSlotDay(intent);
        Mensa mensa = getSlotMensa(intent);
        String speechText = mensa.getName() + " bietet " + Utils.getDayAptonym(day) + " folgendes Angebot: ";

        HTTPXMLTest xmlparser = new HTTPXMLTest();
        List<Mensa> mensen = xmlparser.getAllMensen();

        for(Mensa mensar:mensen){
            if(mensa.getName().equalsIgnoreCase(mensar.getName())){
                mensa = mensar;
            }
        }

        try{
            Speiseplan speiseplan = mensa.getSpeiseplan(day);
            StringBuilder sb = new StringBuilder();
            for(Gericht gericht:speiseplan.getGerichte()){
                sb.append(gericht.getName()+", ");
            }
            speechText = speechText + sb.toString();

        } catch (SpeiseplanException e){
            throw new SpeechletException("Kein Speiseplan für " + Utils.getDayAptonym(day) + " gefunden.");
        }

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Mensa Boys");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

//    public static void main(String[] args) throws Exception
//    {
//        Date day = new Date();
//        Mensa mensa = Mensa.getMensaByName("Mensa da Vinci");
//        String speechText = mensa.getName() + " bietet " + Utils.getDayAptonym(day) + " folgendes Angebot: ";
//
//        HTTPXMLTest xmlparser = new HTTPXMLTest();
//        List<Mensa> mensen = xmlparser.getAllMensen();
//
//        for(Mensa mensar:mensen){
//            if(mensa.getName().equalsIgnoreCase(mensar.getName())){
//                mensa = mensar;
//            }
//        }
//
//        try{
//            Speiseplan speiseplan = mensa.getSpeiseplan(day);
//            StringBuilder sb = new StringBuilder();
//            for(Gericht gericht:speiseplan.getGerichte()){
//                sb.append(gericht.getName()+", ");
//            }
//            speechText = speechText + sb.toString();
//
//        } catch (Exception e){
//            throw new SpeechletException("Kein Speiseplan für " + Utils.getDayAptonym(day) + " gefunden.");
//        }
//
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
//        Date datex = dateFormat.parse("2017-10-23"); // 2017-10-23
//
//        System.out.println(datex);
//
//        System.out.println(speechText);
//
//    }
    /**
     * Extracts the queried date from the Alexa intent
     *
     * @param intent Alexa intent
     * @return Date
     * @throws SpeechletException if a date in the past was given
     */
    private Date getSlotDay(Intent intent) throws SpeechletException {
        Slot daySlot = intent.getSlot(SLOT_DAY);
        Date date;

        if (daySlot != null && daySlot.getValue() != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
            try {
                date = dateFormat.parse(daySlot.getValue()); // 2017-10-23

                Calendar todate = new GregorianCalendar();
                Calendar slotday = new GregorianCalendar();
                slotday.setTime(date);
                // reset hour, minutes, seconds and millis
                todate.set(Calendar.HOUR_OF_DAY, 0);
                todate.set(Calendar.MINUTE, 0);
                todate.set(Calendar.SECOND, 0);
                todate.set(Calendar.MILLISECOND, 0);

                if(slotday.before(todate)){
                    throw new SpeechletException("Das gewünschte Datum liegt in der Vergangenheit");
                }

            } catch (ParseException e) {
                date = new Date();
            }
        } else {
            date = new Date();
        }
        return date;

    }

    /**
     * Extracts the queried Mensa from the Alexa intent
     *
     * @param intent Alexa intent
     * @return Mensa
     * @throws SpeechletException if the mensa was not found
     */
    private Mensa getSlotMensa(Intent intent) throws SpeechletException {

        Slot mensaSlot = intent.getSlot(SLOT_MENSA);
        Mensa mensa;

        if (mensaSlot != null && mensaSlot.getValue() != null) {
            try {
                mensa = Mensa.getMensaByName(mensaSlot.getValue());
            } catch (MensaNotFoundException e) {
                throw new SpeechletException("Mensa " + mensaSlot.getValue() + " nicht gefunden");
            }
        } else {
            try {
                mensa = Mensa.getMensaByName("Mensa Da Vinci"); //Default
            } catch (MensaNotFoundException e) {
                throw new SpeechletException("Default Mensa nicht gefunden");
            }
        }
        return mensa;
    }
}
