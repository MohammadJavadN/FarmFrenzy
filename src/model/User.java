package model;

import java.util.HashMap;

public class User {
    public String username;
    private String password;
    public int gamePlayed;
    public int savedCoin;
    public int openedLevel = 1;

    public int getOpenedLevel() {
        return openedLevel;
    }

    public void setOpenedLevel(int openedLevel) {
        this.openedLevel = openedLevel;
    }

    static public User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    static User currentUser;

    public User(String username) {
        this.username = username;
        getUserHashMap().put(username, this);
    }

    public String getPassword() {
        return password;
    }


    public User(String username, String password, int gamePlayed, int savedCoin, int openedLevel) {
        this.username = username;
        this.password = password;
        this.gamePlayed = gamePlayed;
        this.savedCoin = savedCoin;
        this.openedLevel = openedLevel;
        getUserHashMap().put(username, this);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /////////////////////////////////
    // Keep users in a specific object
    private HashMap<String, User> userHashMap;

    public static HashMap<String, User> getUserHashMap() {
        return getUsers().userHashMap;
    }

    private static User users;

    private User() {
        userHashMap = new HashMap<>();
    }

    private static User getUsers() {
        if (users == null) users = new User();
        return users;
    }

    ///////////////////////////////////

    public boolean correctPass(String pass) {
        return getUserHashMap().get(username).password.equals(pass);
    }
    public static boolean correctPass(String username , String pass) {
        return getUserHashMap().get(username).password.equals(pass);
    }

    public User getUser(String username) {
        return getUserHashMap().get(username);
    }

    public boolean contain(String username) {
        // TODO: ۰۳/۰۶/۲۰۲۱
        return false;
    }
}
