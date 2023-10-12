package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.demo.config.auth.PrincipalDetails;
import project.demo.domain.Board;
import project.demo.dto.PostDto;
import project.demo.repository.BoardRepository;
import project.demo.service.BoardService;
import project.demo.service.CommentService;
import project.demo.service.PostService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final CommentService commentService;
    private final PostService postService;
    private final BoardRepository boardRepository;

    @GetMapping({"/post/form/{boardId}", "/post/form/{boardId}/{postId}"})
    public String postCreateForm(@PathVariable("boardId") Optional<Long> boardId, @PathVariable("postId") Optional<Long> postId, Model model) {
        PostDto postDto = postId.map(postService::findById).orElse(PostDto.builder().boardId(boardId.orElseThrow()).build());
        model.addAttribute("post", postDto);
        return "post/form";
    }

    @PostMapping("/post")
    public @ResponseBody String postCreate(PostDto postDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        postDto.setMember(principalDetails.getMember());
        postDto.setBoard(boardRepository.findById(postDto.getBoardId()).orElseThrow());
        Long saveId = postService.save(postDto.changePost());
        return "/post/"+saveId;
    }

    @PatchMapping("/post")
    public @ResponseBody String postUpdate(PostDto postDto) {
        postService.changePost(postDto.getId(), postDto.getTitle(), postDto.getContent());
        return "/post/"+postDto.getId();
    }

    @GetMapping("/post/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return "post/post";
    }
}
