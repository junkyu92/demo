package project.demo.repository;

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
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원저장(){
        Member member = Member.builder()
                .email("test@gmail.com")
                .password("1234")
                .nickname("test")
                .role(Role.MEMBER)
                .build();

        assertThat(memberRepository.save(member)).isEqualTo(member);
    }

    @Test
    public void 회원조회_이메일(){
        Member member = Member.builder()
                .email("test@gmail.com")
                .password("1234")
                .nickname("test")
                .role(Role.MEMBER)
                .build();

        memberRepository.save(member);

        assertThat(memberRepository.findByEmail("test@gmail.com").orElseThrow()).isEqualTo(member);
    }

}