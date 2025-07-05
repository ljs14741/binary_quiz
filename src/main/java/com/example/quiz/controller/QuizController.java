package com.example.quiz.controller;

import com.example.quiz.dto.QuizDTO;
import com.example.quiz.service.QuizService;
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

    @GetMapping("/about")
    public String about() {
        return "common/about";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy() {
        return "common/privacy-policy";
    }

    @GetMapping("/contact")
    public String contact() {
        return "common/contact";
    }

    @RequestMapping("/")
    public String showQuiz(Model model) {
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
