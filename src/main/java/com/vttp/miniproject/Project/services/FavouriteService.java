package com.vttp.miniproject.Project.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.vttp.miniproject.Project.models.Listing;
import com.vttp.miniproject.Project.repositories.FavouriteRepository;
import com.vttp.miniproject.Project.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavouriteService {
    Logger logger = Logger.getLogger(FavouriteService.class.getName());
    
    @Autowired
    UserRepository userRepo;

    @Autowired
    FavouriteRepository favRepo;

    @Autowired
    TIHSearchService searchService;

    public Optional<List<Listing>> getFavourites(HttpSession session) {
        String userId = userRepo.getUserIdByUsername(
                (String) session.getAttribute("username"));
        logger.info("userId: " + userId);
        List<String> favourites = favRepo.getFavouritesUuidByUser(userId);
        List<Listing> result = new LinkedList<>();
        if (favourites.size() == 0) {
            logger.info("No favourites returned");
            return Optional.empty();
        }
        for (String uuid : favourites) {
            Optional<Listing> opt = searchService.searchListingByUuid(uuid);
            if (opt.isEmpty()) {
                logger.info("Failed to convert UUID to Listing object");
                return Optional.empty();
            } else {
                Listing listing = opt.get();
                result.add(listing);
                logger.info("Listing " + listing.getUuidPath() + " converted and added");
            }
        }
        logger.info("All Listing objects created");
        return Optional.of(result);
    }

    @Transactional
    public void addToFavourites(
            HttpSession session,
            String uuid) {
        // check username valid?
        if (!userRepo.authenticate((String) session.getAttribute("username"))) {
            throw new SecurityException("Corrupted session: Username not in DB");
        }
        // check uuid present?
        if (uuid.isBlank()) {
            throw new IllegalArgumentException("No uuid available");
        }
        // check if in favourites alr
        String userId = userRepo.getUserIdByUsername(
                (String) session.getAttribute("username"));
        if (favRepo.isInFavourites(userId, uuid)) {
            throw new IllegalArgumentException("Already in favourites");
        }
        // add to favourites
        if (!favRepo.addToFavourites(userId, uuid)) {
            throw new RuntimeException("Unable to add to favourites");
        }
    }
}
