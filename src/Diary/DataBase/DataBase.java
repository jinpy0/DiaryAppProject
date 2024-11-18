package Diary.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataBase {
    private static final String URL = "jdbc:mariadb://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static boolean insertUser(String username, String name, String email, String password, String imagePath) {
        String insertSQL = "INSERT INTO User (username, name, email, password, image_path) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, imagePath);

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0; // 성공 여부 반환
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
