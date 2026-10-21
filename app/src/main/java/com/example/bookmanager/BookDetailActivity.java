package com.example.bookmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    private Book book;
    private int bookIndex;
    private TextView txtDetailStatus;
    private Button btnMarkRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        BookStore.load(this);

        bookIndex = getIntent().getIntExtra("bookIndex", -1);

        if (bookIndex < 0 || bookIndex >= BookStore.books.size()) {
            finish();
            return;
        }

        book = BookStore.books.get(bookIndex);

        TextView txtTitle = findViewById(R.id.txtDetailTitle);
        TextView txtAuthorCategory = findViewById(R.id.txtDetailAuthorCategory);
        txtDetailStatus = findViewById(R.id.txtDetailStatus);
        TextView txtNote = findViewById(R.id.txtDetailNote);
        RatingBar ratingDetail = findViewById(R.id.ratingDetail);
        btnMarkRead = findViewById(R.id.btnMarkRead);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnDeleteBook = findViewById(R.id.btnDeleteBook);

        txtTitle.setText(book.getTitle());
        txtAuthorCategory.setText(book.getAuthor() + " • " + book.getCategory());
        txtNote.setText(book.getNote().isEmpty() ? "Không có ghi chú" : book.getNote());
        ratingDetail.setRating(book.getRating());
        updateStatusDisplay();

        ratingDetail.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                book.setRating(rating);
                BookStore.save(this);
            }
        });

        btnMarkRead.setOnClickListener(view -> {
            book.setStatus("Đã đọc");
            BookStore.save(this);
            updateStatusDisplay();
            Toast.makeText(this, "Đã cập nhật tình trạng sách", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(view -> finish());

        btnDeleteBook.setOnClickListener(view -> {
            BookStore.books.remove(bookIndex);
            BookStore.save(this);
            Toast.makeText(this, "Đã xóa sách", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void updateStatusDisplay() {
        txtDetailStatus.setText(book.getStatus());

        if ("Đã đọc".equals(book.getStatus())) {
            btnMarkRead.setVisibility(View.GONE);
        } else {
            btnMarkRead.setVisibility(View.VISIBLE);
        }
    }
}