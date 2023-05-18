package com.example.a81c;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword;
    private Button btnRegister;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        etName = findViewById(R.id.etRegisterName);
        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnRegister);

        databaseHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(databaseHelper.getColumnUserName(), name);
        values.put(databaseHelper.getColumnUserEmail(), email);
        values.put(databaseHelper.getColumnUserPassword(), password);

        long newRowId = db.insert(DatabaseHelper.TABLE_USERS, null, values);

        if (newRowId != -1) {
            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
            //clearFields();

            //Proceed to the next activity (HomeActivity)
            Intent homeIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(homeIntent);
            Log.d("RegisterActivity", "Starting LoginActivity");


        } else {
            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void clearFields() {
        etName.setText("");
        etEmail.setText("");
        etPassword.setText("");
    }
}
