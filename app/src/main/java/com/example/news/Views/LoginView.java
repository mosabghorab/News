package com.example.news.Views;

public interface LoginView {
    void showErrorMessageForUserNamePassword();

    void showErrorMessageForMaxLoginAttempt();

    void showLoginSuccessMessage();
}
