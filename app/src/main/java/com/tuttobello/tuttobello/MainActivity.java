package com.tuttobello.tuttobello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.tuttobello.tuttobello.room.dao.UserDao;
import com.tuttobello.tuttobello.room.database.__AppDatabase__;
import com.tuttobello.tuttobello.room.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        new DatabaseTask().execute();
    }

    private class DatabaseTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... voids) {
            // Crear la instancia de la base de datos
            __AppDatabase__ database = Room.databaseBuilder(getApplicationContext(),
                    __AppDatabase__.class, "AppDatabase").build();

            // Obtener el UserDao
            UserDao userDao = database.userDao();

            // Realizar la consulta en segundo plano
            return userDao.findOne("david@gmail.com");
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                Log.e("--ejecución", user.email);
            } else {
                Log.e("--ejecución", "Usuario no encontrado");
            }
        }
    }
}