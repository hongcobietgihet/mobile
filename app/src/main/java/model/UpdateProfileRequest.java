package model;

public class UpdateProfileRequest {
    private String name;
    private String address;
    private String avatar_url;
    private String description;
    private String phone;

    public UpdateProfileRequest(
            String name,
            String address,
            String avatar_url,
            String description,
            String phone
    ) {
        this.name = name;
        this.address = address;
        this.avatar_url = avatar_url;
        this.description = description;
        this.phone = phone;
    }
}
