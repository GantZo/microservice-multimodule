package com.gantz.microservice.videoservice.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "video_infos")
public class VideoInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @OneToMany(mappedBy = "videoInfo", fetch = FetchType.LAZY)
    private List<VideoInfoCategory> videoInfoCategories;

    public VideoInfo() {
    }

    public VideoInfo(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VideoInfoCategory> getVideoInfoCategories() {
        return videoInfoCategories;
    }

    public void setVideoInfoCategories(List<VideoInfoCategory> videoInfoCategories) {
        this.videoInfoCategories = videoInfoCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        VideoInfo videoInfo = (VideoInfo) o;

        return new EqualsBuilder().append(id, videoInfo.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
