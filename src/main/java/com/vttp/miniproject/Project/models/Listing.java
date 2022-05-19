package com.vttp.miniproject.Project.models;

import jakarta.json.JsonObject;

public class Listing {
    private String name;
    private String thumbnail;
    private String uuidPath;
    private double latitude;
    private double longitude;
    private String contact;
    private String body;
    private String description;
    private double rating;
    private String website;
    private String nextToken;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

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

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
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
        // TODO finish thumbnail
        Listing listing = createFromSearchJSON(object);
        listing.setLatitude(object.getJsonObject("location")
                .getJsonNumber("latitude")
                .doubleValue());
        listing.setLongitude(object.getJsonObject("location")
                .getJsonNumber("longitude")
                .doubleValue());
        listing.setBody(object.getString("body"));
        listing.setContact(object.getJsonObject("contact")
                .getString("primaryContactNo"));
        listing.setWebsite(object.getString("officialWebsite"));
        listing.setDescription(object.getString("description"));
        listing.setRating(object.getJsonNumber("rating").doubleValue());
        return listing;
    }
}
