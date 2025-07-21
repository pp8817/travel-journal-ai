package com.travel.domain.diary.service;

import com.travel.domain.diary.dto.request.AiDiaryRequest;
import com.travel.domain.diary.dto.request.CreateDiaryRequest;
import com.travel.domain.diary.dto.response.AiDiaryResponse;
import com.travel.domain.diary.dto.response.DiaryDetailDto;
import com.travel.domain.diary.dto.response.DiaryListDto;
import com.travel.domain.diary.dto.response.DiaryResponse;
import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Emotion;
import com.travel.domain.diary.model.Visibility;
import com.travel.domain.diary.repository.DiaryRepository;
import com.travel.domain.diary.util.DiaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                saved.getId(),
                null // image GPS 데이터 추출 로직 추가 이후 수정
        );
    }

    public DiaryDetailDto getDiaryById(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 일기를 찾을 수 없습니다."));

        return DiaryMapper.toDiaryDetailDto(diary);
    }

    public List<DiaryListDto> getDiaryListByPublic() {
        // 최신순으로 정렬
        List<Diary> diaries = diaryRepository.findAllByVisibilityOrderByCreatedAtDesc(Visibility.PUBLIC);
        return diaries.stream()
                .map(DiaryListDto::from)
                .collect(Collectors.toList());
    }
}