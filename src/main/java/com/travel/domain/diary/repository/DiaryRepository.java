package com.travel.domain.diary.repository;

import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByVisibilityOrderByCreatedAtDesc(Visibility visibility);
}
