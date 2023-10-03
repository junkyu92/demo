package project.demo.config.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.demo.constant.Role;
import project.demo.domain.Member;
import project.demo.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PrincipalDetailsServiceTest {

    @Autowired PrincipalDetailsService principalDetailsService;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Test
    public void 로그인_이메일확인(){
        Member member = Member.builder()
                .email("test@gmail.com")
                .password(passwordEncoder.encode("12345678"))
                .nickname("test")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(member);

        assertThat(principalDetailsService.loadUserByUsername("test@gmail.com")).isNotNull();
    }

    @Test
    public void 로그인_이메일확인_예외(){
        Member member = Member.builder()
                .email("test@gmail.com")
                .password(passwordEncoder.encode("12345678"))
                .nickname("test")
                .role(Role.MEMBER)
                .build();
        memberRepository.save(member);

        assertThrows(IllegalArgumentException.class, ()->{principalDetailsService.loadUserByUsername("dsgdsgdsg");});
    }
}