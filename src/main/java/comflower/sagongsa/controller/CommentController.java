package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public String createComment(@PathVariable long postId, @RequestBody CreateCommentDTO createCommentDTO) {
        commentService.createComment(postId, createCommentDTO);
        return "Comment created successfully";
    }

    @PutMapping("/posts/{postid}/comments/{commentid}")
    public String editComment(@PathVariable long postid, @PathVariable long commentid, @RequestBody EditCommentDTO editCommentDTO){
     commentService.editComment(postid, commentid, editCommentDTO);
     return "Comment edited successfully";
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable long postId, @PathVariable long commentId){
        commentService.deleteComment(commentId);
        return "Success Delete Contest with id: " + commentId;
    }
    @GetMapping("/posts/{postId}/comments/")
    public List<Comment> getComment(@PathVariable long postId) {
       return commentService.getComment(postId);
    }

}
