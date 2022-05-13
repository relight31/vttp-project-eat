package com.vttp.miniproject.Project.repositories;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.vttp.miniproject.Project.models.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class CommentRepository {
    private final String SQL_GET_COMMENTS_BY_USER_AND_UUID = "select * from comments where user_id ";
    private final String SQL_POST_COMMENT = "insert into comments (rating, user_id, text, listing_id) values (?,?,?,?)";

    @Autowired
    UserRepository userRepo;

    @Autowired
    JdbcTemplate template;

    public List<Comment> getCommentsByUserAndUuid(
            String username,
            String uuid) {
        return null;
    }

    public boolean addComment(Comment comment, HttpSession session) {
        String userId = userRepo.getUserIdByUsername(comment.getUsername());
        int result = template.update(SQL_POST_COMMENT,
                comment.getRating(),
                userId,
                comment.getCommentText(),
                session.getAttribute("uuid"));
        return result == 1;
    }
}
