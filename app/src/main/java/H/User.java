package H;

import androidx.navigationevent.NavigationEventTransitionState;

public class User {
    private String name;
    private String email;
    private String pw;
    private String address;
    private String avt;
    private String desc;

    public User() {}

    public User(String name, String email, String pw, String address, String avt, String desc){
        this.name = name;
        this.email = email;
        this.pw = pw;
        this.address = address;
        this.avt = avt;
        this.desc = desc;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
}

