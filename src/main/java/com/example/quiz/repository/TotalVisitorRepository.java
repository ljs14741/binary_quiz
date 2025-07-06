package com.example.quiz.repository;

import com.example.quiz.entity.TotalVisitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalVisitorRepository extends JpaRepository<TotalVisitor, Long> {
    Optional<TotalVisitor> findByPageName(String pageName);
}