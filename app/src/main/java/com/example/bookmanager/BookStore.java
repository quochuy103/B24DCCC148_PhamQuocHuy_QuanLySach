package com.example.bookmanager;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookStore {

    public static final ArrayList<Book> books = new ArrayList<>();
    private static boolean loaded = false;

    public static void load(Context context) {
        if (loaded) {
            return;
        }

        SharedPreferences preferences =
                context.getSharedPreferences("book_storage", Context.MODE_PRIVATE);
        String savedBooks = preferences.getString("books", null);

        if (savedBooks == null) {
            books.clear();
            books.add(new Book("Lập trình Android cơ bản", "Nguyễn Văn A",
                    "Công nghệ", "Đang đọc", 4, ""));
            books.add(new Book("Đắc nhân tâm", "Dale Carnegie",
                    "Kỹ năng sống", "Đã đọc", 5, ""));
            books.add(new Book("Dế Mèn phiêu lưu ký", "Tô Hoài",
                    "Văn học", "Chưa đọc", 0, ""));
            save(context);
        } else {
            readBooks(savedBooks);
        }

        loaded = true;
    }

    public static void addBook(Context context, Book book) {
        load(context);
        books.add(book);
        save(context);
    }

    public static void save(Context context) {
        JSONArray array = new JSONArray();

        try {
            for (Book book : books) {
                JSONObject object = new JSONObject();
                object.put("title", book.getTitle());
                object.put("author", book.getAuthor());
                object.put("category", book.getCategory());
                object.put("status", book.getStatus());
                object.put("rating", book.getRating());
                object.put("note", book.getNote());
                array.put(object);
            }
        } catch (JSONException e) {
            return;
        }

        context.getSharedPreferences("book_storage", Context.MODE_PRIVATE)
                .edit()
                .putString("books", array.toString())
                .apply();
    }

    private static void readBooks(String savedBooks) {
        books.clear();

        try {
            JSONArray array = new JSONArray(savedBooks);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                books.add(new Book(
                        object.getString("title"),
                        object.getString("author"),
                        object.getString("category"),
                        object.getString("status"),
                        (float) object.getDouble("rating"),
                        object.optString("note", "")
                ));
            }
        } catch (JSONException e) {
            books.clear();
        }
    }
}