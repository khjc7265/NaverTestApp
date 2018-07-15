package com.naver.hyeonjung.navertestapp.vo;


public class Image {

    //검색 결과 이미지의 제목을 나타낸다.
    private String title;

    //검색 결과 이미지의 하이퍼텍스트 link를 나타낸다.
    private String link;

    //검색 결과 이미지의 썸네일 link를 나타낸다.
    private String thumbnail;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
