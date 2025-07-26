package com.travel.domain.diary.service;

import com.travel.domain.diary.dto.request.AiDiaryRequest;
import com.travel.domain.diary.dto.request.CreateDiaryRequest;
import com.travel.domain.diary.dto.response.AiDiaryResponse;
import com.travel.domain.diary.dto.response.DiaryDetailDto;
import com.travel.domain.diary.dto.response.DiaryListDto;
import com.travel.domain.diary.dto.response.DiaryResponse;
import com.travel.domain.diary.dto.response.*;
import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Emotion;
import com.travel.domain.diary.model.Visibility;
import com.travel.domain.diary.repository.DiaryRepository;
import com.travel.domain.diary.util.DiaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

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
    private final PhotoMetadataService metadataService;



    /**
     * 추가 필요 작업
     * - 여러 장의 이미지를 입력 받은 경우 첫 장만 AI 서버로 전송
     * - 각 image의 위치 정보와 시간 정보 추출 로직
     * - 입력 받은 MutipartFile 형식의 이미지를 임의 경로에 저장한 후 imagePath 생성 후 Diary 엔티티에 저장
     */
    @Transactional
    public DiaryResponse createDiary(CreateDiaryRequest request, List<MultipartFile> images) throws IOException {
        AiDiaryRequest aiRequest = DiaryMapper.toAiDiaryRequest(request);
        log.debug("📤 AI 요청 DTO: {}", aiRequest);

        AiDiaryResponse aiResponse = aiClient.generate(aiRequest);
        log.debug("📥 AI 응답: {}", aiResponse.diary());

        Diary diary = DiaryMapper.toDiaryEntity(request, aiResponse.diary());

        List<Emotion> emotions = emotionService.findOrCreateAll(request.emotions());
        emotions.forEach(diary::addEmotion);

        Diary saved = diaryRepository.save(diary);

        // 이미지 메타데이터 추출

        List<PinResponse> pins;
        pins = metadataService.extractPins(images);
        return new DiaryResponse(saved.getId(), pins);
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