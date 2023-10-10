package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.domain.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

}
