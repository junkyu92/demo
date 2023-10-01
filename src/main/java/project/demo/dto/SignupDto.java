package project.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.demo.domain.Member;

@Getter
@Setter
public class SignupDto {
    private String email;
    private String password;
    private String nickname;
}
