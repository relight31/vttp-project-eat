package com.vttp.miniproject.Project.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TIHSearchService {
    @Value("${tih.api.key}")
    private String apiKey;
}
