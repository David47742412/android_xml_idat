package com.tuttobello.front.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.tuttobello.front.R;
import com.tuttobello.front.model.auth.RAuth;
import com.tuttobello.front.model.auth.SAuth;
import com.tuttobello.front.model.book.RBook;
import com.tuttobello.front.model.book.SBook;
import com.tuttobello.front.model.category.RCategory;
import com.tuttobello.front.model.response.ResponseApi;
import com.tuttobello.front.usecase.LoginUseCase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_create);

        CategoryUseCase category = new CategoryUseCase();

        List<RCategory> categories = new ArrayList<>();

        category.getCategories("category")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<ResponseApi<RCategory>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onSuccess(@NonNull ResponseApi<RCategory> rCategoryResponseApi) {
                    if(rCategoryResponseApi.statusCode == 401){
                        Toast.makeText(BookCreateActivity.this, "Tu token expiro", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    categories.addAll(rCategoryResponseApi.body);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }
            });

        //key value


        //lista de Categorias
        String[] categoryList = new String[] {"novela","mito","leyenda"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                categoryList
        );

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.dataCategory);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Toast.makeText(BookCreateActivity.this,
                        autoCompleteTextView.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button backButton = findViewById(R.id.btnBack);
        Log.e("error-tag", "execute");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("error-execute", "probando");

                onBackPressed(); // Llama al método que maneja la acción de volver
            }
        });

        //creando nuevo libro
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener( v -> {
            Context CONTEXT = BookCreateActivity.this;
            try {
                EditText nameBook = findViewById(R.id.etNameBook);
                EditText descriptionBook = findViewById(R.id.etDescriptionBook);
                EditText idCategory = findViewById(R.id.dataCategory);
                BookUseCase bookUseCase = new BookUseCase();
                SBook book = new SBook(nameBook.getText().toString(), descriptionBook.getText().toString(), idCategory.getText().toString());
                bookUseCase.createBook("book-detail", book)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<ResponseApi<RBook>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

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
                Toast.makeText(CONTEXT, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            }
        );
    }
}