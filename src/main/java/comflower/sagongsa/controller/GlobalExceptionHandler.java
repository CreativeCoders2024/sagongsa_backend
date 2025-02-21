package comflower.sagongsa.controller;

import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.error.ErrorType;
import comflower.sagongsa.error.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ErrorResponse.entity(ErrorType.USER_NOT_FOUND, e.getUserId());
    }

    @ExceptionHandler(ContestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleContestNotFound(ContestNotFoundException e) {
        return ErrorResponse.entity(ErrorType.CONTEST_NOT_FOUND, e.getContestId());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(PostNotFoundException e) {
        return ErrorResponse.entity(ErrorType.POST_NOT_FOUND, e.getPostId());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFound(CommentNotFoundException e) {
        return ErrorResponse.entity(ErrorType.COMMENT_NOT_FOUND, e.getCommentId());
    }
}
