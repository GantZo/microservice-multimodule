package com.gantz.microservice.videoservice.web;

import com.gantz.microservice.videoservice.domain.VideoInfoCategory;
import com.gantz.microservice.videoservice.dto.VideoInfoCategoryDTO;
import com.gantz.microservice.videoservice.repository.VideoInfoCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/videoInfo/videoInfoCategory")
@RequiredArgsConstructor
public class VideoInfoCategoryResource {

    private final VideoInfoCategoryRepository videoInfoCategoryRepository;

    @GetMapping("/list")
    public Page<VideoInfoCategoryDTO> findAll(Pageable pageable) {
        Page<VideoInfoCategory> all = videoInfoCategoryRepository.findAll(pageable);

        return new PageImpl<>(
                all.stream().map(p -> new VideoInfoCategoryDTO(p.getVideoInfoId(), p.getCategoryId())).toList(),
                all.getPageable(),
                all.getTotalElements()
        );
    }

    @GetMapping
    public List<VideoInfoCategoryDTO> findAllByVideoInfoId(@RequestParam("videoInfoId") Long videoInfoId) {
        return videoInfoCategoryRepository.findAllByVideoInfoId(videoInfoId).stream()
                .map(p -> new VideoInfoCategoryDTO(p.getVideoInfoId(), p.getCategoryId()))
                .sorted(Comparator.comparing(VideoInfoCategoryDTO::getCategoryId))
                .toList();
    }

    @PostMapping("/{videoInfoId}")
    public List<VideoInfoCategoryDTO> update(@PathVariable("videoInfoId") Long videoInfoId, @RequestBody List<VideoInfoCategoryDTO> list) {
        if (list == null) {
            throw new RuntimeException("u must specify category list!");
        }
        List<VideoInfoCategoryDTO> inBase = videoInfoCategoryRepository.findAllByVideoInfoId(videoInfoId).stream()
                .map(p -> new VideoInfoCategoryDTO(p.getVideoInfoId(), p.getCategoryId())).toList();
        List<VideoInfoCategoryDTO> toDelete = inBase.stream().filter(f -> !list.contains(f)).toList();
                toDelete.forEach(dto -> videoInfoCategoryRepository
                        .deleteById(VideoInfoCategory.IdClass.of(dto.getVideoInfoId(), dto.getCategoryId()))
                );
        List<VideoInfoCategoryDTO> created = list.stream()
                .filter(f -> !inBase.contains(f))
                .distinct()
                .map(dto -> new VideoInfoCategory(dto.getVideoInfoId(), dto.getCategoryId()))
                .map(videoInfoCategoryRepository::save)
                .map(p -> new VideoInfoCategoryDTO(p.getVideoInfoId(), p.getCategoryId()))
                .toList();
        inBase.stream()
                .filter(f -> !toDelete.contains(f))
                .forEach(created::add);
        return created;
    }

}
