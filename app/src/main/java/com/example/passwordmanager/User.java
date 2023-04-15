package com.example.passwordmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private Object username;
    private Object password;
    public List<Website> websites = new ArrayList<>();

    public User(Object username, Object password, List<String> websites) {
        this.username = username;
        this.password = password;
        for(String website: websites) {
            Website newSite = new Website(websites);
            this.websites.add(newSite);
        }
    }

    public String getUsername() {
        return username.toString();
    }

    public String getPassword() {
        return password.toString();
    }
    public List<String> getWebsiteList() {
        List<String> websiteList = new ArrayList<>();
        for(int i = 0; i < websiteList.size();) {
            websiteList.add(websites.get(i).toString());
            i+=3;
        }
        return websiteList;
    }
}
