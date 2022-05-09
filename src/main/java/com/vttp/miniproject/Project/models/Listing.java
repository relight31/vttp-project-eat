package com.vttp.miniproject.Project.models;

import jakarta.json.JsonObject;

public class Listing {
    private String name;
    private String thumbnail;
    private String uuidPath;
    private float latitude;
    private float longitude;
    private String contact;
    private String body;
    private float rating;
    private String website;
    private String nextToken;

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

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

    public static Listing createFromSearchJSON(JsonObject object) {
        Listing listing = new Listing();
        listing.setName(object.getString("name"));
        listing.setUuidPath(object.getString("uuid"));
        // TODO remove from final release
        System.out.println(listing.getName());
        System.out.println(listing.getUuidPath());
        return listing;
    }

    public static Listing createFromUuidJSON(JsonObject object) {
        Listing listing = createFromSearchJSON(object);
        return listing;
    }
}
