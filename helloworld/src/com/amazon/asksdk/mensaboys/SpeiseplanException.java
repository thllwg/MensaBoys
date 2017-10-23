package com.amazon.asksdk.mensaboys;

public class SpeiseplanException extends Exception
{
    // Parameterless Constructor
    public SpeiseplanException() {}

    // Constructor that accepts a message
    public SpeiseplanException(String message)
    {
        super(message);
    }
}