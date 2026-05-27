package model;

public class RegisterResponse {
    public String status;
    public String message;
    public User user;

    public String getStatus(){return status; }
    public String getMessage(){return message; }
    public User getUser(){return user; }

    public void setStatus(String status) { this.status = status; }
    public void setMessage(String message) { this.message = message; }
    public void setUser(User user) { this.user = user; }
}
