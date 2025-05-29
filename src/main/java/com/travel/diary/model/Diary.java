package com.travel.diary.model;

import com.travel.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "weather")
    private String weather;

    @Column(name = "companion")
    private String companion;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility = Visibility.PRIVATE; // 기본값: private으로 설정, 논의 후 수정 필요

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryEmotion> diaryEmotions = new ArrayList<>();

    public void addEmotion(Emotion emotion) {
        DiaryEmotion link = DiaryEmotion.builder()
                .diary(this)
                .emotion(emotion)
                .build();
        this.diaryEmotions.add(link);
    }

}