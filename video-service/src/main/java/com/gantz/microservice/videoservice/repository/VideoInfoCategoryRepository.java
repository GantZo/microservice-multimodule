package com.gantz.microservice.videoservice.repository;

import com.gantz.microservice.videoservice.domain.VideoInfoCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoInfoCategoryRepository extends JpaRepository<VideoInfoCategory, VideoInfoCategory.IdClass> {

    List<VideoInfoCategory> findAllByVideoInfoId(Long videoInfoId);

}
