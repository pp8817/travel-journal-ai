package com.travel.domain.diary.service;

import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Emotion;
import com.travel.domain.diary.repository.DiaryRepository;
import com.travel.domain.diary.repository.EmotionRepository;
import com.travel.domain.diary.util.DiaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.travel.domain.diary.dto.DiaryDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final EmotionRepository emotionRepository;
    private final AiClient aiClient;

    @Transactional
    public DiaryResponse createDiary(CreateDiaryRequest request) {
        // 1. AI 요청 DTO 생성
        AiDiaryRequest aiRequest = DiaryMapper.toAiDiaryRequest(request);
        log.debug("📤 AI 요청 DTO: {}", aiRequest);

        // 2. AI 서버 호출
        AiDiaryResponse aiResponse = aiClient.generate(aiRequest);
        log.debug("📥 AI 응답: {}", aiResponse.diary());

        // 3. Diary 엔티티 생성
        Diary diary = DiaryMapper.toDiaryEntity(request, aiResponse.diary());

        // 4. 감정 키워드 연결
        for (String emotionKeyword : request.emotions()) {
            Emotion emotion = emotionRepository.findByName(emotionKeyword)
                    .orElseGet(() -> emotionRepository.save(
                            Emotion.builder().name(emotionKeyword).build()));
            diary.addEmotion(emotion);
        }

        // 5. 저장
        Diary saved = diaryRepository.save(diary);

        // 6. 응답 생성
        return new DiaryResponse(
                saved.getCreatedAt(),
                200,
                "일기가 성공적으로 생성되었습니다.",
                saved.getContent(),
                saved.getId()
        );
    }
}