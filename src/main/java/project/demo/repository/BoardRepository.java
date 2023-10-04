package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);

    @Override
    List<Board> findAll();
}
