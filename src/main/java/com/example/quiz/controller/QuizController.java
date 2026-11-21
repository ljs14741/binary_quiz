package com.example.quiz.controller;

import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpStatus;
import com.example.quiz.dto.QuizDTO;
import com.example.quiz.service.QuizService;
import com.example.quiz.service.VisitorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    private final VisitorService visitorService;

    // 소개·문의·개인정보는 메인 한 곳에서 관리함 (영구 이동)
    @GetMapping({"/about", "/contact", "/privacy-policy"})
    public RedirectView legalPage(HttpServletRequest request) {
        RedirectView view = new RedirectView("https://binaryworld.kr" + request.getRequestURI());
        view.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return view;
    }

    @RequestMapping("/")
    public String showQuiz(Model model, HttpServletRequest request, HttpServletResponse response) {
        visitorService.countVisitIfNeeded(request, response, "quiz");
        return "quiz";
    }

    @RequestMapping("/quizStart")
    public String showQuizStart(@RequestParam("category_code") String category_code, Model model) {
        // 퀴즈 데이터를 Service를 통해 불러옴
        List<QuizDTO> quizList = quizService.getQuizByCategory(category_code);

        model.addAttribute("quizList", quizList);
        log.info("퀴즈리스트: " + quizList);

        return "quizStart";
    }

    @RequestMapping("/quizResult")
    public String showQuizResult(@RequestParam("score") int score, Model model) {
        String grade = quizService.getGrade(score);
        String message = quizService.getMessage(score);

        model.addAttribute("score", score);
        model.addAttribute("grade", grade);
        model.addAttribute("message", message);

        return "quizResult";
    }
}
