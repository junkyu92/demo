package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.demo.config.auth.PrincipalDetails;
import project.demo.domain.Board;
import project.demo.dto.PostDto;
import project.demo.service.BoardService;
import project.demo.service.CommentService;
import project.demo.service.PostService;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final CommentService commentService;
    private final PostService postService;


    @GetMapping("/post/createForm/{boardId}")
    public String postCreateForm(@PathVariable("boardId") Long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "post/createForm";
    }

    @PostMapping("/post")
    public String postCreate(PostDto postDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postDto.setMember(principalDetails.getMember());
        postService.save(postDto.changePost());
        return "redirect:/board/" + postDto.getBoardId();
    }

    @GetMapping("/post/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "post/post";
    }
}
