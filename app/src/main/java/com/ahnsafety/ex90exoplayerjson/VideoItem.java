package com.ahnsafety.ex90exoplayerjson;

public class VideoItem {

    String title;
    String subTitle;
    String desc;
    String videoUrl;
    String thumb;

    public VideoItem(String title, String subTitle, String desc, String videoUrl, String thumb) {
        this.title = title;
        this.subTitle = subTitle;
        this.desc = desc;
        this.videoUrl = videoUrl;
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
