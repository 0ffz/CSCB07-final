package com.example.cscb07.data;

import com.example.cscb07.data.impl.FirebaseUserRepository;

public class ServiceLocator {

    private static ServiceLocator instance = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized(ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public UserRepository getUserRepository() {
        return new FirebaseUserRepository();
    }
}
