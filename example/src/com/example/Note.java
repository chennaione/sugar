package com.example;

import android.content.Context;
import com.orm.SugarRecord;

public class Note extends SugarRecord<Note>{
    private int noteId;
    private String title;
    private String description;
   private String name;
    private Tag tag;

    public Note(Context context){
        super(context);
    }

    public Note(Context context, int noteId, String title, String description, Tag tag) {
        super(context);
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

    public String getDescription() {
        return description;
    }

    public Tag getTag() {
        return tag;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  title + "id: " + id + " - " + tag + " " + tag.getId();

    }
}
