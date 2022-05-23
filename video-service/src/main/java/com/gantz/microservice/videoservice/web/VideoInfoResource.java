package com.gantz.microservice.videoservice.web;

import com.gantz.microservice.videoservice.domain.VideoInfo;
import com.gantz.microservice.videoservice.dto.VideoInfoDTO;
import com.gantz.microservice.videoservice.repository.VideoInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/videoInfo")
@RequiredArgsConstructor
public class VideoInfoResource {

    private final VideoInfoRepository videoInfoRepository;

    @GetMapping("/list")
    public Page<VideoInfoDTO> findAll(Pageable pageable) {
        Page<VideoInfo> all = videoInfoRepository.findAll(pageable);

        return new PageImpl<>(
                all.stream().map(p -> new VideoInfoDTO(p.getId(), p.getTitle())).collect(Collectors.toList()),
                all.getPageable(),
                all.getTotalElements()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoInfoDTO> findById(@PathVariable("id") Long id) {
        return videoInfoRepository.findById(id)
                .map(p -> new VideoInfoDTO(p.getId(), p.getTitle()))
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("id not found!"));
    }

    @PutMapping
    public ResponseEntity<VideoInfoDTO> create(@RequestBody VideoInfoDTO videoInfoDTO) {
        return Optional.ofNullable(videoInfoDTO)
                .filter(f -> Objects.isNull(f.getId()))
                .map(p -> new VideoInfo(p.getTitle()))
                .map(videoInfoRepository::save)
                .map(p -> new VideoInfoDTO(p.getId(), p.getTitle()))
                .map(p -> new ResponseEntity<>(p, HttpStatus.CREATED))
                .orElseThrow(() -> new RuntimeException("id must be not null"));
    }

    @PostMapping
    public ResponseEntity<VideoInfoDTO> update(@RequestBody VideoInfoDTO videoInfoDTO) {
        return Optional.ofNullable(videoInfoDTO)
                .filter(f -> Objects.nonNull(f.getId()))
                .flatMap(dto -> videoInfoRepository.findById(dto.getId())
                        .map(videoInfo -> {
                            videoInfo.setTitle(dto.getTitle());
                            return videoInfoRepository.save(videoInfo);
                        })
                )
                .map(p -> new VideoInfoDTO(p.getId(), p.getTitle()))
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseThrow(() -> new RuntimeException("there is no videoInfo with id"));
    }

}
