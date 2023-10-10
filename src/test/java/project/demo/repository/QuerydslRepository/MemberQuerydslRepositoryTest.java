package project.demo.repository.QuerydslRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import project.demo.constant.Provider;
import project.demo.constant.Role;
import project.demo.domain.Member;
import project.demo.dto.MemberDto;
import project.demo.dto.searchcondition.MemberSearchCondition;
import project.demo.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberQuerydslRepositoryTest {

    @Autowired
    private MemberQuerydslRepository memberQuerydslRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원검색쿼리(){
        memberRepository.save(Member.builder().email("a1").nickname("a1").role(Role.MEMBER).provider(null).build());
        memberRepository.save(Member.builder().email("a2").nickname("a2").role(Role.MANAGER).provider(Provider.GOOGLE).build());
        memberRepository.save(Member.builder().email("a3").nickname("a3").role(Role.ADMIN).provider(Provider.NAVER).build());
        memberRepository.save(Member.builder().email("b1").nickname("b1").role(Role.MEMBER).provider(null).build());
        memberRepository.save(Member.builder().email("b2").nickname("b2").role(Role.MANAGER).provider(Provider.GOOGLE).build());
        memberRepository.save(Member.builder().email("b3").nickname("b3").role(Role.ADMIN).provider(Provider.NAVER).build());

        //페이징 체크
        Assertions.assertThat(memberQuerydslRepository.applyPagination(MemberSearchCondition.builder()
                        .build(), PageRequest
                        .of(0,2)).getContent().size())
                .isEqualTo(2);

        //이메일 체크
        Assertions.assertThat(memberQuerydslRepository.applyPagination(MemberSearchCondition.builder()
                        .email("b")
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(3);

        //닉네임 체크
        Assertions.assertThat(memberQuerydslRepository.applyPagination(MemberSearchCondition.builder()
                        .nickname("1")
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(2);

        //권한 체크
        Assertions.assertThat(memberQuerydslRepository.applyPagination(MemberSearchCondition.builder()
                        .role(Role.ADMIN)
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(2);

        //provider 체크
        Assertions.assertThat(memberQuerydslRepository.applyPagination(MemberSearchCondition.builder()
                        .provider(Provider.GOOGLE)
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(2);

        //모든 조건 동시 체크
        Assertions.assertThat(memberQuerydslRepository.applyPagination(MemberSearchCondition.builder()
                        .email("a")
                        .nickname("2")
                        .role(Role.MANAGER)
                        .provider(Provider.GOOGLE)
                        .build(), PageRequest
                        .of(0,5)).getContent().size())
                .isEqualTo(1);
    }

}