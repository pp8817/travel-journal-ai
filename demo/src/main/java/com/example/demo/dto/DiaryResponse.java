package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
public class DiaryResponse {

    private String diaryId;
    private String title;
    private String content;
    private OffsetDateTime createdAt;
    private List<String> images;

    public DiaryResponse(String diaryId, String title, String content, OffsetDateTime createdAt, List<String> images) {
        this.diaryId = diaryId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.images = images;
    }

}
