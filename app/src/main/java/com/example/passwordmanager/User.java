package com.example.passwordmanager;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Parcelable {
    private String username;
    private String password;
    public List<String> websites = new ArrayList<>();

    public User(String username, String password, List<String> websites) {
        this.username = username;
        this.password = password;
        for(String website: websites) {
            this.websites.add(website);
        }
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        in.readList(websites, Website.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getWebsiteList() {
        List<String> websiteList = new ArrayList<>();
        for(int i = 0; i < websiteList.size();) {
            websiteList.add(websites.get(i));
            i+=3;
        }
        return websiteList;
    }

    public List<String> getUserNameAndPassword(String website) {
        int index = websites.indexOf(website);
        List<String> usernameAndPassword = new ArrayList<>();
        if(websites.contains(website)) {
            websites.get(index);
            usernameAndPassword.add(websites.get(index+1).toString());
            usernameAndPassword.add(websites.get(index+2).toString());
        }
        return usernameAndPassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeList(websites);
    }
}
