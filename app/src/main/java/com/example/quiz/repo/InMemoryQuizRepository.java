package com.example.quiz.repo;

import com.example.quiz.model.Quiz;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryQuizRepository {
   private final Map<String, Quiz> store = new ConcurrentHashMap<>();

   public void save(Quiz q) { store.put(q.getId(), q); }
    public Quiz findById(String id) { return store.get(id); }
    public Collection<Quiz> findAll() { return store.values(); }
}
