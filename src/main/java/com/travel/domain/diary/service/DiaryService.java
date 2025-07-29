package com.travel.domain.diary.service;

import com.travel.domain.diary.dto.request.AiDiaryRequest;
import com.travel.domain.diary.dto.request.CreateDiaryRequest;
import com.travel.domain.diary.dto.response.*;
import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Emotion;
import com.travel.domain.diary.model.Visibility;
import com.travel.domain.diary.repository.DiaryRepository;
import com.travel.domain.diary.util.DiaryMapper;
import com.travel.domain.folder.model.Folder;
import com.travel.domain.folder.repository.FolderRepository;
import com.travel.global.util.AiDiaryRequestFactory;
import com.travel.global.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final FolderRepository folderRepository;
    private final EmotionService emotionService;
    private final AiClient aiClient;
    private final ImageUtil imageUtil;
    private final AiDiaryRequestFactory aiDiaryRequestFactory;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 추가 필요 작업
     * - 여러 장의 이미지를 입력 받은 경우 첫 장만 AI 서버로 전송
     * - 각 image의 위치 정보와 시간 정보 추출 로직
     * - 입력 받은 MutipartFile 형식의 이미지를 임의 경로에 저장한 후 imagePath 생성 후 Diary 엔티티에 저장
     */
    @Transactional
    public DiaryResponse createDiary(CreateDiaryRequest request, List<MultipartFile> images) {
        try {
            // 1. 이미지 저장 및 메타데이터 추출
            List<String> savedPaths = imageUtil.saveImages(images, uploadDir);
            List<PinResponse> pinResponses = imageUtil.extractMetadata(images);

            // 2. AI 요청 생성 및 호출
            List<String> imagesToBase64 = imageUtil.encodeImagesToBase64(images);
            AiDiaryRequest aiRequest = aiDiaryRequestFactory.create(request, imagesToBase64);

            AiDiaryResponse aiResponse = aiClient.generate(aiRequest); // 일기 본문
            HashtagResponse hashtagResponse = aiClient.generateHashtags(aiRequest); // 해시태그
            log.debug("📥 AI 일기: {}", aiResponse.diary());
            log.debug("🏷️ AI 해시태그: {}", hashtagResponse.hashtags());

            // 3. 일기 저장
            Diary diary = DiaryMapper.toDiaryEntity(request, aiResponse.diary(), savedPaths, hashtagResponse.hashtags());
            List<Emotion> emotions = emotionService.findOrCreateAll(request.emotions());
            emotions.forEach(diary::addEmotion);
            Diary saved = diaryRepository.save(diary);

            Folder folder = folderRepository.findById(request.folderId())
                    .orElseThrow(() -> new RuntimeException("해당 폴더를 찾을 수 없습니다."));

            folder.addDiary(diary);
            diary.setFolder(folder);

            // 4. 응답 반환
            return new DiaryResponse(saved.getId(), pinResponses);

        } catch (Exception e) {
            log.error("❌ 일기 생성 중 예외 발생", e);
            throw e;
        }
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