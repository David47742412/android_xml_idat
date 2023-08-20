package com.tuttobello.front.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.tuttobello.front.R;
import com.tuttobello.front.model.book.RBook;
import com.tuttobello.front.model.response.ResponseApi;

import java.util.List;
import java.util.Objects;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {

    private List<RBook> books;
    private OnDeleteClickListener onDeleteClickListener;

    public BookListAdapter(List<RBook> books, OnDeleteClickListener onDeleteClickListener) {
        this.books = books;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        RBook book = books.get(position);


        holder.bookNameTextView.setText(book.bookName);
        holder.bookDescrTextView.setText(book.bookDescription);

        // Configurar el listener para el botón delete
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(book.bookId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String bookId);
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookNameTextView;
        TextView bookDescrTextView;
        Button deleteButton;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookNameTextView = itemView.findViewById(R.id.bookNameTextView);
            bookDescrTextView = itemView.findViewById(R.id.bookDescrTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
