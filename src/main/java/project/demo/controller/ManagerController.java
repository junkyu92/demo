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
public class ManagerController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/manager")
    public String managerPage() {
        return "manager/manager";
    }

    @GetMapping("/manager/board")
    public String managerBoard(Model model) {
        return "manager/board";
    }

    @GetMapping("/manager/board/list")
    public String managerBoardList(Model model, @PageableDefault(size = 10) Pageable pageable) {
        model.addAttribute("boards", boardService.findForManagerPage(pageable));
        return "manager/ajax/board-list";
    }

    @GetMapping("/manager/boardForm")
    public String boardCreateForm() {
        return "manager/boardCreateForm";
    }
    @GetMapping("/manager/boardForm/{id}")
    public String boardUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardService.findById(id));
        return "manager/boardUpdateForm";
    }

    @PostMapping("/manager/board")
    public String createBoard(@RequestParam("name") String name) {
        boardService.create(name);
        return "redirect:/manager/board";
    }

    @PatchMapping("/manager/board")
    public void changeBoardName(BoardDto boardDto) {
        boardService.changeBoardName(boardDto.getId(), boardDto.getName());
    }

    @DeleteMapping("/board/{id}")
    public @ResponseBody void delete(@PathVariable Long id) {
        boardService.delete(id);
    }

}
