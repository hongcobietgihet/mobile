package model;

public class CreatePostRequest {
    private int user_id;

    private String content;

    public CreatePostRequest(int user_id, String content) {
        this.user_id = user_id;
        this.content = content;
    }
}
