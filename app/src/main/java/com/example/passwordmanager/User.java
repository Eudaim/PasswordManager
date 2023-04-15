package com.example.passwordmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private Object username;
    private Object password;
    public Object websites;

    public User(Object username, Object password, Object websites) {
        this.username = username;
        this.password = password;
        this.websites = websites;
    }
}
