package com.example.scribblespace

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.DatabaseHandler
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.room.util.query

class AddNoteActivity : AppCompatActivity() {

    private lateinit var noteTitleEditText: EditText
    private lateinit var noteContentEditText: EditText
    private lateinit var saveNoteButton: Button
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_add_note)

        noteTitleEditText = findViewById(R.id.noteTitleEditText)
        noteContentEditText = findViewById(R.id.noteContentEditText)
        saveNoteButton = findViewById(R.id.saveNoteButton)

        databaseHandler = DatabaseHandler(this)

        saveNoteButton.setOnClickListener {
            val title = noteTitleEditText.text.toString()
            val content = noteContentEditText.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val cursor:Cursor = SQLiteDatabase.query("note.db",null, null, null, null, null, null)


                val note = Note(title = title, content = content, id = cursor.getLong(idIndex))
                databaseHandler.addNote(note)
                finish()
            }
        }
    }
}
