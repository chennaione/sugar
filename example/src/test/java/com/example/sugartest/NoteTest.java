package com.example.sugartest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.example.models.Note;
import com.example.models.Tag;


@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, manifest = "example/src/main/AndroidManifest.xml")
public class NoteTest {
    @Test
    public void simpleNoteTest() throws Exception {
        Note note = new Note(0, "Note", "this is a note", new Tag("tag"));
        assert(note.getTitle().equals("Note"));
    }
}
