package project.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.demo.domain.Member;

@Getter
@Setter
public class SignupDto {
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "알파벳 대소문자와 숫자만 입력가능합니다.")
    private String password;

    @NotBlank
    @Size(min = 2, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "알파벳 대소문자와 숫자만 입력가능합니다.")
    private String nickname;
}
