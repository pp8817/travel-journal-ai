package com.travel.domain.diary.repository;

import com.travel.domain.diary.model.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Long> {

    Optional<Emotion> findByName(String name);
}
