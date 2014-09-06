package com.fastbleep.fastbleepforandroid.models;


/**
 * Created by ianar on 03/09/2014.
 */
public class Article {

    private int id;
    private String title;
    private String content;

    public Article() {

    }

    public Article(int id, String title, String content) {
        this.title = title;
        this.id = id;
        this.content = content;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}