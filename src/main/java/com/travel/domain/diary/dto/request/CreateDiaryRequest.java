package com.travel.domain.diary.dto.request;

import com.travel.domain.diary.model.Visibility;
import java.time.LocalDate;
import java.util.List;

public record CreateDiaryRequest(
        Long folderId,
        LocalDate date,
        List<String> emotions,
        Visibility visibility
) {}