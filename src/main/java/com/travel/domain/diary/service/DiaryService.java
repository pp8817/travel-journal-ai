package com.travel.domain.diary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Emotion;
import com.travel.domain.diary.repository.DiaryRepository;
import com.travel.domain.diary.repository.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.travel.domain.diary.dto.DiaryDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final EmotionRepository emotionRepository;
    private final AiClient aiClient;

    @Transactional
    public DiaryResponse createDiary(CreateDiaryRequest request) {

        // 1. AI 요청용 DTO 만들기
        AiDiaryRequest aiRequest = AiDiaryRequest.builder()
                .date(formatDate(request.date())) // "yyyy년 MM월 dd일"
                .location(request.location())
                .emotions(request.emotions())
                .weather(request.weather())
                .companion(request.companion())
                .image(request.image())
                .build();

        // ✅ 여기에 로그 출력 추가
        try {
            System.out.println("📤 AI 요청 JSON: " + new ObjectMapper().writeValueAsString(aiRequest));
        } catch (Exception e) {
            e.printStackTrace(); // JSON 직렬화 실패 시 로그 확인용
        }

        // 2. AI 서버 호출
        AiDiaryResponse aiResponse = aiClient.generate(aiRequest);

        System.out.println("📤 AI 응답: " + aiResponse.diary());

        // 3. Diary 생성 및 감정 연관 연결
        Diary diary = Diary.builder()
                .title("제목 없음") // 후처리로 바꾸기 가능
                .content(aiResponse.diary())
                .travelDate(request.date())
                .location(request.location())
                .weather(request.weather())
                .companion(request.companion())
                .visibility(request.visibility())
                .build();

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

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }
}