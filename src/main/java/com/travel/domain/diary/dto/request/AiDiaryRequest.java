package com.travel.domain.diary.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record AiDiaryRequest(
        @JsonProperty("date") String date,
        @JsonProperty("emotions") List<String> emotions,
        @JsonProperty("image") String image
) {}