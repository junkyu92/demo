package project.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import project.demo.config.SecurityConfig;
import project.demo.constant.Role;
import project.demo.domain.Member;
import project.demo.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Test
    public void 회원가입_페이지() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/signup"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 회원가입() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                                .param("email", "test@gmail.com")
                                .param("password", "12345678")
                                .param("nickname", "test")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    public void 회원가입_예외() throws Exception {
        //이메일 유효성
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .param("email", "test")
                        .param("password", "12345678")
                        .param("nickname", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //비밀번호 유효성
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .param("email", "test@gmail.com")
                        .param("password", "12")
                        .param("nickname", "test")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        //닉네임 유효성
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .param("email", "test@gmail.com")
                        .param("password", "12345678")
                        .param("nickname", "t")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void 로그인_페이지() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void 로그인() throws Exception {
        Member member = new Member(null, "test@gmail.com", passwordEncoder.encode("12345678"), "test", Role.MEMBER, null);
        memberRepository.save(member);
        mockMvc.perform(MockMvcRequestBuilders.post("/loginProc")
                        .param("email", "test@gmail.com")
                        .param("password", "12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }
}