package comflower.sagongsa.service;


import comflower.sagongsa.dto.CheckPostDTO;
import comflower.sagongsa.dto.CreatePostDTO;
import comflower.sagongsa.dto.EditPostDTO;
import comflower.sagongsa.dto.ListPostDTO;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    //게시글 생성
    @Transactional
    public void createPost(CreatePostDTO createPostDTO) {
        Post post = Post.builder()
                .userId(createPostDTO.getUserId())
                .contestId(createPostDTO.getContestId())
                .title(createPostDTO.getTitle())
                .content(createPostDTO.getContent())
                .max(createPostDTO.getMax())
                .ppl(createPostDTO.getPpl())
                .desiredField(createPostDTO.getDesiredField())
                .createdAt(createPostDTO.getCreatedAt())
                .endedAt(createPostDTO.getEndedAt())
                .build();
        postRepository.save(post);
    }
    //게시글 목록
    @Transactional(readOnly = true)
    public List<ListPostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> ListPostDTO.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .createdAt(post.getCreatedAt())
                        .endedAt(post.getEndedAt())
                        .desiredField(post.getDesiredField())  // 분야 정보 추가
                        .build())
                .collect(Collectors.toList());
    }
    // 게시글 상세조회
    @Transactional(readOnly = true)
    public CheckPostDTO getPostById(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id : " + postId + " not found"));

        return CheckPostDTO.builder()
                .postId(post.getPostId())
                .userId(post.getUserId())
                .contestId(post.getContestId())
                .title(post.getTitle())
                .content(post.getContent())
                .max(post.getMax())
                .ppl(post.getPpl())
                .desiredField(post.getDesiredField())
                .createdAt(post.getCreatedAt())
                .endedAt(post.getEndedAt())
                .build();
    }

    //게시글 수정
    @Transactional
    public void editPost(Long postId, EditPostDTO editPostDTO) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id : " + postId + " not found"));

        post = Post.builder()
                .postId(postId)
                .userId(editPostDTO.getUserId())
                .contestId(editPostDTO.getContestId())
                .title(editPostDTO.getTitle())
                .content(editPostDTO.getContent())
                .max(editPostDTO.getMax())
                .ppl(editPostDTO.getPpl())
                .desiredField(editPostDTO.getDesiredField())
                .createdAt(editPostDTO.getCreatedAt())
                .endedAt(editPostDTO.getEndedAt())
                .build();

        postRepository.save(post);
    }

    //게시글 삭제

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id : " + postId + " not found"));
        postRepository.delete(post);
    }
}
