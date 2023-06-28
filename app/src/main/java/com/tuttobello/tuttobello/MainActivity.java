package com.tuttobello.tuttobello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.tuttobello.tuttobello.room.dao.UserDao;
import com.tuttobello.tuttobello.room.database.__AppDatabase__;
import com.tuttobello.tuttobello.room.model.User;
//////////////////

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ExecutorService executor;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Tareas en segundo plano aquí



                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Tareas en el hilo principal (UI) aquí

                    }
                });
            }
        });
    }

}