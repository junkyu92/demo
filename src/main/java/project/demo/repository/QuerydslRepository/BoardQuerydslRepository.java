package project.demo.repository.QuerydslRepository;

import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.demo.domain.Board;
import project.demo.domain.QMember;
import project.demo.domain.QPost;
import project.demo.dto.BoardDto;
import project.demo.repository.support.QuerydslRepositorySupport;

import static project.demo.domain.QBoard.*;
import static project.demo.domain.QMember.*;
import static project.demo.domain.QPost.*;

@Repository
public class BoardQuerydslRepository extends QuerydslRepositorySupport {
    public BoardQuerydslRepository() {
        super(Board.class);
    }

    public Page<BoardDto> applyPagination(Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(Projections.constructor(BoardDto.class,
                        board.id,
                        board.name,
                        board.postList.size()
                        ))
                .from(board));
    }

    public Page<BoardDto> applyPaginationWithPostList(Long id, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(Projections.constructor(BoardDto.class,
                        board.id,
                        board.name,
                        board.postList.size(),
                        post.id.as("postId"),
                        post.title,
                        post.createDate,
                        member.nickname
                ))
                .from(board)
                .leftJoin(board.postList, post)
                .leftJoin(post.member, member)
                .where(board.id.eq(id)));
    }

}
