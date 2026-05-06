package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.util.List;

import H.File;
import H.User;


public class MainActivity extends AppCompatActivity {
    TextInputEditText edtName;
    TextInputEditText edtEmail;
    TextInputEditText edtPw;
    TextInputEditText edtCPw;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPw = findViewById(R.id.edtPw);
        edtCPw = findViewById(R.id.edtCPw);

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String pw = edtPw.getText().toString().trim();
            String cpw = edtCPw.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || pw.isEmpty() || cpw.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pw.equals(cpw)) {
                Toast.makeText(this, "Mật khẩu không khớp",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            List<User> users = File.readUsers(this);
            for (User u : users) {
                if (u.getEmail().equals(email)) {
                    Toast.makeText(this, "Email đã tồn tại",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            User newUser = new User(name, email, pw,"", "", "");
            users.add(newUser);

            File.saveUsers(this, users);

            Toast.makeText(this, "Đăng ký thành công",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);

            finish();
        });
    }
}