package com.example.passwordmanager;

import java.util.List;

public class Website {
    public String name;
    public String username;
    public String password;

    public Website(List<String> websites) {
        for(int i = 0; i < websites.size();) {
            name = websites.get(i);
            username = websites.get(i+1);
            password = websites.get(i+2);
            i+=3;
        }
    }
}
