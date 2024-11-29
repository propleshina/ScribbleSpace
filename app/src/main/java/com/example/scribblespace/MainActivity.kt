package com.example.scribblespace

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class Note(
    val title: String,
    val content: String,
    var id: Long
)

class NotesAdapter(context: Context, private val notes: MutableList<Note>) : ArrayAdapter<Note>(context, 0, notes) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val note = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val titleTextView = view.findViewById<TextView>(android.R.id.text1)
        val contentTextView = view.findViewById<TextView>(android.R.id.text2)

        titleTextView.text = note?.title
        contentTextView.text = note?.content

        return view
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var notesListView: ListView
    private lateinit var notesAdapter: NotesAdapter
    private val notes = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Убедитесь, что у вас есть соответствующий layout

        // Инициализация ListView и адаптера
        notesListView = findViewById(R.id.listView)
        notesAdapter = NotesAdapter(this, notes)
        notesListView.adapter = notesAdapter

        // Устанавливаем слушатель на кнопку добавления заметки
        findViewById<Button>(R.id.addNoteButton).setOnClickListener {
            addNewNote()
        }
    }

    private fun addNewNote() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.activity_add_note, null) // Создайте этот layout для ввода данных
        dialogBuilder.setView(dialogView)

        val titleEditText = dialogView.findViewById<EditText>(R.id.noteTitleEditText)
        val contentEditText = dialogView.findViewById<EditText>(R.id.noteContentEditText)

        dialogBuilder.setTitle("Добавить новую заметку")
        dialogBuilder.setPositiveButton("Добавить") { dialog, which ->
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            // Создаем новую заметку и добавляем её в список
            val newNote = Note(title, content, cursor.getLong(idIndex))
            notes.add(newNote)

            // Уведомляем адаптер об изменениях
            notesAdapter.notifyDataSetChanged()
        }
        dialogBuilder.setNegativeButton("Отмена") { dialog, which -> dialog.dismiss() }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}
