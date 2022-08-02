package com.example.cscb07.ui.stateholders;

import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.R;
import com.example.cscb07.data.ServiceLocator;
import com.example.cscb07.data.repositories.UserRepository;
import com.example.cscb07.ui.state.UserUiState;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
    private final MutableLiveData<Integer> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<UserUiState> user = new MutableLiveData<>(null);

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

    public void login(String email, String password) {
        if (!verify(email, password)) return;
        userRepository.signIn(email, password, result -> {
            if (result.isSuccessful())
                user.postValue(result.user);
            else if (result.message != null) errorMessage.postValue(result.message);
        });
    }

    public void signUp(String email, String password) {
        if (!verify(email, password)) return;
        userRepository.registerUser(email, password, result -> {
            if (result.success) login(email, password);
            else if (result.message != null) errorMessage.postValue(result.message);
        });
    }

    public LiveData<Integer> getErrorMessage() {
        return errorMessage;
    }

    public final LiveData<UserUiState> getUser() {
        return user;
    }
}
