package model;

public class LoginRequest {
    private String email;
    private String password;
    public LoginRequest(String email, String pw){
        this.email = email;
        this.password = pw;
    }

}
