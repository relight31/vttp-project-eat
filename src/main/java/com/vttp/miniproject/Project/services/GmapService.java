package com.vttp.miniproject.Project.services;

import java.util.logging.Logger;

import com.vttp.miniproject.Project.models.Listing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GmapService {
    @Value("${gmaps.api.key}")
    private String gmapsApiKey;

    public String getGmapsUrlString(Listing listing) {
        Logger logger = Logger.getLogger(GmapService.class.getName());

        String url = "https://www.google.com/maps/embed/v1/search";
        String gmapsUrlString = UriComponentsBuilder.fromUriString(url)
                .queryParam("key", gmapsApiKey)
                .queryParam("q", cleanSearchQuery(listing.getName()))
                .queryParam("center",
                        String.valueOf(listing.getLatitude()) + "," + String.valueOf(listing.getLongitude()))
                .queryParam("zoom", 17)
                .toUriString();
        logger.info("Gmap src: " + gmapsUrlString);
        return gmapsUrlString;
    }

    public String cleanSearchQuery(String query) {
        if (query != null) {
            query = query.replaceAll("\\s+", " ");
            query = query.replaceAll("[^a-zA-Z0-9_ ]", "");
            query = query.trim();
            query = query.toLowerCase();
            query = query.replaceAll("\\s+", "+");
        }
        return query;
    }
}
