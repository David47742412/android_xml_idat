package com.tuttobello.front.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tuttobello.front.R;

public class HomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Button btn = findViewById(R.id.btnCreateBook);

		btn.setOnClickListener((new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_book_create);
			}
		}));
 	}
}