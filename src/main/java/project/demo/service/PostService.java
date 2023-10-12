package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Post;
import project.demo.dto.PostDto;
import project.demo.repository.PostRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    public Long save(Post post) {
        return postRepository.save(post).getId();
    }

    public void changePost(Long id, String title, String content) {
        Optional<Post> findPost = postRepository.findById(id);
        findPost.ifPresent(post -> post.changePost(title, content));
    }

    @Transactional(readOnly = true)
    public PostDto findById(Long id) {
        return new PostDto(postRepository.findById(id).orElseThrow());
    }
}
