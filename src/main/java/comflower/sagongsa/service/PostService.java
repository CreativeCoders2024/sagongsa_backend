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
                .desiredField(createPostDTO.getDesired_field())
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
    public void editPost(EditPostDTO editPostDTO) {
        Post post = postRepository.findById(editPostDTO.getPostId())
                .orElseThrow(() -> new IllegalStateException("Post with id : " + editPostDTO.getPostId() + " not found"));

        if (editPostDTO.getTitle() != null) {
            post.setTitle(editPostDTO.getTitle());
        }
        if (editPostDTO.getContent() != null) {
            post.setContent(editPostDTO.getContent());
        }
        if (editPostDTO.getMax() != null) {
            post.setMax(editPostDTO.getMax());
        }
        // DTO에서 int 를 Integer로 바꾸면 0이 아니라 널 값으로 집어 넣을 수 있음
        if (editPostDTO.getPpl() != null) {
            post.setPpl(editPostDTO.getPpl());
        }
        // DTO에서 int 를 Integer로 바꾸면 0이 아니라 널 값으로 집어 넣을 수 있음
        if (editPostDTO.getDesiredField() != null) {
            post.setDesiredField(editPostDTO.getDesiredField());
            // DTO에서 int 를 Integer로 바꾸면 0이 아니라 널 값으로 집어 넣을 수 있음

        }
        if (editPostDTO.getCreatedAt() != null) {
            post.setCreatedAt(editPostDTO.getCreatedAt());
        }
        if (editPostDTO.getEndedAt() != null) {
            post.setEndedAt(editPostDTO.getEndedAt());
        }
        // save를 굳이 안써도 영속성 컨텍스트가 자동으로 변경사항을 인지하여 데이터베이스에 반영
    }




        // 여기 보면 post = 어쩌고 정의를 두번 해놔서 74, 75번째 줄에 있는 postRepository.findByPostId(postId) 어쩌고 얘가 싹 다 씹힘!!
        // 없는 것 처럼 된거고, 뒤에 있는 Post.builder() 얘가 결과적으로 post 안에 들어가게 돼서 위에 애는 없는것처럼 필요없는 코드처럼 됐어...!!
        // 색깔 보면 주석이랑 같은 색깔로 74, 75번째 코드가 죽어있을거야



    //게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id : " + postId + " not found"));
        postRepository.delete(post);
    }
}
