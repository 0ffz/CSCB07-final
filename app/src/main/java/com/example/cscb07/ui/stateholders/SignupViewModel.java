package com.example.cscb07.ui.stateholders;

import android.util.Patterns;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cscb07.R;
import com.example.cscb07.data.SignupResult;
import com.example.cscb07.data.ServiceLocator;
import com.example.cscb07.data.SignupResult;
import com.example.cscb07.data.UserRepository;

public class SignupViewModel extends ViewModel {

    private final UserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
    private final MutableLiveData<Integer> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<>(false);

    public LiveData<Integer> getErrorMessage() {
        return errorMessage;
    }

    boolean verify(String name, String email, String password, String passwordC) {
        if (name.length() == 0) {
            errorMessage.setValue(R.string.error_name);
            return false;
        }

        if (email.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage.setValue(R.string.error_email);
            return false;
        }

        if (password.length() == 0) {
            errorMessage.setValue(R.string.error_password);
            return false;
        }

        if (!passwordC.equals(password)) {
            errorMessage.setValue(R.string.error_passwordC);
            return false;
        }

        return true;
    }

    public void signup(String name, String email, String password, String passwordC) {
        if (!verify(name, email, password, passwordC)) return;
        //TODO actually handle signup authentication
        isAuthenticated.setValue(true);
    }

    public LiveData<SignupResult> signUp(String name, String email, String password, String passwordC) {
        if (!verify(name, email, password, passwordC)) return new MutableLiveData<>(new SignupResult(false));

        //userRepository.registerUser(email, password);
        isAuthenticated.setValue(true);

        // Change this to SignupResult
        return new MutableLiveData<>(new SignupResult(true));
    }

    public boolean isAuthenticated() {
        return isAuthenticated.getValue();
    }
}
