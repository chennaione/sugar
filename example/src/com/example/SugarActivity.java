package com.example;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import com.orm.Database;
import com.orm.SugarApp;

public class SugarActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         Note.deleteAll(Note.class);
        TextNote.deleteAll(TextNote.class);
        Tag.deleteAll(Tag.class);
        initDb();
        Intent intent = new Intent(this, NoteListActivity.class);
        startActivity(intent);
    }

     private void initDb() {

        Tag t1 = new Tag("tag1");
        Tag t2 = new Tag("tag2");
        t1.save();
        t2.save();

        Note n1 = new Note( 10, "note1", "description1", t1);
        Note n2 = new Note(11, "note12", "description2", t1);
        Note n3 = new Note( 12, "note13", "description3", t2);
        Note n4 = new Note( 13, "note4", "description4", t2);

         TextNote textNote = new TextNote();
         textNote.desc = "Test";

         textNote.save();
        n1.save();
        n2.save();
        n3.save();
        n4.save();

         n1.setDescription("matrix");
         n1.setTitle("atrix");
         n1.save();
         n2.setDescription("matrix");
         n2.setTitle("satrix");
         n2.save();
         n3.setDescription("matrix");
         n3.setTitle("batrix");
         n3.save();
         
        

    }
}
