package com.vttp.miniproject.Project.repositories;

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

    private final String SQL_AUTHENTICATE = "select username from users where username = ? and password = sha2(?)";
    private final String SQL_DOES_USERNAME_EXIST = "select user_id from users where username = ?";

    public boolean authenticate(String username, String password) {
        SqlRowSet result = template.queryForRowSet(SQL_AUTHENTICATE, username, password);
        if (result.next()) {
            logger.info("Authenticated username and password");
            return result.getString("username").equals(username);
        } else {
            logger.info("Username or Password not in DB");
            return false;
        }
    }

    public String doesUserExist(String username) {
        SqlRowSet result = template.queryForRowSet(SQL_DOES_USERNAME_EXIST, username);
        if (result.next()) {
            logger.info("User " + username + " exists");
            return result.getString("user_id");
        } else {
            logger.info("User " + username + " does not exist");
            return null;
        }
    }
}
