package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

}
