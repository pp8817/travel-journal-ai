package com.travel.domain.diary.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emotions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Emotion {

    @Id
    @GeneratedValue
    @Column(name = "emotion_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}