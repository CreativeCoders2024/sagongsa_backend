package comflower.sagongsa.controller;

import comflower.sagongsa.dto.CreateCommentDTO;
import comflower.sagongsa.dto.EditCommentDTO;
import comflower.sagongsa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
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
//    @GetMapping("/posts/{post_id}/comments")
//    public String getComment(@PathVariable long post_id){
//
//    }




}
