package com.example.bookmanager;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends BaseAdapter {

    private final Context context;
    private final List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.row_book, parent, false);
        }

        Book book = books.get(position);

        TextView txtTitle = row.findViewById(R.id.txtBookTitle);
        TextView txtAuthorCategory = row.findViewById(R.id.txtBookAuthorCategory);
        TextView txtStatus = row.findViewById(R.id.txtBookStatus);
        TextView txtRating = row.findViewById(R.id.txtBookRating);

        txtTitle.setText(book.getTitle());
        txtAuthorCategory.setText(book.getAuthor() + " • " + book.getCategory());
        txtStatus.setText(book.getStatus());

        int statusColor = "Đã đọc".equals(book.getStatus())
                ? Color.rgb(52, 122, 69)
                : Color.rgb(52, 122, 69);
        txtStatus.setTextColor(statusColor);

        int rating = Math.round(book.getRating());
        String stars = "★★★★★";
        SpannableString coloredStars = new SpannableString(stars);
        coloredStars.setSpan(
                new ForegroundColorSpan(Color.rgb(220, 220, 220)),
                0,
                stars.length(),
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        if (rating > 0) {
            coloredStars.setSpan(
                    new ForegroundColorSpan(Color.rgb(242, 145, 0)),
                    0,
                    Math.min(rating, 5),
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        txtRating.setText(coloredStars);

        return row;
    }
}