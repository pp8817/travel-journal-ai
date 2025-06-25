package com.travel.domain.diary.service;

import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Emotion;
import com.travel.domain.diary.repository.DiaryRepository;
import com.travel.domain.diary.util.DiaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.travel.domain.diary.dto.DiaryDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final EmotionService emotionService;
    private final AiClient aiClient;

    @Transactional
    public DiaryResponse createDiary(CreateDiaryRequest request) {
        AiDiaryRequest aiRequest = DiaryMapper.toAiDiaryRequest(request);
        log.debug("📤 AI 요청 DTO: {}", aiRequest);

        AiDiaryResponse aiResponse = aiClient.generate(aiRequest);
        log.debug("📥 AI 응답: {}", aiResponse.diary());

        Diary diary = DiaryMapper.toDiaryEntity(request, aiResponse.diary());

        List<Emotion> emotions = emotionService.findOrCreateAll(request.emotions());
        emotions.forEach(diary::addEmotion);

        Diary saved = diaryRepository.save(diary);

        return new DiaryResponse(
                saved.getCreatedAt(),
                200,
                "일기가 성공적으로 생성되었습니다.",
                saved.getContent(),
                saved.getId()
        );
    }
}