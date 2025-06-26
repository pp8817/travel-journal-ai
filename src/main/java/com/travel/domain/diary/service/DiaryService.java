package com.travel.domain.diary.service;

import com.travel.domain.diary.dto.request.AiDiaryRequest;
import com.travel.domain.diary.dto.request.CreateDiaryRequest;
import com.travel.domain.diary.dto.response.AiDiaryResponse;
import com.travel.domain.diary.dto.response.DiaryResponse;
import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Emotion;
import com.travel.domain.diary.repository.DiaryRepository;
import com.travel.domain.diary.util.DiaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public DiaryResponse getDiaryById(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 일기를 찾을 수 없습니다."));

        return new DiaryResponse(
                diary.getCreatedAt(),         // createdAt
                200,                          // resultCode (예시: 성공 코드)
                "일기 조회 성공",             // message
                diary.getContent(),           // content
                diary.getId()                 // diaryId
        );
    }
}