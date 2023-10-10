package project.demo.dto;


import jakarta.persistence.*;
import lombok.*;
import project.demo.domain.BaseTimeEntity;
import project.demo.domain.Comment;
import project.demo.domain.Member;
import project.demo.domain.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDto{
    private Long id;
    private Post post;
    private Member member;
    private String content;
    private Comment parent;
    private Long postId;
    private Long memberId;
    private String memberNickname;
    private Long parentId;
    private LocalDateTime createDate;

    @Builder
    public CommentDto(Long id, Post post, Member member, String content, Comment parent, Long postId, Long memberId, String memberNickname, Long parentId, LocalDateTime createDate) {
        this.id = id;
        this.post = post;
        this.member = member;
        this.content = content;
        this.parent = parent;
        this.postId = postId;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.parentId = parentId;
        this.createDate = createDate;
    }

    public CommentDto(Long id, Long memberId, String memberNickname, String content, Long parentId, LocalDateTime createDate, Long postId) {
        this.id = id;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.content = content;
        this.parentId = parentId;
        this.createDate = createDate;
        this.postId = postId;
    }

    public Comment changeComment(CommentDto commentDto) {
        return Comment.builder()
                .post(Post.builder().id(commentDto.getPostId()).build())
                .member(commentDto.getMember())
                .content(commentDto.getContent())
                .parent(commentDto.getParent())
                .build();
    }
}
