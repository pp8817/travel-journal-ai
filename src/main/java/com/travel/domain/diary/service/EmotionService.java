package com.travel.domain.diary.service;

import com.travel.domain.diary.model.Emotion;
import com.travel.domain.diary.repository.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmotionService {

    private final EmotionRepository emotionRepository;

    @Transactional
    public List<Emotion> findOrCreateAll(List<String> emotionKeywords) {
        return emotionKeywords.stream()
                .map(this::findOrCreate)
                .collect(Collectors.toList());
    }

    private Emotion findOrCreate(String keyword) {
        return emotionRepository.findByName(keyword)
                .orElseGet(() -> emotionRepository.save(
                        Emotion.builder().name(keyword).build()));
    }
}