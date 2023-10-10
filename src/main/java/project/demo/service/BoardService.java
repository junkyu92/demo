package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Board;
import project.demo.dto.BoardDto;
import project.demo.repository.BoardRepository;
import project.demo.repository.QuerydslRepository.BoardQuerydslRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQuerydslRepository boardQuerydslRepository;

    public BoardDto findById(Long id) {
        return new BoardDto(boardRepository.findById(id).orElseThrow());
    }

    public Page<BoardDto> boardPage(Long id, Pageable pageable) {
        return boardQuerydslRepository.applyPaginationWithPostList(id, pageable);
    }

    public Page<BoardDto> findForManagerPage(Pageable pageable) {
        return boardQuerydslRepository.applyPagination(pageable);
    }

    public void create(String name) {
        Optional<Board> findBoard = boardRepository.findByname(name);
        if (findBoard.isPresent()) {
            findBoard.get().changeName(name);
        } else {
            boardRepository.save(Board.builder().name(name).build());
        }
    }

    public void changeBoardName(Long id, String name) {
        Optional<Board> findBoard = boardRepository.findById(id);
        if(findBoard.isPresent()) findBoard.get().changeName(name);
    }
    public void delete(Long id) {
        boardRepository.delete(Board.builder().id(id).build());
    }
}
