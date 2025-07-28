package com.travel.domain.diary.model;

import com.travel.domain.folder.model.Folder;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility = Visibility.PRIVATE; // 기본값: private으로 설정, 논의 후 수정 필요

    @ElementCollection
    @CollectionTable(name = "diary_images", joinColumns = @JoinColumn(name = "diary_id"))
    @Column(name = "image_path")
    @Builder.Default
    private List<String> imagePaths = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "diary_hashtags", joinColumns = @JoinColumn(name = "diary_id"))
    @Column(name = "hashtag")
    @Builder.Default
    private List<String> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DiaryEmotion> diaryEmotions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id") // FK 컬럼 이름
    private Folder folder;

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

    public void addAllTags(List<String> hashtags) {
        this.hashtags.addAll(hashtags);
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}