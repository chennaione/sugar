package com.example.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.R;
import com.example.models.Note;
import com.example.models.Tag;

import static com.orm.SugarRecord.save;


public class AddNoteActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LinearLayout view = (LinearLayout) findViewById(R.id.layout);
        TextView titleText = new TextView(this);
        titleText.setText("Title");
        TextView descText = new TextView(this);
        descText.setText("Description");
        TextView tagText = new TextView(this);
        tagText.setText("Tag");
        final EditText titleBox = new EditText(this);
        final EditText descBox = new EditText(this);
        final EditText tagBox = new EditText(this);

        Button save = new Button(this);
        save.setText("Save");
        view.addView(titleText);
        view.addView(titleBox);
        view.addView(descText);
        view.addView(descBox);
        view.addView(tagText);
        view.addView(tagBox);
        view.addView(save);

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Tag tag = new Tag(tagBox.getText().toString());
                save(tag);
                save(new Note(10 + (int) (10 * Math.random()), titleBox.getText().toString(), descBox.getText().toString(), tag));
                Intent intent = new Intent(AddNoteActivity.this, NoteListActivity.class);
                startActivity(intent);
            }
        });

    }
}
