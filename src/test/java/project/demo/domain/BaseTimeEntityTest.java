package project.demo.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.demo.constant.Role;
import project.demo.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BaseTimeEntityTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;
    private Member saveMember;
    @BeforeEach
    public void before(){
        Member member = new Member(null, "test@gmail.com", "1234", "test", Role.MEMBER, null);
        saveMember = memberRepository.save(member);
    }
    @Test
    public void 등록일등록(){
        assertThat(saveMember.getCreateDate()).isNotNull();
        assertThat(saveMember.getLastModifiedDate()).isNotNull();
        assertThat(saveMember.getCreateDate()).isEqualTo(saveMember.getLastModifiedDate());
    }

    @Test
    public void 수정일변경(){
        saveMember.changeNickname("change");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findByEmail("test@gmail.com").get();
        assertThat(saveMember.getCreateDate()).isNotNull();
        assertThat(saveMember.getLastModifiedDate()).isNotNull();
        assertThat(findMember.getCreateDate()).isNotEqualTo(findMember.getLastModifiedDate());
    }
}