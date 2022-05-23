package com.gantz.microservice.videoservice.web;

import com.gantz.microservice.videoservice.domain.VideoInfoCategory;
import com.gantz.microservice.videoservice.dto.VideoInfoCategoryDTO;
import com.gantz.microservice.videoservice.repository.VideoInfoCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/videoInfo/videoInfoCategory")
@RequiredArgsConstructor
public class VideoInfoCategoryResource {

    private final VideoInfoCategoryRepository videoInfoCategoryRepository;

    @GetMapping("/list")
    public Page<VideoInfoCategoryDTO> findAll(Pageable pageable) {
        Page<VideoInfoCategory> all = videoInfoCategoryRepository.findAll(pageable);

        return new PageImpl<>(
                all.stream().map(p -> new VideoInfoCategoryDTO(p.getVideoInfoId(), p.getCategoryId())).collect(Collectors.toList()),
                all.getPageable(),
                all.getTotalElements()
        );
    }

    @GetMapping
    public List<VideoInfoCategoryDTO> findAllByVideoInfoId(@RequestParam("videoInfoId") Long videoInfoId) {
        return videoInfoCategoryRepository.findAllByVideoInfoId(videoInfoId).stream()
                .map(p -> new VideoInfoCategoryDTO(p.getVideoInfoId(), p.getCategoryId()))
                .sorted(Comparator.comparing(VideoInfoCategoryDTO::getCategoryId))
                .collect(Collectors.toList());
    }

}
