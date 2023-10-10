package project.demo.dto.searchcondition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.demo.constant.Provider;
import project.demo.constant.Role;

@Getter
@Setter
public class MemberSearchCondition {
    private String email;
    private String nickname;
    private Role role;
    private Provider provider;

    @Builder
    public MemberSearchCondition(String email, String nickname, Role role, Provider provider) {
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
    }
}
