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
import com.travel.domain.image.dto.ImageMetaData;
import com.travel.domain.image.dto.PlaceInfo;
import com.travel.domain.image.model.Image;
import com.travel.domain.image.repository.ImageRepository;
import com.travel.global.util.AiDiaryRequestFactory;
import com.travel.global.util.GooglePlacesUtil;
import com.travel.global.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final FolderRepository folderRepository;
    private final ImageRepository imageRepository;
    private final EmotionService emotionService;
    private final AiClient aiClient;
    private final ImageUtil imageUtil;
    private final AiDiaryRequestFactory aiDiaryRequestFactory;
    private final GooglePlacesUtil googlePlacesUtil;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public DiaryResponse createDiary(CreateDiaryRequest request, List<MultipartFile> images) {
        try {
            // 1. 이미지 저장 및 메타데이터 추출
            List<String> savedPaths = imageUtil.saveImages(images, uploadDir);
            List<ImageMetaData> rawPins = imageUtil.extractMetadata(images);

            // 2. Diary 엔티티 생성
            List<String> imagesToBase64 = imageUtil.encodeImagesToBase64(images);
            AiDiaryRequest aiRequest = aiDiaryRequestFactory.create(request, imagesToBase64);
            AiDiaryResponse aiResponse = aiClient.generate(aiRequest);
            HashtagResponse hashtagResponse = aiClient.generateHashtags(aiRequest);

            Diary diary = DiaryMapper.toDiaryEntity(request, aiResponse, savedPaths, hashtagResponse.hashtags());
            List<Emotion> emotions = emotionService.findOrCreateAll(request.emotions());
            emotions.forEach(diary::addEmotion);

            Folder folder = folderRepository.findById(request.folderId())
                    .orElseThrow(() -> new RuntimeException("해당 폴더를 찾을 수 없습니다."));
            folder.addDiary(diary);
            diary.setFolder(folder);

            Diary savedDiary = diaryRepository.save(diary);

            // 3. 이미지 엔티티 생성 및 저장
            rawPins.stream()
                    .sorted(Comparator.comparing(ImageMetaData::timestamp))
                    .map(meta -> {
                        PlaceInfo placeInfo = googlePlacesUtil.getExactPlaceInfo(meta.latitude(), meta.longitude());

                        Image imageEntity = Image.builder()
                                .diary(savedDiary)
                                .latitude(meta.latitude())
                                .longitude(meta.longitude())
                                .timestamp(meta.timestamp())
                                .fileName(meta.fileName())
                                .fileUrl(findFileUrl(savedPaths, meta.fileName())) // 파일명으로 URL 매핑
                                .location(placeInfo.name())
                                .vicinity(placeInfo.vicinity())
                                .build();

                        imageRepository.save(imageEntity); // DB 저장
                        savedDiary.addImage(imageEntity);

                        return new PinResponse(
                                meta.latitude(),
                                meta.longitude(),
                                meta.timestamp(),
                                meta.fileName(),
                                placeInfo.name(),
                                placeInfo.vicinity()
                        );
                    })
                    .collect(Collectors.toList());

            // 4. 응답 반환
            return new DiaryResponse(savedDiary.getId());

        } catch (Exception e) {
            log.error("❌ 일기 생성 중 예외 발생", e);
            throw e;
        }
    }

    // 저장된 파일 경로 찾는 간단한 유틸
    private String findFileUrl(List<String> savedPaths, String fileName) {
        return savedPaths.stream()
                .filter(path -> path.contains(fileName))
                .findFirst()
                .orElse(null);
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