package com.travel.domain.diary.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record AiDiaryRequest(
        @JsonProperty("date") String date,
        @JsonProperty("location") String location,
        @JsonProperty("emotions") List<String> emotions,
        @JsonProperty("weather") String weather,
        @JsonProperty("companion") String companion,
        @JsonProperty("image") String image
) {}