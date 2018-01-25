package com.example.apple.amazon.Model;

/**
 * Created by apple on 18/01/18.
 */

public class Data {
    private String name;
    private String thumbnail;

    public Data(String name, String thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public Data() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
