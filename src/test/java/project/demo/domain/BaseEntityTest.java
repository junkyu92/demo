package project.demo.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import project.demo.config.auth.PrincipalDetailsService;
import project.demo.constant.Role;
import project.demo.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaseEntityTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PrincipalDetailsService principalDetailsService;
    @Autowired
    private EntityManager em;
    private Member saveMember;
    @BeforeEach
    public void before(){
        UserDetails userDetails = principalDetailsService.loadUserByUsername("test@gmail.com");

        Member member = new Member(null, "test@gmail.com", "1234", "test", Role.MEMBER, null);
        saveMember = memberRepository.save(member);
    }

    @Test
    public void 등록자(){
        assertThat(saveMember.getCreatedBy()).isNotNull();
        assertThat(saveMember.getLastModifiedBy()).isNotNull();
        assertThat(saveMember.getCreatedBy()).isEqualTo(saveMember.getLastModifiedBy());
    }

    @Test
    public void 수정자(){
        saveMember.changeNickname("change");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findByEmail("test@gmail.com").get();
        assertThat(saveMember.getCreatedBy()).isNotNull();
        assertThat(saveMember.getLastModifiedBy()).isNotNull();
        assertThat(findMember.getCreatedBy()).isNotEqualTo(findMember.getLastModifiedBy());
    }
}