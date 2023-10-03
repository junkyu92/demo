package project.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import project.demo.constant.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    private String provider;
    private String providerId;
    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    @Builder
    public Member(Long id, String email, String password, String nickname, Role role, List<Comment> commentList, String provider, String providerId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.commentList = commentList;
        this.provider = provider;
        this.providerId = providerId;
    }
}
