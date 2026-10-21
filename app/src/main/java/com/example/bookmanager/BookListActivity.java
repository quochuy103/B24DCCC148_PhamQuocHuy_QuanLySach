package com.example.bookmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookListActivity extends AppCompatActivity {

    private TextView txtStats;
    private ListView listBooks;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        txtStats = findViewById(R.id.txtStats);
        listBooks = findViewById(R.id.listBooks);
        Button btnAddBook = findViewById(R.id.btnAddBook);

        BookStore.load(this);

        adapter = new BookAdapter(this, BookStore.books);
        listBooks.setAdapter(adapter);

        refreshList();

        btnAddBook.setOnClickListener(view -> {
            Intent intent = new Intent(
                    BookListActivity.this,
                    AddBookActivity.class
            );
            startActivity(intent);
        });

        listBooks.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(
                    BookListActivity.this,
                    BookDetailActivity.class
            );
            intent.putExtra("bookIndex", position);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        BookStore.load(this);

        int readCount = 0;

        for (Book book : BookStore.books) {
            if ("Đã đọc".equals(book.getStatus())) {
                readCount++;
            }
        }

        int total = BookStore.books.size();
        int unfinished = total - readCount;

        txtStats.setText(
                "Tổng số sách: " + total
                        + "  |  Đã đọc: " + readCount
                        + "  |  Chưa đọc xong: " + unfinished
        );

        adapter.notifyDataSetChanged();
    }
}