package com.vttp.miniproject.Project.services;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.vttp.miniproject.Project.models.Comment;
import com.vttp.miniproject.Project.repositories.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

@Service
public class CommentService {
    Logger logger = Logger.getLogger(CommentService.class.getName());

    @Autowired
    private CommentRepository commentRepo;

    @Transactional
    public void postComment(MultiValueMap<String, String> form,
            HttpSession session) {
        // sanitise input
        String commentBody = form.getFirst("commentBody");
        if (commentBody.isBlank()) {
            throw new IllegalArgumentException("Comment body should not be empty");
        }
        // create Comment object
        Comment comment = new Comment();
        comment.setCommentText(commentBody);
        comment.setRating(1);
        comment.setUsername((String) session.getAttribute("username"));
        // add comment object and session uuid to comment table

        // verify added correctly
    }
}
