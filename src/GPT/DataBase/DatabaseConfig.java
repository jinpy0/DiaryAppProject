package GPT.DataBase;

public class DatabaseConfig {
    private String dbUrl;         // 데이터베이스 URL
    private String username;      // 데이터베이스 사용자 이름
    private String password;      // 데이터베이스 비밀번호

    // 생성자
    public DatabaseConfig(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    // Getter 메서드
    public String getDbUrl() {
        return dbUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
