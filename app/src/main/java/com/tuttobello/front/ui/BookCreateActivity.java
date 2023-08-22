package com.tuttobello.front.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.tuttobello.front.R;
import com.tuttobello.front.model.book.RBook;
import com.tuttobello.front.model.book.SBook;
import com.tuttobello.front.model.category.RCategory;
import com.tuttobello.front.model.response.ResponseApi;
import com.tuttobello.front.usecase.book.BookUseCase;
import com.tuttobello.front.usecase.category.CategoryUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BookCreateActivity extends AppCompatActivity {

    public BookCreateActivity(){}
    String idCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_create);

        String bookId = getIntent().getStringExtra("bookId");
        String bookName = getIntent().getStringExtra("bookName");
        String bookDescription = getIntent().getStringExtra("bookDescription");
        String categoryName = getIntent().getStringExtra("categoryName");

        EditText nameBook = findViewById(R.id.etNameBook);
        EditText descriptionBook = findViewById(R.id.etDescriptionBook);
        CategoryUseCase category = new CategoryUseCase();

        //Mostrando valores para el libro a actualizar
        nameBook.setText(bookName);
        descriptionBook.setText(bookDescription);


        List<RCategory> categories = new ArrayList<>();
        ArrayAdapter<RCategory> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ListView spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemClickListener(
                (parent, view, position, id) -> idCategory = categories.get(position).categoryId);

        category.getCategories("category")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseApi<RCategory>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ResponseApi<RCategory> rCategoryResponseApi) {
                        if (rCategoryResponseApi.statusCode == 401) {
                            Toast.makeText(BookCreateActivity.this, "Tu token expiro", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        categories.addAll(rCategoryResponseApi.body);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

        Button backButton = findViewById(R.id.btnBack);
        Log.e("error-tag", "execute");

        backButton.setOnClickListener(v -> {
            onBackPressed(); // Llama al método que maneja la acción de volver
        });

        //creando nuevo libro
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            Context CONTEXT = BookCreateActivity.this;
            try {
                BookUseCase bookUseCase = new BookUseCase();
                if (this.idCategory.isEmpty()) {
                    Snackbar.make(v, "Selecciona una categoria", Snackbar.LENGTH_LONG).show();
                    return;
                }
                SBook book = new SBook(nameBook.getText().toString(), descriptionBook.getText().toString(), idCategory);

                if(bookId != null && !bookId.isEmpty()){
                    Snackbar.make(v, "libro actualizado", Snackbar.LENGTH_LONG).show();

                    bookUseCase.updateBook("book-detail/"+bookId, book)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<ResponseApi<RBook>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {}

                            @Override
                            public void onSuccess(@NonNull ResponseApi<RBook> response) {
                                if (response.statusCode == 401) {
                                    Snackbar.make(v, "Este libro contiene atributos invalidos", Snackbar.LENGTH_LONG).show();
                                    return;
                                }
                                Intent onHome = new Intent(CONTEXT, HomeActivity.class);
                                startActivity(onHome);
                                finish();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Snackbar.make(v, "Ha Ocurrido un Error al actualizar el Libro", Snackbar.LENGTH_LONG).show();
                            }
                        });
                    return;
                }

                bookUseCase.createBook("book-detail", book)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<ResponseApi<RBook>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {}

                            @Override
                            public void onSuccess(@NonNull ResponseApi<RBook> response) {
                                if (response.statusCode == 401) {
                                    Snackbar.make(v, "Este libro contiene atributos invalidos", Snackbar.LENGTH_LONG).show();
                                    return;
                                }
                                Intent onHome = new Intent(CONTEXT, HomeActivity.class);
                                startActivity(onHome);
                                finish();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Snackbar.make(v, "Ha Ocurrido un Error al registrar el Libro", Snackbar.LENGTH_LONG).show();
                            }
                        });

            } catch (Exception ex) {
                Toast.makeText(CONTEXT, ex.getMessage()+" pepepe", Toast.LENGTH_LONG).show();
            }
        }
        );
    }
}