package com.vttp.miniproject.Project.models;

import jakarta.json.JsonObject;

public class Listing {
    private String name;
    private String thumbnail;
    private String uuidPath;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUuidPath() {
        return this.uuidPath;
    }

    public void setUuidPath(String uuidPath) {
        this.uuidPath = uuidPath;
    }

    public static Listing createFromJSON(JsonObject object) {
        Listing listing = new Listing();
        listing.setName(object.getString("name"));
        listing.setUuidPath(object.getString("uuid"));
        System.out.println(listing.getName());
        System.out.println(listing.getUuidPath());
        return listing;
    }
}
