package com.example.cscb07.ui.stateholders;

import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.R;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.data.results.LoginResult;
import com.example.cscb07.data.util.ServiceLocator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final UserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
    //TODO have a separate class for handling messages
    private final MutableLiveData<Integer> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessageString = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>(mAuth.getCurrentUser());

    boolean verify(String email, String password) {
        if (email.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.setValue(R.string.error_email);
            return false;
        }

        if (password.length() == 0) {
            errorMessage.setValue(R.string.error_password);
            return false;
        }

        return true;
    }

    private void handleLogin(LoginResult result) {
        if (result.isSuccessful()) user.postValue(result.user);
        if (result.message != null) errorMessageString.postValue(result.message);
    }

    public void login(String email, String password) {
        if (!verify(email, password)) return;
        userRepository.signIn(email, password, this::handleLogin);
    }

    public void signUp(String email, String password) {
        if (!verify(email, password)) return;
        userRepository.registerUser(email, password, this::handleLogin);
    }

    public LiveData<Integer> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<String> getErrorMessageString() {
        return errorMessageString;
    }

    public final LiveData<FirebaseUser> getUser() {
        return user;
    }
}
