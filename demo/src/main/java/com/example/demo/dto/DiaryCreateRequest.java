package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class DiaryCreateRequest {
    @NotBlank
    private String travelDate;

    @NotBlank
    private String travelLocation;

    @NotBlank
    private String feeling;

    private String companion;
    private String weather;

}
