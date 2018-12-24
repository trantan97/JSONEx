package com.trantan.jsonex.model;

public class Repository {
    private String mName;
    private String mLanguage;
    private int mWatchers;
    private String mImageOwnerUrl;

    public Repository(String name, String language, int watchers, String imageOwnerUrl) {
        mName = name;
        mLanguage = language;
        mWatchers = watchers;
        mImageOwnerUrl = imageOwnerUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public int getWatchers() {
        return mWatchers;
    }

    public void setWatchers(int watchers) {
        mWatchers = watchers;
    }

    public String getImageOwnerUrl() {
        return mImageOwnerUrl;
    }

    public void setImageOwnerUrl(String imageOwnerUrl) {
        mImageOwnerUrl = imageOwnerUrl;
    }
}