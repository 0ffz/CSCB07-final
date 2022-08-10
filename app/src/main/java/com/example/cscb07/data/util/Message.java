package com.example.cscb07.data.util;

public class Message extends Throwable {
    public final String message;
    public final int messageId;

    public Message(String message) {
        this.message = message;
        this.messageId = -1;
    }

    public Message(int messageId) {
        this.message = null;
        this.messageId = messageId;
    }

    public Message(String message, Throwable cause) {
        super(cause);
        this.message = message;
        this.messageId = -1;
    }

    public Message(int messageId, Throwable cause) {
        super(cause);
        this.message = null;
        this.messageId = messageId;
    }
}
