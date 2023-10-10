package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.demo.config.auth.PrincipalDetails;
import project.demo.dto.CommentDto;
import project.demo.service.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments")
    public String commentList(Model model, @RequestParam("postId") Long id, @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("comments", commentService.findAllByPostId(id, pageable));
        return "/post/ajax/comment-list";
    }

    @PostMapping("/comment")
    public String commentCreate(CommentDto commentDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentDto.setMember(principalDetails.getMember());
        if(commentDto.getParentId()!=null) commentDto.setParent(commentService.findById(commentDto.getParentId()));
        commentService.save(commentDto.changeComment(commentDto));
        return "redirect:/post/"+commentDto.getPostId();
    }
}
