package com.example.cscb07.ui.stateholders;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cscb07.R;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.util.MessageUtil;
import com.example.cscb07.data.util.ServiceLocator;
import com.example.cscb07.ui.state.UserUiState;
import com.example.cscb07.ui.stateholders.firebase.FirebaseUserLiveData;
import com.google.firebase.auth.FirebaseUser;

import io.vavr.control.Try;

public class AuthViewModel extends ViewModel {
    private final UserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
    private final FirebaseUserLiveData user = new FirebaseUserLiveData();

    private final MutableLiveData<Boolean> attemptingLogin = new MutableLiveData<>();

    private void handleAuthResult(Try<FirebaseUser> result) {
        attemptingLogin.setValue(false);
        result.onFailure(MessageUtil::showMessage);
    }

    public void login(String email, String password,
                      InputValidator inputValidator) {
        if (!inputValidator.isValid()) return;
        if (!isEmailValid(email)) {
            MessageUtil.showMessage(R.string.error_email);
            return;
        }
        attemptingLogin.setValue(true);
        userRepository.signIn(email, password, this::handleAuthResult);
    }

    public void signUp(String email, String password, String passwordRetype,
                       InputValidator inputValidator) {
        if (!inputValidator.isValid()) return;
        if (!isEmailValid(email)) {
            MessageUtil.showMessage(R.string.error_email);
            return;
        }
        if (!isRetypeValid(password, passwordRetype)) {
            MessageUtil.showMessage(R.string.error_passwords_dont_match);
            return;
        }
        attemptingLogin.setValue(true);
        userRepository.registerUser(email, password, this::handleAuthResult);
    }

    public void signOut() {
        userRepository.signOutCurrentUser();
    }

    public final LiveData<UserUiState> getUser() {
        return user;
    }

    public LiveData<Boolean> isAttemptingLogin() {
        return attemptingLogin;
    }

    public boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isRetypeValid(String password, String passwordRetype) {
        return password.equals(passwordRetype);
    }
}
