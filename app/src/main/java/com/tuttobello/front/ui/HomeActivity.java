package com.tuttobello.front.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.tuttobello.front.R;
import com.tuttobello.front.model.book.RBook;
import com.tuttobello.front.model.response.ResponseApi;
import com.tuttobello.front.network.RetrofitClient;
import com.tuttobello.front.network.api.IApi;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity implements BookListAdapter.OnDeleteClickListener {
    private RecyclerView recyclerView;
    private BookListAdapter adapter;
    private IApi apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        apiService = RetrofitClient.getClient().create(IApi.class);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btn = findViewById(R.id.btnCreateBook);

        btn.setOnClickListener((v -> startActivity(new Intent(HomeActivity.this, BookCreateActivity.class))));

        Call<ResponseApi<Object>> call = apiService.get("book-detail");

        call.enqueue(new Callback<ResponseApi<Object>>() {
            @Override
            public void onResponse(Call<ResponseApi<Object>> call, Response<ResponseApi<Object>> response) {
                if (response.isSuccessful()) {
                    ResponseApi data = response.body();
                    if (data != null) {
                        Gson gson = new Gson();
                        String json = gson.toJson(data.body); // Convert the body to JSON string
                        RBook[] booksArray = gson.fromJson(json, RBook[].class); // Convert JSON string to array of RBook
                        List<RBook> books = Arrays.asList(booksArray); // Convert array to List

                        // Now you can use the 'books' list to set up your RecyclerView
                        adapter = new BookListAdapter(books, HomeActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    System.out.println(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<Object>> call, Throwable t) {
                System.out.println(t.fillInStackTrace());
            }

        });


    }
    @Override
    public void onDeleteClick(String bookId) {
        // Mostrar el ID del libro en la consola al hacer clic en el botón delete
        System.out.println("Book ID to Delete: " + bookId);
    }

}