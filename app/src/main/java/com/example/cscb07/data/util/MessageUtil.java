package com.example.cscb07.data.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MessageUtil {
    private static final MutableLiveData<Message> message = new MutableLiveData<>();

    public static void showError(Throwable throwable) {
        if (throwable instanceof Message) {
            message.postValue((Message) throwable);
        } else {
            message.postValue(new Message(throwable.getLocalizedMessage()));
        }
    }

    public static void showError(int errorId) {
        message.postValue(new Message(errorId));
    }

    public static void showError(String error) {
        message.postValue(new Message(error));
    }

    public static LiveData<Message> getMessage() {
        return message;
    }
}
