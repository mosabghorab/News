package com.example.news.Presenters;

import com.example.news.Models.Login.LogInTestInterface;
import com.example.news.Models.Login.LogInTestPresenterInterface;

public class LogInActivityPresenter implements LogInTestPresenterInterface {
    LogInTestInterface logInTestInterface;

    public LogInActivityPresenter(LogInTestInterface logInTestInterface) {
        this.logInTestInterface = logInTestInterface;
    }


    @Override
    public void checkFromData(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            logInTestInterface.sendRequestLogin();
        } else {
            logInTestInterface.showErrorMessage();
        }

    }
}
