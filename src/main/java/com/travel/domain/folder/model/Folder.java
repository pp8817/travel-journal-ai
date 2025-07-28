package com.travel.domain.folder.model;

import com.travel.domain.diary.model.Diary;
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
@Table(name = "folders")
public class Folder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries = new ArrayList<>();

    public void addDiary(Diary diary) {
        this.diaries.add(diary);
    }

//    @Column(name = "image_url")
//    private String imageUrl;

//    @Column(name = "user_id", nullable = false)
//    private Long userId; // 사용자 ID (외래 키 연결 여부는 상황에 따라 추가)
}