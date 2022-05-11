package com.vttp.miniproject.Project.repositories;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    Logger logger = Logger.getLogger(UserRepository.class.getName());

    @Autowired
    JdbcTemplate template;

    private final String SQL_AUTHENTICATE = "select username from users where username = ?";
    private final String SQL_ADD_USER = "insert into users values (?,?)";

    public boolean authenticate(String username) {
        SqlRowSet result = template.queryForRowSet(SQL_AUTHENTICATE,
                username);
        if (result.next()) {
            logger.info("Authenticated username");
            return result.getString("username").equals(username);
        } else {
            logger.info("Username not in DB");
            return false;
        }
    }

    public boolean addUser(String username) {
        UUID uuid = UUID.randomUUID();
        int result = template.update(SQL_ADD_USER,
                uuid.toString().substring(0, 8),
                username);
        logger.info("Added " + result + " user to DB");
        return (result == 1 && authenticate(username));
    }
}
