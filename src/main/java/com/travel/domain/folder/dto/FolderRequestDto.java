package com.travel.domain.folder.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FolderRequestDto {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String country;
}