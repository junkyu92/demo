package project.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import project.demo.constant.Provider;
import project.demo.constant.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    private Provider provider;
    private String providerId;
    public void changeNickname(String nickname){
        this.nickname = nickname;
    }
    public void changeRole(Role role){
        this.role = role;
    }

    @Builder
    public Member(Long id, String email, String password, String nickname, Role role, List<Post> postList, List<Comment> commentList, Provider provider, String providerId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.postList = postList;
        this.commentList = commentList;
        this.provider = provider;
        this.providerId = providerId;
    }
}
