package com.example.bitcoin.quiz.controller;

import com.example.bitcoin.quiz.dto.QuizDTO;
import com.example.bitcoin.quiz.service.QuizService;
import com.example.bitcoin.service.VisitorService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    private final VisitorService visitorService;

    @RequestMapping("/quiz")
    public String showQuiz(Model model) {
        visitorService.incrementVisitorCount("quiz");
        return "quiz/quiz";
    }

    @RequestMapping("/quizStart")
    public String showQuizStart(@RequestParam("category_code") String category_code, Model model) {
        // 퀴즈 데이터를 Service를 통해 불러옴
        List<QuizDTO> quizList = quizService.getQuizByCategory(category_code);

        model.addAttribute("quizList", quizList);
        log.info("퀴즈리스트: " + quizList);

        return "quiz/quizStart";
    }

    @RequestMapping("/quizResult")
    public String showQuizResult(@RequestParam("score") int score, Model model) {
        String grade = quizService.getGrade(score);
        String message = quizService.getMessage(score);

        model.addAttribute("score", score);
        model.addAttribute("grade", grade);
        model.addAttribute("message", message);

        return "quiz/quizResult";
    }
}
