package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.demo.constant.Provider;
import project.demo.constant.Role;
import project.demo.dto.BoardDto;
import project.demo.dto.searchcondition.MemberSearchCondition;
import project.demo.service.BoardService;
import project.demo.service.MemberService;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/admin";
    }

    @GetMapping("/admin/member")
    public String adminMember(Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("providers", Provider.values());
        return "admin/member";
    }
    @GetMapping("/admin/member/list")
    public String adminMemberList(Model model, MemberSearchCondition memberSearchCondition, @PageableDefault(size = 15) Pageable pageable) {
        model.addAttribute("members", memberService.findForAdminPage(memberSearchCondition, pageable));
        model.addAttribute("roles", Role.values());
        return "admin/ajax/member-list";
    }

    @PatchMapping("/member/role/{id}")
    public @ResponseBody void roleChange(@PathVariable("id") Long id, @RequestParam("role") Role role){
        memberService.roleChange(id, role);
    }
}
