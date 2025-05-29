package com.travel.diary.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}