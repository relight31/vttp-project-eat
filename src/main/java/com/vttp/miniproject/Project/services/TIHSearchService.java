package com.vttp.miniproject.Project.services;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

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
    Logger logger = Logger.getLogger(TIHSearchService.class.getName());

    @Value("${tih.api.key}")
    private String apiKey;

    private Random rand = new Random();

    private String[] categories = new String[] {
            "cafes",
            "dim sum",
            "zi char",
            "indian",
            "korean",
            "mookata",
            "steak",
            "french",
            "thai",
            "burger",
            "italian",
            "pasta",
            "pizza",
            "sushi",
            "japanese",
            "buffet",
            "chinese",
            "vegan",
            "mediterranean",
            "seafood",
            "turkish",
            "hawker",
            "malay",
            "hot pot",
            "bbq",
            "mexican",
            "ramen",
            "spanish",
            "western"
    };

    private final String apiUrl = "https://tih-api.stb.gov.sg/content/v1/food-beverages/search";
    private final String uuidUrl = "https://tih-api.stb.gov.sg/content/v1/food-beverages";

    public Optional<List<Listing>> chooseRandom3() {
        int index = rand.nextInt(0, categories.length);
        return keywordChoose3(categories[index]);
    }

    public Optional<List<Listing>> keywordChoose3(String keyword) {
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
        if (object.getJsonObject("status").getInt("code") == 200) {
            JsonArray dataArray = object.getJsonArray("data");
            // grab 3 random items and convert into listing objects
            List<Listing> listings = rand.ints(0, dataArray.size())
                    .distinct()
                    .limit(3)
                    .mapToObj(i -> createFromSearchJSON(dataArray.get(i).asJsonObject()))
                    .toList();
            // return list
            return Optional.of(listings);
        } else {
            return Optional.empty();
        }

    }

    public Optional<Listing> searchListingByUuid(String uuid) {
        String searchByUuid = UriComponentsBuilder
                .fromUriString(uuidUrl)
                .queryParam("uuid", uuid)
                .queryParam("language", "en")
                .queryParam("apikey", apiKey)
                .toUriString();
        // make API call
        RequestEntity<Void> req = RequestEntity.get(searchByUuid)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        RestTemplate template = new RestTemplate();
        try {
            ResponseEntity<String> resp = template.exchange(req, String.class);
            // throws HttpClientErrorException$NotFound if bad uuid given
            JsonReader reader = Json.createReader(
                    new StringReader(
                            resp.getBody()));
            JsonObject object = reader.readObject();
            if (object.getJsonObject("status").getInt("code") == 200) {
                JsonArray dataArray = object.getJsonArray("data");
                Listing listing = createFromUuidJSON(dataArray
                        .get(0)
                        .asJsonObject());
                return Optional.of(listing);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
