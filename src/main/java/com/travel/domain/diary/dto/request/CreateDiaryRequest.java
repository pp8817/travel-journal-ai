package com.travel.domain.diary.dto.request;

import com.travel.domain.diary.model.Visibility;
import java.time.LocalDate;
import java.util.List;

public record CreateDiaryRequest(
        LocalDate date,
        String location,
        String weather,
        String companion,
        List<String> emotions,
        Visibility visibility,
        String image
) {}