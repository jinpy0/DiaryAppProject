package Diary.DataBase.service;

import Diary.DataBase.Dto.UserDTO;

public class UserSession {
    private static UserSession instance;
    private UserDTO currentUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCurrentUser(UserDTO user) {
        this.currentUser = user;
    }

    public UserDTO getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        currentUser = null;
    }
}
