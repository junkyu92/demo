package project.demo.dto;

import jakarta.persistence.*;
import lombok.*;
import project.demo.domain.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto extends BaseTimeEntity {

    private Long id;
    private String title;
    private String content;
    private Member member;
    private Board board;
    private Long boardId;
    private List<Comment> commentList = new ArrayList<>();
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


}
