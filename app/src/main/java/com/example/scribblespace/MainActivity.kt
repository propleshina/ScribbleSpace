package com.example.scribblespace

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.DatabaseHandler
import com.example.notesapp.Note
import com.example.notesapp.com.example.scribblespace.AddNoteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var notesListView: ListView
    private lateinit var addNoteButton: Button
    private lateinit var notesList: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHandler = DatabaseHandler(this)
        notesListView = findViewById(R.id.listView)
        addNoteButton = findViewById(R.id.addNoteButton)

        notesList = databaseHandler.getAllNotes().toMutableList()
        updateListView()

        addNoteButton.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        notesListView.setOnItemClickListener { _, _, position, _ ->
            databaseHandler.deleteNote(notesList[position].id)
            notesList.removeAt(position)
            updateListView()
        }
    }

    private fun updateListView() {
        val titles = notesList.map { it.title }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titles)
        notesListView.adapter = adapter
    }
}
