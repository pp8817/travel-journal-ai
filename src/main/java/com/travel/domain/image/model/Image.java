package com.travel.domain.image.model;

import com.travel.domain.diary.model.Diary;
import com.travel.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "images")
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "image_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;

    private String fileName;   // 원본 파일명
    private String location;   // city, country (GeocodingUtil)
    private String vicinity;   // 장소명 (GooglePlacesUtil.getExactPlaceInfo)

    private String fileUrl;    // 저장된 경로 (S3/CDN 등)

    public void setDiary(Diary diary) {
        this.diary = diary;
    }
}
