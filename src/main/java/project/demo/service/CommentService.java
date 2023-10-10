package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Comment;
import project.demo.dto.CommentDto;
import project.demo.repository.CommentRepository;
import project.demo.repository.QuerydslRepository.CommentQuerydslRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentQuerydslRepository commentQuerydslRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Page<CommentDto> findAllByPostId(Long id, Pageable pageable) {
        return commentQuerydslRepository.applyPagination(id, pageable);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment findById(Long parentId) {
        return commentRepository.findById(parentId).orElseThrow();
    }
}
