package com.example.models;

import com.orm.dsl.Column;
import com.orm.dsl.Table;

@Table(name = "Note")
public class Note {

    private long id;

    @Column(name = "noteId", unique = true, notNull = true)
    private int noteId;

    private String title;
    private String description;
   private String name;
    private Integer noteNumber = 2;
    private Float tagNumber = 4.0f;
    private float tagNo = 5.0f;
    private Boolean isTag = true;
    private boolean isTagged = false;
    private Double noteTagNo = 40.5;
    private double noteTagNumber = 24.4;
    private Tag tag;

    public Note(){
    }

    public Note(int noteId, String title, String description, Tag tag) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
        this.tag = tag;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return title + "id: " + noteId;

    }
}
