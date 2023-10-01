package project.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import project.demo.constant.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }
}
