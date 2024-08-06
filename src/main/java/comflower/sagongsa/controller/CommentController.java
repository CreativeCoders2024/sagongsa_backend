package comflower.sagongsa.controller;

import comflower.sagongsa.dto.CreateCommentDTO;
import comflower.sagongsa.dto.EditCommentDTO;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public Comment createComment(@PathVariable long postId, @RequestBody CreateCommentDTO createCommentDTO) {
        return commentService.createComment(postId, createCommentDTO);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public Comment editComment(@PathVariable long commentId, @RequestBody EditCommentDTO editCommentDTO) {
        return commentService.editComment(commentId, editCommentDTO);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
        return "Success Delete Contest with id: " + commentId;
    }

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getComments(@PathVariable long postId) {
        return commentService.getComments(postId);
    }
}
