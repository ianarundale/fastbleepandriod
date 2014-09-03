package com.fastbleep.fastbleepnotes;


/**
 * Created by ianar on 03/09/2014.
 */
public class RowItem  {

    private int id;
    private String title;
    private String content;

    public RowItem(int id, String title, String content) {
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

}