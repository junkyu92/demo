package project.demo.dto;

import jakarta.persistence.*;
import lombok.*;
import project.demo.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Member member;
    private Board board;
    private Long boardId;
    private LocalDateTime createDate;
    //이미지 업로드 관련 추가

    public Post changePost() {
        return Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .member(this.member)
                .board(Board.builder().id(this.boardId).build())
                .build();
    }

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.member = post.getMember();
        this.board = post.getBoard();
        this.createDate = post.getCreateDate();
    }

    @Builder
    public PostDto(Long id, String title, String content, Member member, Board board, Long boardId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.board = board;
        this.boardId = boardId;
    }
}
