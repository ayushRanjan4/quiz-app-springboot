package com.ayush.quizApp.dao;

import com.ayush.quizApp.model.questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<questions, Integer> {

    List<questions> findByCategory(String category);

    @Query(value = "SELECT * FROM questions q WHERE q.category = ?1 ORDER BY RAND() LIMIT ?2",
            nativeQuery = true)
    List<questions> findRandomQuestionByCategory(String category, int numQ);
}