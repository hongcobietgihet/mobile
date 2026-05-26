package H;

import androidx.navigationevent.NavigationEventTransitionState;

public class User {
    private String name;
    private String email;
    private String phone;
    private String pw;
    private String address;
    private String avt;
    private String desc;
    private Boolean isFriend;

    public User() {}

    public User(String name, String email,String phone, String pw, String address, String avt, String desc, Boolean isFriend){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pw = pw;
        this.address = address;
        this.avt = avt;
        this.desc = desc;
        this.isFriend = isFriend;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone() {return phone; }

    public String getPassword(){
        return pw;
    }

    public String getAddress(){
        return address;
    }

    public String getAvatar(){
        return avt;
    }

    public String getDescription(){
        return desc;
    }
    public Boolean getIsFriend(){return isFriend;}




    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {this.phone = phone; }

    public void setPassword(String password) {
        this.pw = password;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public void setAvatar(String avatar) {

        this.avt = avatar;
    }

    public void setDescription(String description) {

        this.desc = description;
    }

    public void setIsFriend(Boolean isFriend){
        this.isFriend = isFriend;
    }
}

