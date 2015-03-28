package com.example.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.orm.SugarRecord;

import com.example.models.NewNote;
import com.example.models.Note;
import com.example.R;


public class NoteListActivity extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notelist);

        List<Note> notes = Select.from(Note.class).orderBy("title").list();
        List<NewNote> list = SugarRecord.listAll(NewNote.class);

        setListAdapter(new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, notes));

        findViewById(R.id.Button01).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(NoteListActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        Log.d("COUNT", "Count: " + Select.from(Note.class).where(new Condition[]{new Condition("title").eq("note")}).count() + "/" + notes.size());
    }
}
