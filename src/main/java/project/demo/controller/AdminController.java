package project.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.demo.service.BoardService;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final BoardService boardService;

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/admin";
    }

    @GetMapping("/admin/board")
    public String adminBoard(Model model) {
        model.addAttribute("boards", boardService.findAll());
        return "admin/board";
    }

    @GetMapping("/admin/boardForm")
    public String boardForm(Model model) {
        return "admin/boardForm";
    }

    @PostMapping("/board")
    public String createBoard(@RequestParam("name") String name) {
        boardService.create(name);
        return "redirect:/admin/board";
    }
}
