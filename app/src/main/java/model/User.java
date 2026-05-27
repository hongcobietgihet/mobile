package model;

import com.google.gson.annotations.SerializedName;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String address;

    private String avatar_url;
    private String description;
    private Boolean isFriend;

    public User() {}

    public int getId(){return id; }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone() {return phone; }

    public String getPassword(){
        return password;
    }

    public String getAddress(){
        return address;
    }

    public String getAvatar(){
        return avatar_url;
    }

    public String getDescription(){
        return description;
    }
    public Boolean getIsFriend(){return isFriend;}



    public void setId(int id){ this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {this.phone = phone; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public void setAvatar(String avatar) {

        this.avatar_url = avatar;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public void setIsFriend(Boolean isFriend){
        this.isFriend = isFriend;
    }
}

