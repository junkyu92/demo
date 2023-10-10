package project.demo.repository.QuerydslRepository;

import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.demo.domain.Comment;
import project.demo.domain.QComment;
import project.demo.domain.QMember;
import project.demo.domain.QPost;
import project.demo.dto.CommentDto;
import project.demo.repository.support.QuerydslRepositorySupport;

import static project.demo.domain.QComment.*;
import static project.demo.domain.QMember.*;
import static project.demo.domain.QPost.*;


@Repository
public class CommentQuerydslRepository extends QuerydslRepositorySupport {
    public CommentQuerydslRepository() {
        super(Comment.class);
    }

    public Page<CommentDto> applyPagination(Long id, Pageable pageable) {
        QComment qComment = new QComment("parent");
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(Projections.constructor(CommentDto.class,
                        comment.id,
                        member.id,
                        member.nickname,
                        comment.content,
                        qComment.id,
                        comment.createDate,
                        post.id
                        ))
                .from(comment)
                .leftJoin(comment.member, member)
                .leftJoin(comment.parent, qComment)
                .leftJoin(comment.post, post)
                .where(comment.post.id.eq(id)));
    }

}
