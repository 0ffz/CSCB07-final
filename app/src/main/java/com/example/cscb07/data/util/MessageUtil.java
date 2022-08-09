package com.example.cscb07.data.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MessageUtil {
    private static final MutableLiveData<Message> message = new MutableLiveData<>();

    public static void showMessage(Throwable throwable) {
        if (throwable instanceof Message) {
            message.postValue((Message) throwable);
        } else {
            message.postValue(new Message(throwable.getLocalizedMessage()));
        }
        throwable.printStackTrace();
    }

    public static void showMessage(int errorId) {
        message.postValue(new Message(errorId));
    }

    public static void showMessage(String error) {
        message.postValue(new Message(error));
    }

    public static LiveData<Message> getMessage() {
        return message;
    }
}
