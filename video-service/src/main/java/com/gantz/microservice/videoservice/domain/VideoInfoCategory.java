package com.gantz.microservice.videoservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "video_infos_categories")
@IdClass(VideoInfoCategory.IdClass.class)
public class VideoInfoCategory {

    @Id
    @Column(name = "video_info_id")
    private Long videoInfoId;
    @Id
    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_info_id", updatable = false, insertable = false)
    private VideoInfo videoInfo;

    public IdClass idClass() {
        return IdClass.of(videoInfoId, categoryId);
    }

    public VideoInfoCategory() {
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdClass implements Serializable {
        @Column(name = "video_info_id")
        private Long videoInfoId;
        @Column(name = "category_id")
        private Integer categoryId;

        public static IdClass of(Long videoInfoId, Integer categoryId) {
            return new IdClass(videoInfoId, categoryId);
        }
    }

    public Long getVideoInfoId() {
        return videoInfoId;
    }

    public void setVideoInfoId(Long videoInfoId) {
        this.videoInfoId = videoInfoId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VideoInfoCategory that = (VideoInfoCategory) o;

        return new EqualsBuilder().append(videoInfoId, that.videoInfoId).append(categoryId, that.categoryId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(videoInfoId).append(categoryId).toHashCode();
    }
}
