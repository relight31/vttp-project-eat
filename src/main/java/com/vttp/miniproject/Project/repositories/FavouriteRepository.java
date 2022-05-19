package com.vttp.miniproject.Project.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class FavouriteRepository {
    Logger logger = Logger.getLogger(FavouriteRepository.class.getName());

    private final String SQL_GET_FAVS_BY_USER = "select * from favourites where user_id = ?";
    private final String SQL_ADD_TO_FAVS = "insert into favourites (user_id, listing_id) values (?,?)";
    private final String SQL_IS_IN_FAVS = "select * from favourites where user_id = ? and listing_id = ?";

    @Autowired
    JdbcTemplate template;

    public List<String> getFavouritesUuidByUser(String userId) {
        List<String> favourites = new LinkedList<>();
        SqlRowSet result = template.queryForRowSet(
                SQL_GET_FAVS_BY_USER,
                userId);
        while (result.next()) {
            favourites.add(result.getString("listing_id"));
        }
        logger.info("Retrieved " + favourites.size() + " UUIDs");
        return favourites;
    }

    public boolean isInFavourites(String userId, String uuid) {
        SqlRowSet result = template.queryForRowSet(SQL_IS_IN_FAVS,
                userId,
                uuid);
        return result.next();
    }

    public boolean addToFavourites(String userId, String uuid) {
        int result = template.update(SQL_ADD_TO_FAVS,
                userId,
                uuid);
        return result == 1;
    }
}
