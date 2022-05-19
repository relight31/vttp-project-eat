package com.vttp.miniproject.Project.services;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.vttp.miniproject.Project.models.Comment;
import com.vttp.miniproject.Project.repositories.CommentRepository;
import com.vttp.miniproject.Project.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

@Service
public class CommentService {
    Logger logger = Logger.getLogger(CommentService.class.getName());

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    UserRepository userRepo;

    @Transactional
    public void postComment(MultiValueMap<String, String> form,
            HttpSession session) {
        // check username valid?
        if (!userRepo.authenticate((String) session.getAttribute("username"))) {
            throw new SecurityException("Corrupted session: Username not in DB");
        }
        // sanitise input
        String commentBody = form.getFirst("commentBody");
        if (commentBody.trim().isBlank()) {
            throw new IllegalArgumentException("Comment body should not be empty");
        }
        int rating = Integer.parseInt(form.getFirst("rating"));
        // create Comment object
        Comment comment = new Comment();
        comment.setCommentText(commentBody);
        comment.setRating(rating);
        comment.setUsername((String) session.getAttribute("username"));
        // add comment object and session uuid to comment table
        if (!commentRepo.addComment(comment, session)) {
            throw new RuntimeException("Unable to add comment to database");
        }
        // verify added correctly?
    }

    public List<Comment> getComments(HttpSession session) {
        String uuid = (String) session.getAttribute("uuid");
        String username = (String) session.getAttribute("username");
        List<Comment> comments = commentRepo.getCommentsByUuid(username, uuid);
        return comments;
    }
}
