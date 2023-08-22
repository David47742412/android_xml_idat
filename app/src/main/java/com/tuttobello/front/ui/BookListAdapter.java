package com.tuttobello.front.ui;

//import static androidx.core.app.ActivityCompat.recreate;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tuttobello.front.R;
import com.tuttobello.front.model.book.RBook;
import com.tuttobello.front.model.response.ResponseApi;
import com.tuttobello.front.usecase.book.BookUseCase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

        new Intent().putExtra("bookId", book.bookId)
                .putExtra("bookName", book.bookName)
                .putExtra("bookDescription", book.bookDescription)
                .putExtra("categoryName", book.categoryName);

        new Intent().getStringExtra("bookId");

        // Configurar el listener para el botón delete
        holder.deleteButton.setOnClickListener(v -> {
//                    Context CONTEXT = BookListAdapter.this;
                    try {
                        BookUseCase bookUseCase = new BookUseCase();
                        if (book.bookId.isEmpty()) {
                            Snackbar.make(v, "No se especifico el Id de Libro", Snackbar.LENGTH_LONG).show();
                            return;
                        }
//                        SBook book = new SBook();
                        bookUseCase.deleteBook("book-detail/"+book.bookId)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<ResponseApi<RBook>>() {
                                    @Override
                                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull ResponseApi<RBook> response) {
                                        if (response.statusCode == 401) {
                                            Snackbar.make(v, "No se pudo eliminar el libro "+book.bookId, Snackbar.LENGTH_LONG).show();
                                            return;
                                        }

//                                        Intent onHome = new Intent(CONTEXT, BookListAdapter.class);
//                                        startActivity(onHome);
//                                        finish();
                                    }

                                    @Override
                                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                        Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
                                    }
                                });

                    } catch (Exception ex) {
//                        Toast.makeText(CONTEXT, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
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
