package com.travel.domain.folder.service;

import com.travel.domain.diary.model.Diary;
import com.travel.domain.folder.dto.FolderDetailResponse;
import com.travel.domain.folder.dto.FolderListResponse;
import com.travel.domain.folder.dto.FolderRequestDto;
import com.travel.domain.folder.model.Folder;
import com.travel.domain.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    @Transactional
    public void createFolder(FolderRequestDto dto) {
        Folder folder = Folder.builder()
                .title(dto.getTitle())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();

        folderRepository.save(folder);
    }

    public FolderDetailResponse getFolderDetail(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 폴더를 찾을 수 없습니다."));

        List<FolderDetailResponse.DiarySummary> diarySummaries = folder.getDiaries().stream()
                .map(diary -> new FolderDetailResponse.DiarySummary(
                        diary.getId(),
                        diary.getTitle(),
                        diary.getContent(),
                        diary.getTravelDate(),
                        diary.getImagePaths().get(0),
                        diary.getHashtags()
                ))
                .toList();

        return new FolderDetailResponse(
                folder.getTitle(),
                folder.getStartDate(),
                folder.getEndDate(),
                diarySummaries
        );
    }

    public List<FolderListResponse> getFolderList() {
        List<Folder> folders = folderRepository.findAll();

        return folders.stream()
                .map(folder -> {
                    // 첫 번째 일기 (있다면)
                    Diary firstDiary = folder.getDiaries().stream()
                            .filter(diary -> diary.getImagePaths() != null && !diary.getImagePaths().isEmpty())
                            .findFirst()
                            .orElse(null);

                    String image = (firstDiary != null && !firstDiary.getImagePaths().isEmpty())
                            ? firstDiary.getImagePaths().get(0)
                            : null;

                    List<String> tags = (firstDiary != null && firstDiary.getHashtags() != null)
                            ? firstDiary.getHashtags()
                            : List.of();

                    return new FolderListResponse(
                            folder.getId(),
                            folder.getTitle(),
                            folder.getStartDate(),
                            folder.getEndDate(),
                            image,
                            tags
                    );
                })
                .toList();
    }
}