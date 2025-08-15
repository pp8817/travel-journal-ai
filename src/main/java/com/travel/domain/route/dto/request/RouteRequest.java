package com.travel.domain.route.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class RouteRequest {
    private LocalDate date;
    private String transport;
    private Double latitude;
    private Double longitude;
    private List<MultipartFile> images; // 이미지 업로드 파일
}
