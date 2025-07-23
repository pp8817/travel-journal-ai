package com.travel.domain.diary.model;

import com.travel.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "diaries")
public class Diary extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate; // 현재 일기가 몇 일차인지

    @Column(name = "location", nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility = Visibility.PRIVATE; // 기본값: private으로 설정, 논의 후 수정 필요

    @Column(name = "image_paths", nullable = false)
    private List<String> imagePaths = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DiaryEmotion> diaryEmotions = new ArrayList<>();

    /* Using Method */
    public void addEmotion(Emotion emotion) {
        DiaryEmotion link = DiaryEmotion.builder()
                .diary(this)
                .emotion(emotion)
                .build();
        this.diaryEmotions.add(link);
    }

    public void addAllImage(List<String> imagePaths) {
        this.imagePaths.addAll(imagePaths);
    }
}