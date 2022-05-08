package com.vttp.miniproject.Project.services;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import com.vttp.miniproject.Project.models.Listing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import static com.vttp.miniproject.Project.models.Listing.*;

@Service
public class TIHSearchService {
    @Value("${tih.api.key}")
    private String apiKey;

    private final String apiUrl = "https://tih-api.stb.gov.sg/content/v1/food-beverages/search";

    public List<Listing> chooseRandom3() {
        List<Listing> listings = new LinkedList<Listing>();
        return listings;
        // TODO: randomised searchterms and listings
    }

    public List<Listing> keywordChoose3(String keyword) {
        String searchByKeyword = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("keyword", keyword)
                .queryParam("language", "en")
                .queryParam("apikey", apiKey)
                .toUriString();
        // make API call
        RequestEntity<Void> req = RequestEntity.get(searchByKeyword)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
        JsonObject object = reader.readObject();
        JsonArray dataArray = object.getJsonArray("data");
        // grab 3 random items and convert into listing objects
        // TODO: randomise retrieved listings
        List<Listing> listings = dataArray.stream()
                .limit(3)
                .map(value -> createFromJSON(value.asJsonObject()))
                .toList();
        // return list
        return listings;
    }
}
