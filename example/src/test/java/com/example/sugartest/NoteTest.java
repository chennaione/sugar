package com.example.sugartest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import com.example.models.Note;
import com.example.models.Tag;


@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk=18)
public class NoteTest {
    @Test
    public void simpleNoteTest() throws Exception {
        Note note = new Note(0, "Note", "this is a note", new Tag("tag"));
        assert(note.getTitle().equals("Note"));
    }
}
