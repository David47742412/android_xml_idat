package com.tuttobello.front.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.tuttobello.front.R;
import com.tuttobello.front.model.auth.RAuth;
import com.tuttobello.front.model.auth.SAuth;
import com.tuttobello.front.model.response.ResponseApi;
import com.tuttobello.front.usecase.LoginUseCase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

private final OnClickListener onLogin = (v) -> {
	Context CONTEXT = LoginActivity.this;
	try {
		EditText username = findViewById(R.id.etUsername);
		EditText password = findViewById(R.id.etPassword);
		LoginUseCase loginUseCase = new LoginUseCase();
		SAuth auth = new SAuth(username.getText().toString(), password.getText().toString());
		loginUseCase.login("auth/login", auth)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new SingleObserver<ResponseApi<RAuth>>() {
					@Override
					public void onSubscribe(@NonNull Disposable d) {
						
					}
					
					@Override
					public void onSuccess(@NonNull ResponseApi<RAuth> response) {
						if (response.statusCode == 401) {
							Snackbar.make(v, "Usuario o contraseña incorrectos", Snackbar.LENGTH_LONG).show();
							return;
						}
						Intent onHome = new Intent(CONTEXT, HomeActivity.class);
						startActivity(onHome);
						finish();
					}
					
					@Override
					public void onError(@NonNull Throwable e) {
						Snackbar.make(v, "Ha Ocurrido un Error al momento de autenticar", Snackbar.LENGTH_LONG).show();
					}
				});
	} catch (Exception ex) {
		Toast.makeText(CONTEXT, ex.getMessage(), Toast.LENGTH_LONG).show();
	}
};

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);
	Button btnLogin = findViewById(R.id.btnLogin);
	btnLogin.setOnClickListener(onLogin);
}

}