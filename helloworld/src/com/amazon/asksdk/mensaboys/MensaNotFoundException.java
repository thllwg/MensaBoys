package com.amazon.asksdk.mensaboys;

public class MensaNotFoundException extends Exception
    {
        // Parameterless Constructor
        public MensaNotFoundException() {}

        // Constructor that accepts a message
        public MensaNotFoundException(String message)
        {
            super(message);
        }
    }