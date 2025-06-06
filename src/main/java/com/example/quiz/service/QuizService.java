package com.example.quiz.service;

import com.example.quiz.dto.QuizDTO;
import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.QuizOptions;
import com.example.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public List<QuizDTO> getQuizByCategory(String categoryCode) {
        // 선택한 카테고리와 난이도에 맞는 퀴즈 리스트를 반환
        List<Quiz> quizzes = quizRepository.findByCategoryCode(categoryCode);

        log.info("가나: " + categoryCode);

        quizzes.forEach(quiz -> {
            log.info("Quiz: " + quiz);
            quiz.getQuizOptions().forEach(option -> log.info("Option: " + option));
        });
        log.info("Retrieved quizzes size: " + quizzes.size());


        // Quiz 엔티티와 관련된 QuizOptions를 함께 조회하여 QuizDTO로 변환
        return quizzes.stream()
                .map(quiz -> {
                    // QuizOptions에서 선택지를 추출하여 리스트로 변환
                    List<String> options = quiz.getQuizOptions()
                            .stream()
                            .map(QuizOptions::getQuizOption) // QuizOptions에서 선택지 추출
                            .collect(Collectors.toList());

                    return new QuizDTO(quiz.getQuestion(), options, quiz.getAnswerIndex());
                })
                .collect(Collectors.toList());
    }

    public String getGrade(int score) {
        if (score >= 9) {
            return "천재 중의 천재";
        } else if (score >= 7) {
            return "똑똑이";
        } else if (score >= 4) {
            return "좀 치는데?";
        } else if (score >= 2) {
            return "초보 탈출 중";
        } else {
            return "바보 멍청이";
        }
    }

    public String getMessage(int score) {
        if (score >= 9) {
            return "우주적 상식을 다 씹어먹는 천재라거덩여! 모두가 당신을 무서워하거덩여! 당신은 합격입니다!";
        } else if (score >= 7) {
            return "거의 다 맞혔다거덩여! 머리 좀 쓰는 똑똑이라거덩여! 하지만... 당신은 보류입니다.";
        } else if (score >= 4) {
            return "오~ 감 잡아가고 있다거덩여! 조금만 더 하면 다 맞힐 듯하다거덩여! 아쉽지만, 당신은 보류입니다.";
        } else if (score >= 2) {
            return "조금 더 힘내라거덩여! 이제 초보 탈출할 때라거덩여! 하지만, 당신은 보류입니다.";
        } else {
            return "음... 상식이 살짝 모자르다거덩여? 그래도 웃음 줘서 고맙다거덩여! 당신은 보류입니다.";
        }
    }
}