package com.example.cscb07.ui.stateholders;

import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.R;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.vavr.control.Try;

public class AuthViewModel extends ViewModel {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final UserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
    //TODO have a separate class for handling messages
    private final FirebaseUserLiveData user = new FirebaseUserLiveData();

    private final MutableLiveData<Boolean> attemptingLogin = new MutableLiveData<>();

    boolean verify(String email, String password) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            MessageUtil.showError(R.string.error_email);
            return false;
        }

        if (password.isEmpty()) {
            MessageUtil.showError(R.string.error_password);
            return false;
        }

        return true;
    }

    private void handleAuthResult(Try<FirebaseUser> result) {
        attemptingLogin.setValue(false);
        result.onFailure(MessageUtil::showError);
    }

    public void login(String email, String password) {
        if (!verify(email, password)) return;
        attemptingLogin.setValue(true);
        userRepository.signIn(email, password, this::handleAuthResult);
    }

    public void signUp(String email, String password, String passwordRetype) {
        //TODO do we want name?
        if (!verify(email, password)) return;
        if(!password.equals(passwordRetype)) MessageUtil.showError(R.string.error_passwords_dont_match);
        attemptingLogin.setValue(true);
        userRepository.registerUser(email, password, this::handleAuthResult);
    }

    public void signOut() {
        userRepository.signOutCurrentUser();
    }

    public final LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<Boolean> isAttemptingLogin() {
        return attemptingLogin;
    }
}
