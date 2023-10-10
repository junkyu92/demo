package project.demo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.demo.constant.Provider;
import project.demo.constant.Role;
import project.demo.domain.Member;
import project.demo.dto.searchcondition.MemberSearchCondition;
import project.demo.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
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

    @Test
    public void 어드민페이지용_회원검색(){
        memberRepository.save(Member.builder().email("a1").nickname("a1").role(Role.MEMBER).provider(null).build());
        memberRepository.save(Member.builder().email("a2").nickname("a2").role(Role.MANAGER).provider(Provider.GOOGLE).build());
        memberRepository.save(Member.builder().email("a3").nickname("a3").role(Role.ADMIN).provider(Provider.NAVER).build());
        memberRepository.save(Member.builder().email("b1").nickname("b1").role(Role.MEMBER).provider(null).build());
        memberRepository.save(Member.builder().email("b2").nickname("b2").role(Role.MANAGER).provider(Provider.GOOGLE).build());
        memberRepository.save(Member.builder().email("b3").nickname("b3").role(Role.ADMIN).provider(Provider.NAVER).build());

        //페이징 체크
        assertThat(memberService.findForAdminPage(MemberSearchCondition.builder()
                        .build(), PageRequest
                        .of(0,2)).getContent().size())
                .isEqualTo(2);

        //이메일 체크
        assertThat(memberService.findForAdminPage(MemberSearchCondition.builder()
                        .email("b")
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(3);

        //닉네임 체크
        assertThat(memberService.findForAdminPage(MemberSearchCondition.builder()
                        .nickname("1")
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(2);

        //권한 체크
        assertThat(memberService.findForAdminPage(MemberSearchCondition.builder()
                        .role(Role.ADMIN)
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(2);

        //provider 체크
        assertThat(memberService.findForAdminPage(MemberSearchCondition.builder()
                        .provider(Provider.GOOGLE)
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(2);

        //모든 조건 동시 체크
        assertThat(memberService.findForAdminPage(MemberSearchCondition.builder()
                        .email("a")
                        .nickname("2")
                        .role(Role.MANAGER)
                        .provider(Provider.GOOGLE)
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(1);
    }

    @Test
    public void 권한변경(){
        Member saveMember = memberRepository.save(Member.builder().role(Role.ADMIN).build());
        memberService.roleChange(saveMember.getId(), Role.MANAGER);
        assertThat(saveMember.getRole()).isEqualTo(Role.MANAGER);
    }
}