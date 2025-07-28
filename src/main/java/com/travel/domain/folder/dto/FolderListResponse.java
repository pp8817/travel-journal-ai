package com.travel.domain.folder.dto;

import java.time.LocalDate;
import java.util.List;

public record FolderListResponse(
        Long folderId,
        String title,
        LocalDate startDate,
        LocalDate endDate,
        String image, // 대표 이미지 URL
        List<String>tag
) {}