package project.demo.repository.QuerydslRepository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import project.demo.constant.Provider;
import project.demo.constant.Role;
import project.demo.domain.Member;
import project.demo.domain.QComment;
import project.demo.domain.QPost;
import project.demo.dto.MemberDto;
import project.demo.dto.searchcondition.MemberSearchCondition;
import project.demo.repository.support.QuerydslRepositorySupport;

import static project.demo.domain.QMember.*;

@Repository
public class MemberQuerydslRepository extends QuerydslRepositorySupport {
    public MemberQuerydslRepository() {
        super(Member.class);
    }

    public Page<MemberDto> applyPagination(MemberSearchCondition condition, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(Projections.constructor(MemberDto.class,
                        member.id,
                        member.email,
                        member.password,
                        member.nickname,
                        member.role,
                        member.postList.size(),
                        member.commentList.size(),
                        member.provider,
                        member.providerId))
                .from(member)
                .where(emailLike(condition.getEmail()),
                        nicknameLike(condition.getNickname()),
                        roleEq(condition.getRole()),
                        providerEq(condition.getProvider())));
    }

    private BooleanExpression emailLike(String email) {
        return StringUtils.hasText(email) ? member.email.like(email+"%") : null;
    }

    private BooleanExpression nicknameLike(String nickname) {
        return StringUtils.hasText(nickname) ? member.nickname.contains(nickname) : null;
    }

    private BooleanExpression roleEq(Role role) {
        return role!=null ? member.role.eq(role) : null;
    }

    private BooleanExpression providerEq(Provider provider) {
        return provider!=null ? member.provider.eq(provider) : null;
    }

}
