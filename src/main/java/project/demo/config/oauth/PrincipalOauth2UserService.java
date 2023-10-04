package project.demo.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.demo.config.auth.PrincipalDetails;
import project.demo.config.oauth.provider.GoogleUserInfo;
import project.demo.config.oauth.provider.NaverUserInfo;
import project.demo.config.oauth.provider.OAuth2UserInfo;
import project.demo.constant.Role;
import project.demo.domain.Member;
import project.demo.repository.MemberRepository;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("지원하지 않는 요청입니다.");
        }
        Optional<Member> findMember = memberRepository.findByEmail(oAuth2UserInfo.getEmail());

        Member member = null;
        if(findMember.isEmpty()){
            member = createMember(oAuth2UserInfo);
            memberRepository.save(member);
        }else{
            member = findMember.get();
        }
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }

    public Member createMember(OAuth2UserInfo oAuth2UserInfo){
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String nickname = provider + providerId;
        String password = "no";
        String email = oAuth2UserInfo.getEmail();
        Role role = Role.MEMBER;

        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(role)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}
