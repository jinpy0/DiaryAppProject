package Diary.DataBase.Dto;

public class UserDTO {
    private int id;
    private String userId;
    private String name;
    private String email;
    private String password;
    private String image;
    private String role;

    public UserDTO(String userId, String name, String email, String password, String image, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.role = role;
    }

    public UserDTO(int id, String userId, String name, String email, String password, String image, String role) {
        this.id = getId();
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}