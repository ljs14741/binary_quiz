package com.example.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {
    private String question;
    private List<String> options;
    private Integer answerIndex;
}