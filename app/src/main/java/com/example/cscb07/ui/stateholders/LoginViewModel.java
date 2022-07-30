package com.example.cscb07.ui.stateholders;

import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.R;
import com.example.cscb07.data.ServiceLocator;
import com.example.cscb07.data.UserRepository;

public class LoginViewModel extends ViewModel {
    //TODO not sure if we are supposed to assign right here or not, but /shrug
    private final UserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
    private final MutableLiveData<Integer> errorMessage = new MutableLiveData<>();

    LiveData<Integer> getErrorMessage() {
        return errorMessage;
    }

    boolean verify(String email, String password) {
        if (email.length() == 0 || Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
        //TODO login
    }

    public void signUp(String email, String password) {
        if (!verify(email, password)) return;
        userRepository.registerUser(email, password);
        //TODO switch screens
    }
}
