package com.travel.domain.diary.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "diary_emotions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DiaryEmotion {

    @Id
    @GeneratedValue
    @Column(name = "diary_emotion_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id", nullable = false)
    private Emotion emotion;

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }
}