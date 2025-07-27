package com.travel.domain.folder.service;

import com.travel.domain.folder.dto.FolderDetailResponse;
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

//        List<String> tags = folder.getTags().stream()
//                .map(Tag::getName)
//                .toList();

        List<FolderDetailResponse.DiarySummary> diarySummaries = folder.getDiaries().stream()
                .map(diary -> new FolderDetailResponse.DiarySummary(
                        diary.getId(),
                        diary.getTitle(),
                        diary.getTravelDate(),
                        diary.getImagePaths().get(0)
                ))
                .toList();

        return new FolderDetailResponse(
                folder.getTitle(),
                folder.getStartDate(),
                folder.getEndDate(),
//                tags,
                diarySummaries
        );
    }
}