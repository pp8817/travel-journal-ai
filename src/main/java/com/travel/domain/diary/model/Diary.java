package com.travel.domain.diary.model;

import com.travel.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.ThreadContext;

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

    //유저 아이디
    @Column(name = "user_id", nullable = false)
    private Long userId;
    //이미지
    @Column(name = "image_url", nullable = false)
    private String imgUrl;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "travel_date", nullable = false)
    private LocalDate travelDate; // 현재 일기가 몇 일차인지

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
    @Builder.Default
    private List<DiaryEmotion> diaryEmotions = new ArrayList<>();

    public void addEmotion(Emotion emotion) {
        DiaryEmotion link = DiaryEmotion.builder()
                .diary(this)
                .emotion(emotion)
                .build();
        this.diaryEmotions.add(link);
    }

    public String getImageUrl() {
        return this.imgUrl;
    }
}