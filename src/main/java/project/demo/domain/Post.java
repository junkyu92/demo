package project.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();
    //이미지 업로드 관련 추가


    @Builder
    public Post(Long id, String title, String content, Member member, Board board, List<Comment> commentList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.board = board;
        this.commentList = commentList;
    }
}
