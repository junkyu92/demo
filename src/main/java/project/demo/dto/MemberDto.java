package project.demo.dto;

import jakarta.persistence.*;
import lombok.*;
import project.demo.constant.Provider;
import project.demo.constant.Role;
import project.demo.domain.BaseTimeEntity;
import project.demo.domain.Comment;
import project.demo.domain.Member;
import project.demo.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto extends BaseTimeEntity {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Role role;
    private int postNum;
    private int commentNum;
    private Provider provider;
    private String providerId;

    @Builder
    public MemberDto(Long id, String email, String password, String nickname, Role role, int postNum, int commentNum, Provider provider, String providerId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.postNum = postNum;
        this.commentNum = commentNum;
        this.provider = provider;
        this.providerId = providerId;
    }

    public MemberDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.role = member.getRole();
        this.postNum = member.getPostList().size();
        this.commentNum = member.getCommentList().size();
        this.provider = member.getProvider();
        this.providerId = member.getProviderId();
    }
}
