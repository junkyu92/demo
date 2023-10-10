package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Post;
import project.demo.dto.PostDto;
import project.demo.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    public void save(Post post) {
        postRepository.save(post);
    }

    public PostDto findById(Long id) {
        return postRepository.findById(id);
    }
}
