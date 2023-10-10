package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
