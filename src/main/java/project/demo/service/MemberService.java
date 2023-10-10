package project.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.demo.constant.Role;
import project.demo.domain.Member;
import project.demo.dto.MemberDto;
import project.demo.dto.searchcondition.MemberSearchCondition;
import project.demo.repository.MemberRepository;
import project.demo.repository.QuerydslRepository.MemberQuerydslRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQuerydslRepository memberQuerydslRepository;

    public Member signup(Member member) {
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Page<MemberDto> findForAdminPage(MemberSearchCondition memberSearchCondition, Pageable pageable) {
        return memberQuerydslRepository.applyPagination(memberSearchCondition, pageable);
    }

    public void roleChange(Long id, Role role) {
        Optional<Member> findMember = memberRepository.findById(id);
        findMember.ifPresent(member -> member.changeRole(role));
    }
}
