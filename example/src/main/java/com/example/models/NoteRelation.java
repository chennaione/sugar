package com.example.models;

import android.content.Context;

import com.orm.dsl.Table;

@Table(name = "note_relation")
public class NoteRelation {
    String name;
    int noteId;

    public NoteRelation() {
    }

    public NoteRelation(Context context, String name, int noteId) {
        this.name = name;
        this.noteId = noteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }
}
