package com.example.myapplication;

public class post {

    private String name;
    private String avatar;
    private String content;
    private String time;
    private boolean isHidden;

    public post(
            String name,
            String avatar,
            String content,
            String time,
            boolean isHidden
    ) {
        this.name = name;
        this.avatar = avatar;
        this.content = content;
        this.time = time;
        this.isHidden = isHidden;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden){
        isHidden = hidden;
    }
}
