package com.example.bookmanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private EditText edtTitle;
    private EditText edtAuthor;
    private EditText edtNote;
    private Spinner spinnerCategory;
    private RadioGroup radioGroupStatus;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtNote = findViewById(R.id.edtNote);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        ratingBar = findViewById(R.id.ratingBar);

        TextView btnBack = findViewById(R.id.btnBack);
        Button btnSaveBook = findViewById(R.id.btnSaveBook);

        BookStore.load(this);

        btnBack.setOnClickListener(view -> finish());
        btnSaveBook.setOnClickListener(view -> saveBook());
    }

    private void saveBook() {
        String title = edtTitle.getText().toString().trim();
        String author = edtAuthor.getText().toString().trim();

        if (title.isEmpty()) {
            edtTitle.setError("Vui lòng nhập tên sách");
            edtTitle.requestFocus();
            return;
        }

        if (author.isEmpty()) {
            edtAuthor.setError("Vui lòng nhập tác giả");
            edtAuthor.requestFocus();
            return;
        }

        int selectedId = radioGroupStatus.getCheckedRadioButtonId();
        String status;

        if (selectedId == R.id.radioReading) {
            status = "Đang đọc";
        } else if (selectedId == R.id.radioRead) {
            status = "Đã đọc";
        } else {
            status = "Chưa đọc";
        }

        String category = spinnerCategory.getSelectedItem().toString();
        float rating = ratingBar.getRating();
        String note = edtNote.getText().toString().trim();

        Book book = new Book(title, author, category, status, rating, note);
        BookStore.addBook(this, book);

        Toast.makeText(this, "Đã lưu sách", Toast.LENGTH_SHORT).show();
        finish();
    }
}