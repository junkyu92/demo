package project.demo.dto;

import jakarta.persistence.*;
import lombok.*;
import project.demo.domain.Board;
import project.demo.domain.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDto {

    private Long id;
    private String name;
    private int postNum;
    private Long postId;
    private String title;
    private LocalDateTime createDate;
    private String nickname;
    @Builder
    public BoardDto(Long id, String name, int postNum, Long postId, String title, LocalDateTime createDate, String nickname) {
        this.id = id;
        this.name = name;
        this.postNum = postNum;
        this.postId = postId;
        this.title = title;
        this.createDate = createDate;
        this.nickname = nickname;
    }

    public BoardDto(Long id, String name, int postNum) {
        this.id = id;
        this.name = name;
        this.postNum = postNum;
    }

    public BoardDto(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.postNum = board.getPostList().size();
    }
}
