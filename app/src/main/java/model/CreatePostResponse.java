package model;

import com.example.myapplication.post;

public class CreatePostResponse {
    private String status;

    private String message;

    private post data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public post getData() {
        return data;
    }
}
