package project.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.demo.constant.Role;
import project.demo.domain.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입(){
        Member member = Member.builder()
                .email("test@gmail.com")
                .password("1234")
                .nickname("test")
                .role(Role.MEMBER)
                .build();
        assertThat(memberService.signup(member)).isEqualTo(member);
    }
}