package com.example.quiz.controller;

import com.example.quiz.entity.DailyVisitor;
import com.example.quiz.entity.TotalVisitor;
import com.example.quiz.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/visit")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping("/today")
    public List<DailyVisitor> getTodayVisitors() {
        return visitorService.getTodayAllVisitors();
    }

    @GetMapping("/total")
    public List<TotalVisitor> getTotalVisitors() {
        return visitorService.getAllTotalVisitors();
    }
}