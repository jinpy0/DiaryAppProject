package Diary.DataBase;

public class User {
    private String user_id; // 아이디
    private String name;     // 이름
    private String email;    // 이메일
    private String password; // 비밀번호
    private String profileImage; // 프로필 이미지 경로 (선택 사항)

    // 기본 생성자
    public User() {
    }

    public User(String user_id, String name, String email) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
    }


    // 전체 필드를 초기화하는 생성자
    public User(String user_id, String name, String email, String password, String profileImage) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
    }

    // 생성자: profileImage 없이 초기화
    public User(String user_id, String name, String email, String password) {
        this(user_id, name, email, password, null);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", profileImage='" + (profileImage != null ? profileImage : "default") + '\'' +
                '}';
    }
}
