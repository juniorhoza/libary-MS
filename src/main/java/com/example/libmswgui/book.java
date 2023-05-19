package com.example.libmswgui;

import java.util.UUID;

public class book {
    private UUID id= UUID.randomUUID();
    private String title= " ";
    private String author= "";
    private int num_of_copies=0;

    public book(String title, String author, int num_of_copies) {
        this.title = title;
        this.author = author;
        this.num_of_copies = num_of_copies;
    }

    public book(UUID id, String title, String author, int num_of_copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.num_of_copies = num_of_copies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNum_of_copies() {
        return num_of_copies;
    }

    public void setNum_of_copies(int num_of_copies) {
        this.num_of_copies = num_of_copies;
    }

    public String getId() {
        return id.toString();
    }

    @Override
    public String toString() {
        return "book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", num_of_copies=" + num_of_copies +
                '}';
    }
}
