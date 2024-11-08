package FirstTest.User;

public class User {
    private String profileImagePath;
    private String ID;
    private String name;
    private String email;
    private String password;

    public User(String profileImagePath, String ID, String name, String email, String password) {
        this.profileImagePath = profileImagePath;
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
