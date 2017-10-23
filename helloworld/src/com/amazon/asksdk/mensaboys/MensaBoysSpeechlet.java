/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.amazon.asksdk.mensaboys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("GetSpeiseplanTag".equals(intentName)) {
            return getSpeiseplanResponse(intent);
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else {
            throw new SpeechletException("Invalid Intent");
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

        String speechText = "Tach Chef! Du kannst mich fragen, was es in der Mensa gibt!";
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
                "Du kannst mich fragen, was es an einem beliebigen Tag in einer der Mensen in Münster zu essen gibt. "
                        + " Zum Beispiel kannst Du fragen: Was gibt es heute in der Da Vinci Mensa?";

        Reprompt reprompt = new Reprompt();
        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the Speiseplan Tag intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getSpeiseplanResponse(Intent intent) {
        String speechText = "Heute gibt es Hack. Der Tag ist ";
        String day = getSlotDay(intent);
        String mensa = getSlotMensa(intent);

        speechText = speechText + " " + day;

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Mensa Boys");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        String speechText = "You can say hello to me!";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Mensa Boys");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    private String getSlotDay(Intent intent) {
        Slot daySlot = intent.getSlot(SLOT_DAY);

        if (daySlot != null && daySlot.getValue() != null) {
            return daySlot.getValue();
        } else{
            return new String("Kein Tag");
        }
    }

    private String getSlotMensa(Intent intent){
        Slot mensaSlot = intent.getSlot(SLOT_MENSA);

        if (mensaSlot != null && mensaSlot.getValue() != null) {
            return mensaSlot.getValue();
        } else{
            return new String("Keine Mensa");
        }
    }
}
