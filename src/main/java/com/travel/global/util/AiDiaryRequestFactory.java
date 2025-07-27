package com.travel.global.util;

import com.travel.domain.diary.dto.request.AiDiaryRequest;
import com.travel.domain.diary.dto.request.CreateDiaryRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class AiDiaryRequestFactory {

    public AiDiaryRequest create(CreateDiaryRequest request, List<String> base64Image) {
        return AiDiaryRequest.builder()
                .date(formatDate(request.date()))
                .emotions(request.emotions())
                .image(base64Image)
                .build();
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        return date.format(FORMATTER);
    }
}