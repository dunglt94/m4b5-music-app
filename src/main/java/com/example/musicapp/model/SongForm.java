package com.example.musicapp.model;

import org.springframework.web.multipart.MultipartFile;

public class SongForm {
    private int id;
    private String title;
    private String singer;
    private String category;
    private MultipartFile file;

    public SongForm() {
    }

    public SongForm(int id, String title, String singer, String category, MultipartFile file) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.category = category;
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
