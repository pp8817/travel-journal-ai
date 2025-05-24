package com.example.demo.service;

import com.example.demo.dto.DiaryCreateRequest;
import com.example.demo.dto.DiaryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DiaryService {

    public DiaryResponse createDiary(DiaryCreateRequest request, List<MultipartFile> images) {
        String diaryId = UUID.randomUUID().toString();
        String title = request.getTravelDate() + ", " + request.getTravelLocation();
        String content = "여행지 " + request.getTravelLocation() + "에서 느낀 감정은 " + request.getFeeling() + "입니다."; // 예시 내용

        List<String> imageUrls = images != null
                ? IntStream.range(0, images.size())
                .mapToObj(i -> "https://cdn.cchaksa.com/diary/" + diaryId + "/img" + (i + 1) + ".jpg")
                .collect(Collectors.toList())
                : List.of();

        return new DiaryResponse(diaryId, title, content, OffsetDateTime.now(ZoneOffset.UTC), imageUrls);
    }
}
