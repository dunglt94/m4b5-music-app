package com.example.musicapp.model;

import javax.persistence.*;

@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String singer;
    private String category;
    private String file;

    public Song() {
    }

    public Song(String title, String singer, String category, String file) {
        this.title = title;
        this.singer = singer;
        this.category = category;
        this.file = file;
    }

    public Song(int id, String title, String singer, String category, String file) {
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
