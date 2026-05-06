package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

import H.File;
import H.User;

public class MainActivity2 extends AppCompatActivity {

    TextInputEditText edtEmail;
    TextInputEditText edtPw;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        edtEmail = findViewById(R.id.edtEmail);
        edtPw = findViewById(R.id.edtPw);
        btnLogin = findViewById(R.id.btnLogin);

        ActivityResultLauncher<Intent> resgisterLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {if(result.getResultCode() == RESULT_OK){
                            Toast.makeText(this, "Đăng ký thành công",
                                    Toast.LENGTH_SHORT).show();
                        }});

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String pw = edtPw.getText().toString().trim();
            if(email.isEmpty() || pw.isEmpty()){
                Toast.makeText(this, "Vui longf nhập đầy đủ thông tin",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            List<User> users = File.readUsers(this);

            for(User u : users){
                if(u.getEmail().equals(email) && u.getPassword().equals(pw)){
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);

                    intent.putExtra("email", u.getEmail());
                    startActivity(intent);

                    return;
                }
            }

            Toast.makeText(this, "Sai email hoặc password",
                    Toast.LENGTH_SHORT).show();
        });

    }
}