package com.example.news.Presenters;

import com.example.news.Models.Profile.ProfilePageInterface;
import com.example.news.Models.Profile.ProfilePagePresenterInterface;

public class ProfilePagePresenter implements ProfilePagePresenterInterface {
    ProfilePageInterface profilePageInterface;

    public ProfilePagePresenter(ProfilePageInterface profilePageInterface) {
        this.profilePageInterface = profilePageInterface;
    }


    @Override
    public void validateData(String email, String password,String name) {
        if (!email.isEmpty() && !password.isEmpty()&& !name.isEmpty()) {
            if(password.length() >= 8 && email.contains("@") && email.contains(".com")){
                profilePageInterface.sendUpdateRequestToServer();
            }else{
                profilePageInterface.stopProcessAndShowError();
            }
        } else {
            profilePageInterface.stopProcessAndShowError();
        }

    }
}
