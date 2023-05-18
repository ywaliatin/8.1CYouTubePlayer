package com.example.a81c;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);

        databaseHelper = new DatabaseHelper(this);

        Button registerbutton = findViewById(R.id.btnRegister);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegisterActivity
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }



    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_USER_ID,
                DatabaseHelper.COLUMN_USER_NAME,
                DatabaseHelper.COLUMN_USER_EMAIL,
                DatabaseHelper.COLUMN_USER_PASSWORD
        };

        String selection = DatabaseHelper.COLUMN_USER_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            // User credentials match
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_NAME));
            Toast.makeText(LoginActivity.this, "Login successful. Welcome, " + userName + "!", Toast.LENGTH_SHORT).show();

            //Proceed to the next activity (HomeActivity)
            Intent homeIntent = new Intent(LoginActivity.this, Home0Activity.class);
            startActivity(homeIntent);
            Log.d("LoginActivity", "Starting HomeActivity");

            //finish(); // Optional: Close the LoginActivity to prevent going back to it from the HomeActivity
        } else {
            // User credentials do not match
            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
}

