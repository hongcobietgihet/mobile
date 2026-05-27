package model;

import com.example.myapplication.post;

import java.util.List;

public class GetPostsResponse {
    private String status;

    private List<post> data;

    private int count;

    public String getStatus() {
        return status;
    }

    public List<post> getData() {
        return data;
    }

    public int getCount() {
        return count;
    }
}
