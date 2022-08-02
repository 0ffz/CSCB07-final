package com.example.cscb07.data;

import com.example.cscb07.data.repositories.impl.FirebaseUserRepository;
import com.example.cscb07.data.repositories.UserRepository;

/**
 * The service locator is a central class for letting the UI access implementations of repositories.
 * <p>
 * Example use {@code ServiceLocator.getInstance().getSomeRepository() }
 */
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
