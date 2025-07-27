package com.travel.domain.folder.dto;

import java.time.LocalDate;

public record FolderListResponse(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        String image // 대표 이미지 URL
//        List<String> tag
) {}