package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.demo.domain.Member;
import project.demo.constant.Role;
import project.demo.dto.LoginDto;
import project.demo.dto.SignupDto;
import project.demo.service.MemberService;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signupPage(SignupDto signupDto, Model model) {
        model.addAttribute("signupDto", signupDto);
        return "members/signupForm";
    }

    @PostMapping("/signup")
    public String signup(SignupDto signupDto) {
        memberService.signup(new Member(null, signupDto.getEmail(), passwordEncoder.encode(signupDto.getPassword()),signupDto.getNickname(), Role.MEMBER, null));
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(LoginDto loginDto, Model model) {
        model.addAttribute("loginDto", loginDto);
        return "members/loginForm";
    }
}
