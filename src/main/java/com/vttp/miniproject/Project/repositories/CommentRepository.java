package com.vttp.miniproject.Project.repositories;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.vttp.miniproject.Project.models.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

@Repository
public class CommentRepository {
    private final String SQL_GET_COMMENTS_BY_UUID = "select * from commentsview where uuid = ?";
    private final String SQL_POST_COMMENT = "insert into comments (rating, user_id, text, listing_id) values (?,?,?,?)";

    @Autowired
    UserRepository userRepo;

    @Autowired
    JdbcTemplate template;

    public List<Comment> getCommentsByUuid(String username, String uuid) {
        SqlRowSet result = template.queryForRowSet(
                SQL_GET_COMMENTS_BY_UUID,
                uuid);
        List<Comment> comments = new LinkedList<>();
        while (result.next()) {
            Comment comment = new Comment();
            comment.setCommentText(result.getString("text"));
            comment.setRating(result.getInt("rating"));
            comment.setUsername(result.getString("username"));
            comments.add(comment);
        }
        return comments;
    }

    public boolean addComment(Comment comment, HttpSession session) {
        String userId = userRepo.getUserIdByUsername(comment.getUsername());
        int result = template.update(SQL_POST_COMMENT,
                comment.getRating(),
                userId,
                comment.getCommentText(),
                (String) session.getAttribute("uuid"));
        return result == 1;
    }
}
