package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import H.File;
import H.User;

public class MainActivity5 extends AppCompatActivity {

    ListView lvFriend;
    MaterialButton btnGui, btnNhan;
    TextView txtTotal;

    ArrayList<User> users;
    ArrayList<User> suggestList;
    ArrayList<User> requestList;
    ArrayList<User> friendList;
    ArrayList<String> phoneList;

    suggestAdapter sAdt;
    requestAdapter rAdt;

    private static final int CONTACT_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        lvFriend = findViewById(R.id.lvFriends);
        btnGui = findViewById(R.id.btnGui);
        btnNhan = findViewById(R.id.btnNhan);
        txtTotal = findViewById(R.id.txtTotal);

        users = (ArrayList<User>) File.readUsers(this);

        suggestList = new ArrayList<>();
        requestList = new ArrayList<>();
        friendList = new ArrayList<>();
        phoneList = new ArrayList<>();

        sAdt = new suggestAdapter(this, suggestList, requestList);
        rAdt = new requestAdapter(this, requestList, friendList);

        lvFriend.setAdapter(rAdt);

        checkContactsPermission();

        btnGui.setOnClickListener(v -> {
            lvFriend.setAdapter(sAdt);
            txtTotal.setVisibility(View.GONE);
        });

        btnNhan.setOnClickListener(v -> {
            lvFriend.setAdapter(rAdt);
            txtTotal.setVisibility(View.VISIBLE);
            txtTotal.setText("Tổng số bạn bè: " + friendList.size());
        });
    }

    private void checkContactsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    CONTACT_PERMISSION_CODE);
        } else {
            readAndFilterContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readAndFilterContacts();
            } else {
                Toast.makeText(this, "Cần quyền danh bạ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readAndFilterContacts() {
        loadContacts();
        loadSuggestUsers();

        lvFriend.setAdapter(sAdt);
        sAdt.notifyDataSetChanged();


    }

    private String normalize(String phone) {
        if (phone == null) return "";

        return phone.replaceAll("[\\s\\-\\(\\)]", "")
                .replace("+84", "0");
    }

    private void loadContacts() {

        phoneList.clear();

        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String phone = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                );

                phoneList.add(normalize(phone));
            }
            cursor.close();
        }

        System.out.println("CONTACT SIZE = " + phoneList.size());
        System.out.println("CONTACT LIST = " + phoneList);
    }


    private void loadSuggestUsers() {

        suggestList.clear();

        for (User u : users) {

            String userPhone = normalize(u.getPhone());

            boolean inContact = phoneList.contains(userPhone);

            if (inContact && !Boolean.TRUE.equals(u.getIsFriend())) {
                suggestList.add(u);
            }
        }

        System.out.println("SUGGEST SIZE = " + suggestList.size());
    }
}