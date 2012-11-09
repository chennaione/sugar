package com.example;

import android.content.Context;
import com.orm.SugarRecord;

public class NoteRelation  extends SugarRecord<Note> {
    String name;
    int noteId;


    public NoteRelation(Context context) {
        super(context);
    }

    public NoteRelation(Context context, String name, int noteId) {
        super(context);
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
