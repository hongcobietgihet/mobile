package com.example.myapplication;

import model.User;

public class post {

    private int id;
    private int user_id;

    private User author;
    private String content;
    private String created_at;
    private boolean isHidden;


    public int getId() {return id; }

    public int getUser_idId() {return user_id; }
    public User getAuthor() {
        return author;
    }


    public String getContent() {
        return content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden){
        isHidden = hidden;
    }

    public String getName() {
        if(author == null) return "";
        return author.getName();
    }
    public String getAvatar() {
        if(author == null) return "";
        return author.getAvatar();
    }
}
