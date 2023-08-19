package com.tuttobello.front.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tuttobello.front.R;

public class BookCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_create);

        Button backButton = findViewById(R.id.btnBack);
        Log.e("error-tag", "execute");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("error-execute", "probando");
                onBackPressed(); // Llama al método que maneja la acción de volver
            }
        });
    }
}