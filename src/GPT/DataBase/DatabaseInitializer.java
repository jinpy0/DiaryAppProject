package GPT.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private DatabaseConfig config; // 데이터베이스 구성

    // 생성자
    public DatabaseInitializer(DatabaseConfig config) {
        this.config = config;
    }

    // 데이터베이스 테이블 생성 메서드
    public void initialize() {
        try (Connection connection = DriverManager.getConnection(config.getDbUrl(), config.getUsername(), config.getPassword());
             Statement statement = connection.createStatement()) {

            // 다이어리 항목 테이블 생성 쿼리
            String createTableSQL = "CREATE TABLE IF NOT EXISTS diary_entries (" +
                    "date DATE PRIMARY KEY," +
                    "content TEXT NOT NULL," +
                    "photo_path TEXT," +
                    "weather VARCHAR(50)" +
                    ");";

            statement.executeUpdate(createTableSQL); // 테이블 생성 쿼리 실행
            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리
        }
    }
}
