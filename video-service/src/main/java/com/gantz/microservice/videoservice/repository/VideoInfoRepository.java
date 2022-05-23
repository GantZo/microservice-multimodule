package com.gantz.microservice.videoservice.repository;

import com.gantz.microservice.videoservice.domain.VideoInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoInfoRepository extends JpaRepository<VideoInfo, Long> {
}
