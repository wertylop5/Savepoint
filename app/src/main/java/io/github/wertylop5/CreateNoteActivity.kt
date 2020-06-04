package io.github.wertylop5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class CreateNoteActivity : AppCompatActivity() {
    private lateinit var titleText: EditText
    private lateinit var descText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        titleText = findViewById(R.id.create_note_title_edittext)
        descText = findViewById(R.id.create_note_desc_edittext)
    }
}
